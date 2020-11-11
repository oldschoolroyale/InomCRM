package com.kaisho.inomcrm.app.room.view

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kaisho.inomcrm.app.model.DataBasePOJO

@Dao
interface DataBaseDAO {
    @Query("SELECT * FROM database_table ORDER BY uniqueId ASC")
    fun getAllData(): LiveData<List<DataBasePOJO>>

    @Query("DELETE FROM database_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(dataBasePOJO: DataBasePOJO)

    @Delete
    suspend fun deleteItem(dataBasePOJO: DataBasePOJO)

    @Query("SELECT * FROM database_table ORDER BY isSelected LIKE :searchQuery")
    fun searchDatabase(searchQuery: Boolean): LiveData<List<DataBasePOJO>>
}