package com.kaisho.inomcrm.app.activity

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.kaisho.inomcrm.R
import com.kaisho.inomcrm.app.presenter.ProfilePresenter
import com.kaisho.inomcrm.app.view.ProfileView
import com.kaisho.inomcrm.app.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

class ProfileActivity : MvpAppCompatActivity(),
    ProfileView {
    companion object{
        private const val UZ = "uz"
        private const val RU = "en"
        private lateinit var TOKEN: String
        private const val SETTINGS = "Settings"
        private const val MY_LANG = "My_lang"
    }

    private val sharedViewModel by viewModels<SharedViewModel>()

    @InjectPresenter
    lateinit var presenter : ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        TOKEN = sharedViewModel.user

        activityProfileRegBtn.setOnClickListener {
            if (activityProfileFullName.text.toString().isNotEmpty()){
                presenter.endProfile(activityProfileFullName.text.toString(), TOKEN)
            }
            else{
                activityProfileFullName.error = getString(R.string.important_text)
            }
        }

    }

    fun radioClick(view: View) {
        when(view.id){
            R.id.activityProfileRadioUzb ->{
               setLocale(UZ)
            }
            R.id.activityProfileRadioRus ->{
                setLocale(RU)
            }
        }
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun goHome() {
        finish()
        startActivity(Intent(this, SplashActivity::class.java))
    }

    private fun setLocale(code: String){
        val locale = Locale(code)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences(SETTINGS, Activity.MODE_PRIVATE).edit()
        editor.putString(MY_LANG, code)
        editor.apply()

        ActivityCompat.recreate(this)
    }
}