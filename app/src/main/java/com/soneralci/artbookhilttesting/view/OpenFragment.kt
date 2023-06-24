package com.soneralci.artbookhilttesting.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.soneralci.artbookhilttesting.R

import com.soneralci.artbookhilttesting.databinding.OpenFragmentBinding
import javax.inject.Inject

class OpenFragment @Inject constructor(

) : Fragment(R.layout.open_fragment) {

    private var fragmentBinding: OpenFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = OpenFragmentBinding.bind(view)
        fragmentBinding = binding

        binding.button.setOnClickListener {
            val action = OpenFragmentDirections.actionOpenFragmentToArtFragment()
            Navigation.findNavController(view).navigate(action)
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }

    }
}