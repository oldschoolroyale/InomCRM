package com.kaisho.inomcrm.app.room.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kaisho.inomcrm.app.model.DataBasePOJO
import com.kaisho.inomcrm.app.room.DatabaseRoom
import com.kaisho.inomcrm.app.room.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseViewModel(application: Application): AndroidViewModel(application) {

    private val databaseDao = DatabaseRoom.getDatabase(
        application
    ).getDatabaseDao()
    private val repository: DatabaseRepository

    val getAllData: LiveData<List<DataBasePOJO>>
    var dataPOJO = DataBasePOJO()

    init {
        repository = DatabaseRepository(databaseDao)
        getAllData = repository.getAllData
    }

    fun insertData(dataBasePOJO: DataBasePOJO){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertData(dataBasePOJO)
        }
    }

    fun deleteData(dataBasePOJO: DataBasePOJO){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(dataBasePOJO)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }


}