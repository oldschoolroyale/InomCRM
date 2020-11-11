package com.kaisho.inomcrm.app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.kaisho.inomcrm.R
import com.kaisho.inomcrm.databinding.FragmentUpdateBinding


class UpdateFragment : Fragment() {

    //Binding
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    //NavArgs
    private val args by navArgs<UpdateFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.model = args.note

        (activity as AppCompatActivity).supportActionBar?.show()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}