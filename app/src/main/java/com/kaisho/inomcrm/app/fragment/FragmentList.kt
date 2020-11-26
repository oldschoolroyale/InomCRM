package com.kaisho.inomcrm.app.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.kaisho.inomcrm.R
import com.kaisho.inomcrm.app.activity.RegistrationActivity
import com.kaisho.inomcrm.app.adapter.NoteAdapter
import com.kaisho.inomcrm.app.data.NoteLiveData
import com.kaisho.inomcrm.app.model.NoteModel
import com.kaisho.inomcrm.app.room.viewModel.AccountViewModel
import com.kaisho.inomcrm.app.viewModel.SharedViewModel
import com.kaisho.inomcrm.databinding.FragmentListBinding


class FragmentList : Fragment() {
    //viewModels
    private val sharedViewModel by viewModels<SharedViewModel>()
    private val accountViewModel by viewModels<AccountViewModel>()

    //LiveData
    private lateinit var liveData: NoteLiveData

    //Binding
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    //Observer
    private lateinit var observer: Observer<List<NoteModel>>

    //Adapter
    private val adapter = NoteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container , false)
        binding.lifecycleOwner = this
        binding.accountViewModel = accountViewModel
        binding.activity = this

        (activity as AppCompatActivity).supportActionBar?.hide()

        //recycler
        binding.fragmentNoteRecyclerView.adapter = adapter
        binding.fragmentNoteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.fragmentNoteRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(),
            LinearLayoutManager.VERTICAL) )

        //Data
        liveData = NoteLiveData(sharedViewModel)
        observer = Observer {
            adapter.newList(it)
        }
        liveData.observe(viewLifecycleOwner, observer)

        binding.fragmentNoteViewDataCardView.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentList_to_dataBaseFragment)
        }

        return binding.root
    }

    fun signOut(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(requireActivity(), RegistrationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        liveData.removeObserver(observer)
        _binding = null
    }
}