package com.kaisho.inomcrm.app.data

import android.util.Log
import com.google.firebase.database.*
import com.kaisho.inomcrm.app.model.DataBasePOJO
import com.kaisho.inomcrm.app.room.viewModel.DatabaseViewModel
import com.kaisho.inomcrm.app.viewModel.SharedViewModel

class DataBaseLiveData(private val databaseViewModel: DatabaseViewModel) {
    companion object{
        private const val DATA_BASE = "Database"
        private lateinit var reference: DatabaseReference
    }

    fun getData(town: String, region: String, type: String){

        databaseViewModel.deleteAll()

        //Firebase conn
        reference = FirebaseDatabase.getInstance().reference
            .child(DATA_BASE).child(town).child(region).child(type)

        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("MyLog", "Retrieving data to Database")
                for (i in snapshot.children){
                    val model = DataBasePOJO(
                        0,
                        i.child("id").value.toString(),
                        i.child("name").value.toString(),
                        i.child("address").value.toString(),
                        i.child("status").value.toString(),
                        i.child("phone").value.toString(),
                        i.child("specialization").value.toString(),
                        i.child("category").value.toString(),
                        i.child("state").value.toString(),
                        i.child("employee").value.toString(),
                        i.child("owner").value.toString(),
                        0,
                        textTown = town,
                        textRegion = region,
                        textType = type
                    )
                    databaseViewModel.insertData(model)
                }

            }

        })
    }
}