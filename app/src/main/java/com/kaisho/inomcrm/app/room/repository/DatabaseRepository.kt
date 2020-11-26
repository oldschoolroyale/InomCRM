package com.kaisho.inomcrm.app.room.repository

import androidx.lifecycle.LiveData
import com.kaisho.inomcrm.app.model.DataBasePOJO
import com.kaisho.inomcrm.app.room.view.DataBaseDAO

class DatabaseRepository(private val dataBaseDAO: DataBaseDAO) {

    val getAllData: LiveData<List<DataBasePOJO>> = dataBaseDAO.getAllData()

    suspend fun insertData(dataList: DataBasePOJO){
        dataBaseDAO.insertData(dataList)
    }

    suspend fun updateData(dataBasePOJO: DataBasePOJO){
        dataBaseDAO.updateData(dataBasePOJO)
    }

    suspend fun deleteAll(){
        dataBaseDAO.deleteAll()
    }


    fun searchDatabase(): LiveData<List<DataBasePOJO>>{
       return dataBaseDAO.searchDatabase()
    }


}