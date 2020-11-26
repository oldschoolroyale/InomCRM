package com.kaisho.inomcrm.app.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaisho.inomcrm.R
import com.kaisho.inomcrm.app.adapter.DataBaseAdapter
import com.kaisho.inomcrm.app.model.AccountPOJO
import com.kaisho.inomcrm.app.model.DataBasePOJO
import com.kaisho.inomcrm.app.room.viewModel.AccountViewModel
import com.kaisho.inomcrm.app.room.viewModel.DatabaseViewModel
import com.kaisho.inomcrm.app.viewModel.SharedViewModel
import com.kaisho.inomcrm.databinding.FragmentDataBaseBinding


class DataBaseFragment : Fragment() {

    companion object{
        private const val DOCTOR = "Doctor"
        private const val PHARMACY = "Pharmacy"
    }

    //Binding
    private var _binding: FragmentDataBaseBinding? = null
    private val binding get() = _binding!!

    //viewModels
    private val accountViewModel by viewModels<AccountViewModel>()
    private val sharedViewModel by viewModels<SharedViewModel>()
    private val databaseViewModel by viewModels<DatabaseViewModel>()


    //Observer
    private lateinit var searchObserver: Observer<List<DataBasePOJO>>
    private lateinit var allDataObserver: Observer<List<DataBasePOJO>>
    private lateinit var accountObserver: Observer<List<AccountPOJO>>

    //Adapter
    private lateinit var adapter: DataBaseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentDataBaseBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel

        //Layout setUp
        (activity as AppCompatActivity).supportActionBar?.show()
        setHasOptionsMenu(true)

        //recyclerViewSetUp
        adapter = DataBaseAdapter(databaseViewModel)
        binding.fragmentDataBaseRecyclerView.adapter = adapter
        binding.fragmentDataBaseRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        //Observers
        accountObserver = Observer {
            sharedViewModel.townArray =
                it[0].town.toString().split(", ").toTypedArray()
            sharedViewModel.regionArray =
                it[0].region.toString().split(", ").toTypedArray()
        }
        allDataObserver = Observer {
            sharedViewModel.checkIfDataFragmentEmpty(it)
            adapter.newList(it)
            if (it.isNotEmpty()){
                sharedViewModel.insertDataBaseInfo(
                    it[0].textTown!!,
                    it[0].textRegion!!,
                    it[0].textType!!
                )
            }

        }
        searchObserver = Observer {
            sharedViewModel.checkFloatingActionButton(it)
        }


        //ObserverInit
        databaseViewModel.searchDatabase().observe(viewLifecycleOwner,
            searchObserver)
        databaseViewModel.getAllData.observe(viewLifecycleOwner,
        allDataObserver)
        accountViewModel.getAllData.observe(viewLifecycleOwner,
        accountObserver)

        //Fab click
        binding.activityDataBasePlanFab.setOnClickListener {
            findNavController().navigate(R.id.action_dataBaseFragment_to_planFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.data_base_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.data_base_menu_doctor -> {
                    sharedViewModel.itemSelect(DOCTOR)
            }
            R.id.data_base_menu_pharmacy -> {
                    sharedViewModel.itemSelect(PHARMACY)
            }
            R.id.data_base_menu_add_doctor -> findNavController().navigate(R.id.action_dataBaseFragment_to_doctorAddFragment)
            R.id.data_base_menu_add_pharmacy -> findNavController().navigate(R.id.action_dataBaseFragment_to_pharmacyAddFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        databaseViewModel.searchDatabase().removeObserver(searchObserver)
        databaseViewModel.getAllData.removeObserver(allDataObserver)
        accountViewModel.getAllData.removeObserver(accountObserver)
    }
}