package com.kaisho.inomcrm.app.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.kaisho.inomcrm.R
import com.kaisho.inomcrm.app.model.AccountPOJO
import com.kaisho.inomcrm.app.room.viewModel.AccountViewModel
import com.kaisho.inomcrm.app.viewModel.SharedViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SplashActivity : AppCompatActivity() {
    companion object{
        private const val ACCOUNT = "Account"
    }
    //viewModel
    private val accountSharedViewModel: AccountViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    //Firebase
    private lateinit var reference : DatabaseReference

    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Firebase
        reference = FirebaseDatabase.getInstance().reference
            .child(ACCOUNT).child(sharedViewModel.user)

        compositeDisposable.add(
            dataSource()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
        )
    }

    private fun dataSource(): Completable{
        return Completable.create{
            Thread.sleep(1500)

                reference.addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        Log.d("MyLog", error.toString())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {

                        val model = snapshot.getValue(AccountPOJO::class.java)
                        accountSharedViewModel.insertData(model!!)

                        it.onComplete()
                    }
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}