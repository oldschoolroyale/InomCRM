package com.kaisho.inomcrm.app.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kaisho.inomcrm.app.provider.CodeSendProvider
import com.kaisho.inomcrm.app.view.CodeSendView
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider


@InjectViewState
class CodeSendPresenter : MvpPresenter<CodeSendView>() {

    fun prepare(credentialString: String, otp: String){
        viewState.startLoading()
        Log.d("MyLog", "$credentialString $otp")
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(credentialString, otp)
        CodeSendProvider(this).load(credential)
    }
    fun providedData(check: Boolean){
        viewState.endLoading()
        if (check){
            viewState.openProfile()
        }
        else{
            viewState.showError("Произошла ошибка с верификацией")
        }

    }

}