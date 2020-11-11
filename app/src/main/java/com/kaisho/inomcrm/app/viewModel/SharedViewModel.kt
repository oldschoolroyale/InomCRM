package com.kaisho.inomcrm.app.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.kaisho.inomcrm.app.model.AccountPOJO
import com.kaisho.inomcrm.app.model.DataBasePOJO
import com.kaisho.inomcrm.app.model.NotePOJO
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SharedViewModel(application: Application): AndroidViewModel(application) {

    //EmptyDataBase
    val emptyDataBase: MutableLiveData<Boolean> = MutableLiveData(false)

    //FAB
    val emptyFab: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(noteList: List<NotePOJO>){
        emptyDataBase.value = noteList.isEmpty()
    }

    fun checkIfDataFragmentEmpty(dataList: ArrayList<DataBasePOJO>){
        emptyDataBase.value = dataList.isEmpty()
    }
    fun checkFloatingActionButton(fb: List<DataBasePOJO>){
        emptyFab.value = fb.isEmpty()
    }

    //date
    var yearString: String = SimpleDateFormat("yyyy").format(Calendar.getInstance().time)
    var monthString: String = SimpleDateFormat("M").format(Calendar.getInstance().time)
    var dayString: String = SimpleDateFormat("d").format(Calendar.getInstance().time)

    //userID
    var user = FirebaseAuth.getInstance().currentUser?.uid!!
}