package com.kaisho.inomcrm.app.data

import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import com.kaisho.inomcrm.app.model.DataBasePOJO

class DataBaseLiveData: LiveData<ArrayList<DataBasePOJO>>() {
    companion object{
        private const val DATA_BASE = "DataBase"
        private lateinit var reference: DatabaseReference
        private var loadList = ArrayList<DataBasePOJO>()
    }

    fun getData(town: String, region: String, type: String){
        //Firebase conn
        reference = FirebaseDatabase.getInstance().reference
            .child(DATA_BASE).child(town).child(region).child(type)

        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                loadList.clear()
                for (i in snapshot.children){
                    val model = i.getValue(DataBasePOJO::class.java)
                    loadList.add(model!!)
                }
                value = loadList
            }

        })
    }
}