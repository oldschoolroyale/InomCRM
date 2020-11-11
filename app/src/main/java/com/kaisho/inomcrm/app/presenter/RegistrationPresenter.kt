package com.kaisho.inomcrm.app.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kaisho.inomcrm.app.provider.RegistrationProvider
import com.kaisho.inomcrm.app.view.RegistrationView
import com.google.firebase.auth.PhoneAuthProvider
import com.kaisho.inomcrm.app.activity.RegistrationActivity
import java.util.concurrent.TimeUnit

@InjectViewState
class RegistrationPresenter : MvpPresenter<RegistrationView>() {

    fun prepare(phoneNumber : String){
        viewState.startLoading()

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            RegistrationActivity(),
            RegistrationProvider(this)
        )
    }

    fun loadAccount(){
        viewState.endLoading()
        viewState.openMain()
    }

    fun loadError(error : String){
        viewState.endLoading()
        viewState.showError(error)
    }

    fun codeSend(code : String){
        viewState.endLoading()
        viewState.openLogin(code)
    }
}

