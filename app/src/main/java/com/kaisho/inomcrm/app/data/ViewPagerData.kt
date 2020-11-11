package com.kaisho.inomcrm.app.data

import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import com.kaisho.inomcrm.app.model.ViewPagerPOJO

class ViewPagerData: LiveData<ArrayList<ViewPagerPOJO>>() {


    companion object{
        private const val BLANK = "Blank"
        private lateinit var reference: DatabaseReference
        private var list = ArrayList<ViewPagerPOJO>()
        private lateinit var TYPE: String
    }

     fun getData(type: String){
         TYPE = type.split(" ")[0]

        reference = FirebaseDatabase.getInstance().reference
            .child(BLANK).child(TYPE)

        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children){
                    val model = i.getValue(ViewPagerPOJO::class.java)
                    list.add(model!!)
                }
                value = list
            }

        })
    }
}