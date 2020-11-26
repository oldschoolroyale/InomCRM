package com.kaisho.inomcrm.app.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.kaisho.inomcrm.app.model.AccountPOJO


@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ProfileView: MvpView {
    fun showError(error: String)
    fun goHome()

}