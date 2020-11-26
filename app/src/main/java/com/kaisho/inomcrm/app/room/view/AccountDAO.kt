package com.kaisho.inomcrm.app.room.view

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kaisho.inomcrm.app.model.AccountPOJO

@Dao
interface AccountDAO {

    @Query("SELECT * FROM account_table")
    fun getAllData(): LiveData<List<AccountPOJO>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(accountPOJO: AccountPOJO)

    @Update
    suspend fun updateData(accountPOJO: AccountPOJO)

    @Query("DELETE FROM account_table")
    suspend fun deleteAll()
}