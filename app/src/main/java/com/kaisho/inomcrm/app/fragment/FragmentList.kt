package com.kaisho.inomcrm.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaisho.inomcrm.R
import com.kaisho.inomcrm.app.adapter.NotesAdapter
import com.kaisho.inomcrm.app.adapter.ViewPagerAdapter
import com.kaisho.inomcrm.app.data.NoteLiveData
import com.kaisho.inomcrm.app.data.ViewPagerData
import com.kaisho.inomcrm.app.model.NotePOJO
import com.kaisho.inomcrm.app.room.viewModel.AccountViewModel
import com.kaisho.inomcrm.app.viewModel.SharedViewModel
import com.kaisho.inomcrm.databinding.FragmentListBinding
import com.squareup.picasso.Picasso


class FragmentList : Fragment() {
    //viewModels
    private val sharedViewModel by viewModels<SharedViewModel>()
    private val accountViewModel by viewModels<AccountViewModel>()

    //LiveData
    private lateinit var liveData: NoteLiveData
    private lateinit var liveViewPager: ViewPagerData

    //Binding
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    //Adapter
    private var adapter = NotesAdapter()
    private var viewPagerAdapter = ViewPagerAdapter()

    //Observer
    private lateinit var observer: Observer<ArrayList<NotePOJO>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container , false)
        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel
        binding.accountViewModel = accountViewModel

        (activity as AppCompatActivity).supportActionBar?.hide()

        //ViewPager
        binding.fragmentNoteViewPager2.adapter = viewPagerAdapter
        liveViewPager = ViewPagerData()
        liveViewPager.observe(viewLifecycleOwner, Observer {
            viewPagerAdapter.newList(it)
        })
        //recycler
        binding.fragmentNoteRecyclerView.adapter = adapter
        binding.fragmentNoteRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        //Data
        liveData = NoteLiveData(sharedViewModel)
        observer = Observer {
            adapter.newArray(it)
            sharedViewModel.checkIfDatabaseEmpty(it)
        }
        accountViewModel.getAllData.observe(viewLifecycleOwner, Observer { url ->
            url[0].image.let { Picasso.with(requireContext()).load(it).placeholder(R.drawable.logo)
                .into(binding.fragmentListProfileImage)}
            liveViewPager.getData(url[0].medications.toString())
        })
        liveData.observe(viewLifecycleOwner, observer)

        binding.fragmentNoteViewDataCardView.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentList_to_dataBaseFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        liveData.removeObserver(observer)
        _binding = null
    }
}