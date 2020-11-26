package com.kaisho.inomcrm.app.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kaisho.inomcrm.app.provider.PCompleteProvider
import com.kaisho.inomcrm.app.view.ProfileView


@InjectViewState
class ProfilePresenter: MvpPresenter<ProfileView>() {

    fun showMessage(error: String){
        viewState.showError(error)
    }

    fun endProfile(text: String, token: String){
        PCompleteProvider(this, token = token).execute(text)
    }

    fun goHome(message: String){
        viewState.showError(message)
        viewState.goHome()
    }
}