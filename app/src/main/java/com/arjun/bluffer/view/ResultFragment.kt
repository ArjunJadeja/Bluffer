package com.arjun.bluffer.view

import android.os.Bundle
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

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    private val viewModel: ResultViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

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

        sharedViewModel.explainer.observe(viewLifecycleOwner) {
            explainer = it.uppercase()
        }

        sharedViewModel.guesser.observe(viewLifecycleOwner) {
            guesser = it.uppercase()
            binding.guesserQuestion.text = "WHAT DID $guesser SAY ?"
        }

        viewModel.result.observe(viewLifecycleOwner) {
            winner = if (it == true) {
                guesser
            } else {
                explainer
            }
        }

        binding.explainerBluffButton.setOnClickListener {
            explainedCorrectly = false
            explainerCardGone()
        }

        binding.explainerTruthButton.setOnClickListener {
            explainedCorrectly = true
            explainerCardGone()
        }

        binding.guesserBluffButton.setOnClickListener {
            guessedCorrectly = false
            guesserCardGone()
        }

        binding.guesserTruthButton.setOnClickListener {
            guessedCorrectly = true
            guesserCardGone()
        }

        binding.playAgainButton.setOnClickListener {
            startGame()
        }

        binding.newGameButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_playFragment)
        }

        binding.exitButton.setOnClickListener {
            showExitCard()
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
                    if (binding.exitCard.isVisible) {
                        hideExitCard()
                    } else if (binding.guesserCardView.isVisible) {
                        showExplainerCard()
                    } else if (binding.explainerCardView.isVisible) {
                        findNavController().navigate(R.id.action_resultFragment_to_playFragment)
                    } else {
                        showExitCard()
                    }
                }
            })

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
        binding.winnerCard.visibility = View.VISIBLE
        viewModel.playerResponse(explainedCorrectly, guessedCorrectly)
        binding.greetText.text = "CONGRATULATIONS!\n$winner YOU WON"
    }

    private fun startGame() {
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