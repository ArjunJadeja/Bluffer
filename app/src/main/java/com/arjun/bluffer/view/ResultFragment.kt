package com.arjun.bluffer.view

import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.arjun.bluffer.R
import com.arjun.bluffer.databinding.FragmentResultBinding
import com.arjun.bluffer.viewmodel.ResultViewModel
import com.arjun.bluffer.viewmodel.SharedViewModel
import kotlin.system.exitProcess

private const val DELAY = 1000L

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    private val viewModel: ResultViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val soundPool = SoundPool.Builder().setMaxStreams(2).build()
    private var clickSound = R.integer.integer_zero
    private var finishSound = R.integer.integer_zero

    private var explainedCorrectly = false
    private var guessedCorrectly = false

    private lateinit var explainer: String
    private lateinit var guesser: String

    private lateinit var winner: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentResultBinding.bind(view)

        clickSound = soundPool.load(context, R.raw.click, 1)
        finishSound = soundPool.load(context, R.raw.finish, 1)

        sharedViewModel.explainer.observe(viewLifecycleOwner) {
            explainer = it.uppercase()
        }

        sharedViewModel.guesser.observe(viewLifecycleOwner) {
            guesser = it.uppercase()
            binding.guesserQuestion.text = "WHAT DID $guesser SAY?"
        }

        viewModel.result.observe(viewLifecycleOwner) {
            winner = if (it == true) {
                guesser
            } else {
                explainer
            }
        }

        binding.explainerBluffButton.setOnClickListener {
            playClickSound()
            explainedCorrectly = false
            explainerCardGone()
        }

        binding.explainerTruthButton.setOnClickListener {
            playClickSound()
            explainedCorrectly = true
            explainerCardGone()
        }

        binding.guesserBluffButton.setOnClickListener {
            playClickSound()
            guessedCorrectly = false
            guesserCardGone()
            showResultCard()
        }

        binding.guesserTruthButton.setOnClickListener {
            playClickSound()
            guessedCorrectly = true
            guesserCardGone()
            showResultCard()
        }

        binding.playAgainButton.setOnClickListener {
            playClickSound()
            loadGame()
        }

        binding.newGameButton.setOnClickListener {
            playClickSound()
            findNavController().navigate(R.id.action_resultFragment_to_playFragment)
        }

        binding.exitButton.setOnClickListener {
            playClickSound()
            showExitCard()
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
                    if (binding.guesserCardView.isVisible) {
                        showExplainerCard()
                    } else if (binding.exitCard.isVisible) {
                        hideExitCard()
                    } else {
                        findNavController().navigate(R.id.action_resultFragment_to_playFragment)
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
        if (sharedViewModel.soundOn.value!!) {
            soundPool.play(clickSound, 1f, 1f, 1, 0, 1f)
        }
    }

    private fun playFinishSound() {
        if (sharedViewModel.soundOn.value!!) {
            soundPool.play(finishSound, 1f, 1f, 1, 0, 1f)
        }
    }

    private fun showExplainerCard() {
        binding.guesserCardView.visibility = View.GONE
        binding.explainerCardView.visibility = View.VISIBLE
    }

    private fun explainerCardGone() {
        binding.explainerCardView.visibility = View.GONE
        binding.guesserCardView.visibility = View.VISIBLE
    }

    private fun guesserCardGone() {
        binding.guesserCardView.visibility = View.GONE
        binding.resultProgressBar.visibility = View.VISIBLE
        viewModel.playerResponse(explainedCorrectly, guessedCorrectly)
    }

    private fun showResultCard() {
        Handler(Looper.getMainLooper()).postDelayed({
            playFinishSound()
            binding.resultProgressBar.visibility = View.GONE
            binding.greetText.text = "CONGRATULATIONS!\n$winner YOU WON"
            binding.winnerCard.visibility = View.VISIBLE
        }, DELAY)
    }

    private fun loadGame() {
        sharedViewModel.getNewImage()
        findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
    }

    private fun showExitCard() {
        binding.winnerCard.visibility = View.GONE
        binding.exitCard.visibility = View.VISIBLE
    }

    private fun hideExitCard() {
        binding.exitCard.visibility = View.GONE
        binding.winnerCard.visibility = View.VISIBLE
    }

}