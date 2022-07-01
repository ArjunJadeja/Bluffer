package com.arjun.bluffer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.arjun.bluffer.databinding.FragmentPlayBinding
import kotlin.system.exitProcess

class PlayFragment : Fragment(R.layout.fragment_play) {

    private lateinit var binding: FragmentPlayBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayBinding.bind(view)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    exitProcess(0)
                }
            })

        binding.playButton.setOnClickListener {
            binding.playButton.visibility = View.GONE
            binding.playerNamesCardView.visibility = View.VISIBLE
        }
        binding.nextButton.setOnClickListener {
            sharedViewModel.playersName(binding.playerOneName.editText!!.text.toString(), binding.playerTwoName.editText!!.text.toString())
            binding.playerNamesCardView.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            Navigation.findNavController(view).navigate(R.id.action_playFragment_to_gameFragment)
        }
    }

}