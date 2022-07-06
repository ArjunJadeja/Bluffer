package com.arjun.bluffer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.arjun.bluffer.R
import com.arjun.bluffer.databinding.FragmentPlayBinding
import com.arjun.bluffer.utils.Helper
import com.arjun.bluffer.viewmodel.SharedViewModel
import kotlin.system.exitProcess

class PlayFragment : Fragment(R.layout.fragment_play) {

    private lateinit var binding: FragmentPlayBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val helper: Helper = Helper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayBinding.bind(view)

        binding.aboutGame.text = helper.aboutGame
        binding.discretionAdvised.text = helper.discretionAdvised
        binding.rulesList.text = helper.rulesList

        binding.playButton.setOnClickListener {
            showPlayerNamesCard()
        }

        binding.helpButton.setOnClickListener {
            if (helperCardVisible()) {
                hideHelperCard()
            } else {
                showHelperCard()
            }
        }

        binding.nextButton.setOnClickListener {
            if (invalidPlayerNames()) {
                Toast.makeText(activity, R.string.toast_enter_names, Toast.LENGTH_SHORT).show()
            } else {
                loadGame()
            }
        }

        binding.closeButton.setOnClickListener {
            hideHelperCard()
        }

        binding.cancelButton.setOnClickListener {
            hideExitCard()
        }

        binding.exitConfirmButton.setOnClickListener {
            exitProcess(R.integer.integer_zero)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (playerNamesCardVisible()) {
                        hidePlayerNamesCard()
                    } else if (helperCardVisible()) {
                        hideHelperCard()
                    } else {
                        showExitCard()
                    }
                }
            })
    }

    private fun helperCardVisible(): Boolean {
        return binding.helperCardView.isVisible
    }

    private fun playerNamesCardVisible(): Boolean {
        return binding.playerNamesCardView.isVisible
    }

    private fun invalidPlayerNames(): Boolean {
        return binding.playerOneName.editText?.text?.trim().toString().isEmpty() ||
                binding.playerTwoName.editText?.text?.trim().toString().isEmpty()
    }

    private fun showHelperCard() {
        binding.playButton.visibility = View.GONE
        binding.helperCardView.visibility = View.VISIBLE
    }

    private fun hideHelperCard() {
        binding.helperCardView.visibility = View.GONE
        binding.playButton.visibility = View.VISIBLE
    }

    private fun showPlayerNamesCard() {
        binding.playButton.visibility = View.GONE
        binding.helpButton.visibility = View.GONE
        binding.playerNamesCardView.visibility = View.VISIBLE
    }

    private fun hidePlayerNamesCard() {
        binding.playerNamesCardView.visibility = View.GONE
        binding.playButton.visibility = View.VISIBLE
        binding.helpButton.visibility = View.VISIBLE
    }

    private fun showExitCard() {
        binding.playButton.visibility = View.GONE
        binding.helpButton.visibility = View.GONE
        binding.exitCard.visibility = View.VISIBLE
    }

    private fun hideExitCard() {
        binding.playButton.visibility = View.VISIBLE
        binding.helpButton.visibility = View.VISIBLE
        binding.exitCard.visibility = View.GONE
    }

    private fun loadGame() {
        sharedViewModel.getNewImage()
        sharedViewModel.playersName(
            binding.playerOneName.editText!!.text.toString().trim(),
            binding.playerTwoName.editText!!.text.toString().trim()
        )
        binding.playerNamesCardView.visibility = View.GONE
        findNavController().navigate(R.id.action_playFragment_to_gameFragment)
    }

}