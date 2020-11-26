package com.kaisho.inomcrm.app.activity

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.kaisho.inomcrm.R
import com.kaisho.inomcrm.app.model.AccountPOJO
import com.kaisho.inomcrm.app.room.viewModel.AccountViewModel
import com.kaisho.inomcrm.app.viewModel.SharedViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class SplashActivity : AppCompatActivity() {
    companion object{
        private const val ACCOUNT = "Account"
        private const val SETTINGS = "Settings"
        private const val MY_LANG = "My_lang"
        private  var mCurrentUser : FirebaseUser? = null
        private const val MY_LOG = "MyLog"
    }
    //viewModel
    private val accountSharedViewModel: AccountViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    //Firebase
    private lateinit var reference : DatabaseReference

    private var compositeDisposable = CompositeDisposable()
    private lateinit var observer: Observer<List<AccountPOJO>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val prefs = getSharedPreferences(SETTINGS, Activity.MODE_PRIVATE)
        val lang = prefs.getString(MY_LANG, "en")
        if (lang != null)
            setLocale(lang)


        val mAuth = FirebaseAuth.getInstance()
        mCurrentUser = mAuth.currentUser

    }

    private fun retrieveData(){
        //Firebase
        reference = FirebaseDatabase.getInstance().reference
            .child(ACCOUNT).child(sharedViewModel.user)

        observer = Observer {
            if (it.isEmpty())
                dataAdd()
            else
                compositeDisposable.add(
                    dataSource(it[0])
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                        }
                )
        }

       accountSharedViewModel.getAllData.observe(this, observer)

    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }

    private fun dataSource(accountPOJO: AccountPOJO): Completable{
        return Completable.create{

                reference.addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        Log.d(MY_LOG, error.toString())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        accountSharedViewModel.getAllData.removeObserver(observer)
                        val model = AccountPOJO(
                            accountPOJO.id,
                            snapshot.child("image").value.toString(),
                            snapshot.child("manager").value.toString(),
                            snapshot.child("medications").value.toString(),
                            snapshot.child("name").value.toString(),
                            snapshot.child("region").value.toString(),
                            snapshot.child("token").value.toString(),
                            snapshot.child("town").value.toString()
                        )

                        if (model.name == null){
                            goProfile()
                        }
                        else{
                            Log.d(MY_LOG, "Update")
                            accountSharedViewModel.updateData(model)
                            goHome()
                        }


                        it.onComplete()
                    }
                })
        }
    }

    private fun dataAdd(){
            reference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    Log.d("MyLog", error.toString())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    accountSharedViewModel.getAllData.removeObserver(observer)
                    val model = AccountPOJO(
                        0,
                        snapshot.child("image").value.toString(),
                        snapshot.child("manager").value.toString(),
                        snapshot.child("medications").value.toString(),
                        snapshot.child("name").value.toString(),
                        snapshot.child("region").value.toString(),
                        snapshot.child("token").value.toString(),
                        snapshot.child("town").value.toString()
                    )
                    if (model.name == null){
                        goProfile()
                    }
                    else{
                        Log.d(MY_LOG, "Insert")
                        accountSharedViewModel.insertData(model)
                        goHome()
                    }
                }
            })

    }

    private fun goProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun goHome(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onStart() {
        if (mCurrentUser != null){
            retrieveData()
        }
        else{
            goRegistration()
        }
        super.onStart()
    }

    private fun goRegistration() {
        val intent = Intent(this, RegistrationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}