package com.kaisho.inomcrm.app.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.kaisho.inomcrm.R
import com.kaisho.inomcrm.app.adapter.PlanAdapter
import com.kaisho.inomcrm.app.model.DataBasePOJO
import com.kaisho.inomcrm.app.room.viewModel.DatabaseViewModel
import com.kaisho.inomcrm.app.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_plan.*
import kotlinx.android.synthetic.main.fragment_plan.view.*
import java.util.*

class PlanFragment : Fragment() {

    //viewModels
    private val dataBaseViewModel by viewModels<DatabaseViewModel>()
    private val sharedViewModel by viewModels<SharedViewModel>()

    //Observer
    private lateinit var searchObserver: Observer<List<DataBasePOJO>>

    //Adapter
    private val adapter = PlanAdapter()

    //List
    private var sendList = emptyList<DataBasePOJO>()

    companion object{
        lateinit var YEAR: String
        lateinit var MONTH: String
        lateinit var DAY: String
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val view = inflater.inflate(R.layout.fragment_plan, container, false)

        view.fragmentPlanRecyclerView.adapter = adapter
        view.fragmentPlanRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        searchObserver = Observer {
            adapter.newList(it)
            this.sendList = it
        }
        setHasOptionsMenu(true)

        YEAR = sharedViewModel.yearString
        MONTH = sharedViewModel.monthString
        DAY = sharedViewModel.dayString

        view.fragment_plan_add_calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            YEAR = year.toString()
            MONTH = (month + 1).toString()
            DAY = dayOfMonth.toString()
        }

        //init
        dataBaseViewModel.searchDatabase().observe(viewLifecycleOwner, searchObserver)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.plan_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.planMenuAdd){
            sendNote()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBaseViewModel.searchDatabase().removeObserver(searchObserver)
    }

   private fun sendNote(){
       for (element in sendList){
           val reference = FirebaseDatabase.getInstance().reference.child("Notes")
               .child(sharedViewModel.user).child(YEAR).child(MONTH)
               .child(DAY)

           val randomId = "Note${randomInt()}"
           element.id = randomId
           reference.child(randomId).setValue(element)
       }
       findNavController().navigate(R.id.action_planFragment_to_fragmentList)
   }

    private fun randomInt(): Int{
        return Random().nextInt()
    }
}