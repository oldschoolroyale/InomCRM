package com.kaisho.inomcrm.app.room.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kaisho.inomcrm.app.data.ViewPagerData
import com.kaisho.inomcrm.app.model.AccountPOJO
import com.kaisho.inomcrm.app.room.AccountRoom
import com.kaisho.inomcrm.app.room.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(application: Application): AndroidViewModel(application) {
    private val accountDao = AccountRoom.getDatabase(
        application
    ).getAccountDao()
    private val repository: AccountRepository

    val getAllData: LiveData<List<AccountPOJO>>

    init {
        repository = AccountRepository(accountDao)
        getAllData = repository.getAllData
    }

    fun insertData(accountPOJO: AccountPOJO){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertData(accountPOJO)
        }
    }
}