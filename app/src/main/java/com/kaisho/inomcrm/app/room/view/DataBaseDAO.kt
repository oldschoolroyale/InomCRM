package com.kaisho.inomcrm.app.room.view

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kaisho.inomcrm.app.model.DataBasePOJO

@Dao
interface DataBaseDAO {
    @Query("SELECT * FROM database_table ORDER BY positionId ASC")
    fun getAllData(): LiveData<List<DataBasePOJO>>

    @Query("DELETE FROM database_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(dataList: DataBasePOJO)

    @Delete
    suspend fun deleteItem(dataBasePOJO: DataBasePOJO)

    @Update
    suspend fun updateData(dataBasePOJO: DataBasePOJO)

    @Query("SELECT * FROM database_table WHERE isSelected LIKE 1")
    fun searchDatabase(): LiveData<List<DataBasePOJO>>


}