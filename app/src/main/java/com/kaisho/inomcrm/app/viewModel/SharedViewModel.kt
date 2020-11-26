package com.kaisho.inomcrm.app.viewModel

import android.app.AlertDialog
import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.kaisho.inomcrm.app.data.DataBaseLiveData
import com.kaisho.inomcrm.app.model.DataBasePOJO
import com.kaisho.inomcrm.app.model.NoteModel
import com.kaisho.inomcrm.app.room.viewModel.DatabaseViewModel
import java.text.SimpleDateFormat
import java.util.*

class SharedViewModel(application: Application): AndroidViewModel(application) {

    //EmptyDataBase
    val emptyDataBase: MutableLiveData<Boolean> = MutableLiveData(false)

    //FAB
    val emptyFab: MutableLiveData<Boolean> = MutableLiveData(false)

    //DataBaseInfo
    val town: MutableLiveData<String> = MutableLiveData()
    val region: MutableLiveData<String> = MutableLiveData()
    val doctorType: MutableLiveData<String> = MutableLiveData()
    lateinit var regionArray : Array<String>
    lateinit var townArray: Array<String>


    fun insertDataBaseInfo(town: String, region: String, doctorType: String){
        this.town.value = town
        this.region.value = region
        this.doctorType.value = doctorType
    }

    fun itemSelect(type: String){
        DataBaseLiveData(DatabaseViewModel(getApplication()))
            .getData(town.value!!,
            region.value!!,
            type)
    }

    fun checkIfDatabaseEmpty(noteList: List<NoteModel>){
        emptyDataBase.value = noteList.isEmpty()
    }

    fun checkIfDataFragmentEmpty(dataList: List<DataBasePOJO>){
        emptyDataBase.value = dataList.isEmpty()
    }

    fun checkFloatingActionButton(fb: List<DataBasePOJO>){
        emptyFab.value = fb.isEmpty()
    }

    private fun startLoading(town: String, region: String, type: String){
        DataBaseLiveData(DatabaseViewModel(getApplication())).getData(town, region, type)
    }

    private fun regionAlert(view: View){
        lateinit var dialog: AlertDialog
        val list = regionArray

        val town = town.value!!
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Выберите")
        builder.setSingleChoiceItems(list, -1){ _, which ->
            startLoading(town,
                list[which],
                "Doctor")

            dialog.dismiss()
        }
        builder.setNeutralButton("Отмена"){_,_ ->
            dialog.cancel()
        }
        dialog = builder.create()
        dialog.show()
    }

    fun townAlert(view: View){
        lateinit var dialog: AlertDialog
        val list = townArray
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Выберите")
        builder.setSingleChoiceItems(list, -1){ _, which ->
            town.value = list[which]
            dialog.dismiss()
            regionAlert(view)
        }
        builder.setNeutralButton("Отмена"){_,_ ->
            dialog.cancel()
        }
        dialog = builder.create()
        dialog.show()
    }

    //date
    var yearString: String = SimpleDateFormat("yyyy").format(Calendar.getInstance().time)
    var monthString: String = SimpleDateFormat("M").format(Calendar.getInstance().time)
    var dayString: String = SimpleDateFormat("d").format(Calendar.getInstance().time)

    //userID
    var user = FirebaseAuth.getInstance().currentUser?.uid!!


}