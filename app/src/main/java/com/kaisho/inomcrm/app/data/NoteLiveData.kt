package com.kaisho.inomcrm.app.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import com.kaisho.inomcrm.app.model.NotePOJO
import com.kaisho.inomcrm.app.viewModel.SharedViewModel
import kotlin.collections.ArrayList

class NoteLiveData(private val sharedViewModel: SharedViewModel) : LiveData<ArrayList<NotePOJO>>(){


    //Database connect
    private lateinit var reference : DatabaseReference

    override fun onActive() {
        super.onActive()
        //Database connection settings
        reference = FirebaseDatabase.getInstance().reference.child("Notes").child(sharedViewModel.user!!)
            .child(sharedViewModel.yearString).child(sharedViewModel.monthString).child(sharedViewModel.dayString)


        Log.d("MyLog", "onActive")
        connectToDB()
    }

    override fun onInactive() {
        super.onInactive()
        reference.onDisconnect()
        Log.d("MyLog", "OnDisconnect")
    }

    private fun connectToDB(){
        val retrieveList = ArrayList<NotePOJO>()
        reference.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                retrieveList.clear()
                for (i in snapshot.children){
                    val model = i.getValue(NotePOJO::class.java)
                    retrieveList.add(model!!)
                }
                value = retrieveList
            }
        })
    }

}