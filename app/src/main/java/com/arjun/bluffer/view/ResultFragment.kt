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

    private var explain = false
    private var guess = false

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

        sharedViewModel.guesser.observe(viewLifecycleOwner) {
            binding.guesserQuestion.text = "WHAT DID ${it.uppercase()} SAY ?"
            guesser = it.uppercase()
        }
        sharedViewModel.explainer.observe(viewLifecycleOwner) {
            explainer = it.uppercase()
        }
        viewModel.result.observe(viewLifecycleOwner) {
            winner = if (it == true) {
                guesser
            } else {
                explainer
            }
        }

        binding.explainerBluffButton.setOnClickListener {
            explain = false
            explainerCardGone()
        }
        binding.explainerTruthButton.setOnClickListener {
            explain = true
            explainerCardGone()
        }
        binding.guesserBluffButton.setOnClickListener {
            guess = false
            guesserCardGone()
        }
        binding.guesserTruthButton.setOnClickListener {
            guess = true
            guesserCardGone()
        }
        binding.playAgainButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
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
            exitProcess(0)
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
        binding.congoCard.visibility = View.VISIBLE
        viewModel.playerResponse(explain, guess)
        binding.greetText.text = "CONGRATULATIONS!\n$winner YOU WON"
    }

    private fun showExitCard() {
        binding.congoCard.visibility = View.GONE
        binding.exitCard.visibility = View.VISIBLE
    }

    private fun hideExitCard() {
        binding.exitCard.visibility = View.GONE
        binding.congoCard.visibility = View.VISIBLE
    }

}