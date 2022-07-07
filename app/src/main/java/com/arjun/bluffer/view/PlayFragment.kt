package com.arjun.bluffer.view

import android.media.SoundPool
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

class PlayFragment : Fragment() {

    private lateinit var binding: FragmentPlayBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val helper: Helper = Helper()

    private val soundPool = SoundPool.Builder().setMaxStreams(2).build()
    private var clickSound = R.integer.integer_zero
    private var wrongSound = R.integer.integer_zero

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

        clickSound = soundPool.load(context, R.raw.click, 1)
        wrongSound = soundPool.load(context, R.raw.wrong, 1)

        binding.playButton.setOnClickListener {
            playClickSound()
            showPlayerNamesCard()
        }

        binding.helpButton.setOnClickListener {
            playClickSound()
            if (helperCardVisible()) {
                hideHelperCard()
            } else {
                showHelperCard()
            }
        }

        binding.nextButton.setOnClickListener {
            if (invalidPlayerNames()) {
                playWrongSound()
                Toast.makeText(activity, R.string.toast_enter_names, Toast.LENGTH_SHORT).show()
            } else {
                playClickSound()
                loadGame()
            }
        }

        binding.closeButton.setOnClickListener {
            playClickSound()
            hideHelperCard()
        }

        binding.cancelButton.setOnClickListener {
            playClickSound()
            hideExitCard()
        }

        binding.exitConfirmButton.setOnClickListener {
            exitProcess(R.integer.integer_zero)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    playClickSound()
                    if (playerNamesCardVisible()) {
                        hidePlayerNamesCard()
                    } else if (helperCardVisible()) {
                        hideHelperCard()
                    } else {
                        showExitCard()
                    }
                }
            }
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

    private fun playClickSound() {
        soundPool.play(clickSound, 1f, 1f, 1, 0, 1f)
    }

    private fun playWrongSound() {
        soundPool.play(wrongSound, 1f, 1f, 1, 0, 1f)
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