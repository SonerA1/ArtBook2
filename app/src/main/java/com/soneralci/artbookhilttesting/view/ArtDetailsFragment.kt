package com.soneralci.artbookhilttesting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.soneralci.artbookhilttesting.R
import com.soneralci.artbookhilttesting.databinding.FragmentArtDetailsBinding
import com.soneralci.artbookhilttesting.util.Status
import com.soneralci.artbookhilttesting.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    val glide : RequestManager
) : Fragment(R.layout.fragment_art_details) {

    lateinit var viewModel : ArtViewModel
    private var fragmentBinding : FragmentArtDetailsBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding
        subscribeToObservers()


        //Actions for next
        binding.artImageView.setOnClickListener{
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageAPIFragment())
        }
        //Actions for next END

        //back push
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.saveButton.setOnClickListener{
            viewModel.makeArt(binding.nameText.text.toString(), binding.artistText.text.toString(),binding.yearText.text.toString())
        }

    }//back push END

    private fun subscribeToObservers(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            fragmentBinding?.let {binding ->
                glide.load(url).into(binding.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsetArtMsg()
                }
                Status.ERROR ->{
                    Toast.makeText(requireContext(),it.message ?: "Error", Toast.LENGTH_LONG).show()
                }
                Status.LOADING ->{

                }
            }
        })

    }


    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}