package com.kaisho.inomcrm.app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.kaisho.inomcrm.R
import com.kaisho.inomcrm.databinding.FragmentEditBinding


class EditFragment : Fragment() {

    //NavArgs
    private val args by navArgs<EditFragmentArgs>()


    //Binding
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.model = args.model



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}