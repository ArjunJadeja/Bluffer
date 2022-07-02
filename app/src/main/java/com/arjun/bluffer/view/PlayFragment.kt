package com.arjun.bluffer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.arjun.bluffer.R
import com.arjun.bluffer.databinding.FragmentPlayBinding
import com.arjun.bluffer.viewmodel.SharedViewModel

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

        binding.playButton.setOnClickListener {
            binding.playButton.visibility = View.GONE
            binding.playerNamesCardView.visibility = View.VISIBLE
        }
        binding.nextButton.setOnClickListener {
            sharedViewModel.playersName(binding.playerOneName.editText!!.text.toString(), binding.playerTwoName.editText!!.text.toString())
            binding.playerNamesCardView.visibility = View.GONE
            Navigation.findNavController(view).navigate(R.id.action_playFragment_to_gameFragment)
        }
    }

}