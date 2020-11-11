package com.kaisho.inomcrm.app.room.repository

import androidx.lifecycle.LiveData
import com.kaisho.inomcrm.app.model.DataBasePOJO
import com.kaisho.inomcrm.app.room.view.DataBaseDAO

class DatabaseRepository(private val dataBaseDAO: DataBaseDAO) {

    val getAllData: LiveData<List<DataBasePOJO>> = dataBaseDAO.getAllData()

    suspend fun insertData(dataBasePOJO: DataBasePOJO){
        dataBaseDAO.insertData(dataBasePOJO)
    }

    suspend fun delete(dataBasePOJO: DataBasePOJO){
        dataBaseDAO.deleteItem(dataBasePOJO)
    }

    suspend fun deleteAll(){
        dataBaseDAO.deleteAll()
    }

}