package com.kaisho.inomcrm.app.room.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kaisho.inomcrm.app.data.DataBaseLiveData
import com.kaisho.inomcrm.app.model.DataBasePOJO
import com.kaisho.inomcrm.app.room.DatabaseRoom
import com.kaisho.inomcrm.app.room.repository.DatabaseRepository
import com.kaisho.inomcrm.app.viewModel.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseViewModel(application: Application): AndroidViewModel(application) {

    private val databaseDao = DatabaseRoom.getDatabase(
        application
    ).getDatabaseDao()
    private val repository: DatabaseRepository
    var dataPOJO = DataBasePOJO(0)

    val getAllData: LiveData<List<DataBasePOJO>>

    init {
        repository = DatabaseRepository(databaseDao)
        getAllData = repository.getAllData
    }

    fun insertData(dataList: DataBasePOJO){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertData(dataList)
        }
    }

    fun updateData(dataBasePOJO: DataBasePOJO){
        viewModelScope.launch(Dispatchers.IO){
            databaseDao.updateData(dataBasePOJO)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }

    fun searchDatabase(): LiveData<List<DataBasePOJO>>{
        return repository.searchDatabase()
    }

}