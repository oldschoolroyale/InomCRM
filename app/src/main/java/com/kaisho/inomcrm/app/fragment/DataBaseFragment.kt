package com.kaisho.inomcrm.app.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaisho.inomcrm.R
import com.kaisho.inomcrm.app.adapter.DataBaseAdapter
import com.kaisho.inomcrm.app.data.DataBaseLiveData
import com.kaisho.inomcrm.app.model.DataBasePOJO
import com.kaisho.inomcrm.app.model.NotePOJO
import com.kaisho.inomcrm.app.room.viewModel.AccountViewModel
import com.kaisho.inomcrm.app.room.viewModel.DatabaseViewModel
import com.kaisho.inomcrm.app.viewModel.SharedViewModel
import com.kaisho.inomcrm.databinding.FragmentDataBaseBinding
import kotlinx.android.synthetic.main.fragment_data_base.view.*


class DataBaseFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object{
        private lateinit var TOWN : String
        private lateinit var REGION: String
    }

    //Binding
    private var _binding: FragmentDataBaseBinding? = null
    private val binding get() = _binding!!

    //viewModels
    private val accountViewModel by viewModels<AccountViewModel>()
    private val sharedViewModel by viewModels<SharedViewModel>()
    private val databaseViewModel by viewModels<DatabaseViewModel>()

    //LiveData
    private val liveData = DataBaseLiveData()

    //Observer
    private lateinit var observer: Observer<ArrayList<DataBasePOJO>>

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

        binding.activityDataBaseFb.setOnClickListener {
            databaseViewModel.deleteAll()
        }

        //recyclerViewSetUp
        adapter = DataBaseAdapter(databaseViewModel)
        binding.fragmentDataBaseRecyclerView.adapter = adapter
        binding.fragmentDataBaseRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        //DataRetrieve
        observer = Observer {
            sharedViewModel.checkIfDataFragmentEmpty(it)
            adapter.newList(it)
        }
        databaseViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            sharedViewModel.checkFloatingActionButton(it)

        })

        accountViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            REGION = it[0].region.toString().split(", ")[0]
            TOWN = it[0].town.toString()
        })

        liveData.observe(viewLifecycleOwner,observer)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.data_base_menu, menu)
        val item = menu.findItem(R.id.data_base_menu_spinner)
        val spinner = item.actionView as Spinner

        //Spinner Adapter
        val categoryAdapter : ArrayAdapter<CharSequence> =
            context?.let { ArrayAdapter.createFromResource(it, R.array.dataCategory, R.layout.custom_spinner) } as ArrayAdapter<CharSequence>
        categoryAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinner.adapter = categoryAdapter

        spinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent?.id == R.id.data_base_menu_spinner){
            liveData.getData(TOWN,
                REGION,
                parent.getItemAtPosition(position).toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        liveData.removeObserver(observer)
        _binding = null
    }
}