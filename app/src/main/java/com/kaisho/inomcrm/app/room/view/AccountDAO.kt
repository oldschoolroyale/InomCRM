package com.kaisho.inomcrm.app.room.view

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kaisho.inomcrm.app.model.AccountPOJO

@Dao
interface AccountDAO {

    @Query("SELECT * FROM account_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<AccountPOJO>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(accountPOJO: AccountPOJO)
}