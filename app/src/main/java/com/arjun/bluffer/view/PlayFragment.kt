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
import com.arjun.bluffer.utils.Helper
import com.arjun.bluffer.viewmodel.SharedViewModel

class PlayFragment : Fragment(R.layout.fragment_play) {

    private lateinit var binding: FragmentPlayBinding
    private val rules: Helper = Helper()
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

        binding.aboutGame.text = rules.aboutGame
        binding.rulesList.text = rules.rulesList

        binding.playButton.setOnClickListener {
            binding.playButton.visibility = View.GONE
            binding.helpButton.visibility = View.GONE
            binding.playerNamesCardView.visibility = View.VISIBLE
        }
        binding.nextButton.setOnClickListener {
//            if (checkNames()) {
                sharedViewModel.playersName(
                    binding.playerOneName.editText!!.text.toString(),
                    binding.playerTwoName.editText!!.text.toString()
                )
//            } else {
//                Toast.makeText(activity, "Enter valid names!", Toast.LENGTH_SHORT).show()
//            }
            binding.playerNamesCardView.visibility = View.GONE
            Navigation.findNavController(view).navigate(R.id.action_playFragment_to_gameFragment)
        }
        binding.helpButton.setOnClickListener {
            if (binding.helperCardView.visibility == View.GONE) {
                binding.playButton.visibility = View.GONE
                binding.helperCardView.visibility = View.VISIBLE
            } else {
                binding.helperCardView.visibility = View.GONE
                binding.playButton.visibility = View.VISIBLE
            }
        }
        binding.closeButton.setOnClickListener {
            binding.helperCardView.visibility = View.GONE
            binding.playButton.visibility = View.VISIBLE
        }
    }
//    private fun checkNames(): Boolean {
//        return binding.playerOneName.editText != null && binding.playerTwoName.editText != null
//    }

}