package com.kaisho.inomcrm.app.room.repository

import androidx.lifecycle.LiveData
import com.kaisho.inomcrm.app.model.AccountPOJO
import com.kaisho.inomcrm.app.room.view.AccountDAO

class AccountRepository(private val accountDAO: AccountDAO) {
    val getAllData: LiveData<List<AccountPOJO>> = accountDAO.getAllData()

    suspend fun insertData(accountPOJO: AccountPOJO){
        accountDAO.insertData(accountPOJO)
    }

    suspend fun updateData(accountPOJO: AccountPOJO){
        accountDAO.updateData(accountPOJO)
    }

    suspend fun deleteAll(){
        accountDAO.deleteAll()
    }
}