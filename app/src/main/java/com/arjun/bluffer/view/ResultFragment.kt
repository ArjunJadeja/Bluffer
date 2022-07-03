package com.arjun.bluffer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.arjun.bluffer.R
import com.arjun.bluffer.databinding.FragmentResultBinding
import com.arjun.bluffer.viewmodel.ResultViewModel
import kotlin.system.exitProcess

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private val viewModel: ResultViewModel by viewModels()

    private var explain = false
    private var guess = false

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

        binding.explainerBluffButton.setOnClickListener {
            binding.explainerCardView.visibility = View.GONE
            binding.guesserCardView.visibility = View.VISIBLE
        }
        binding.explainerTruthButton.setOnClickListener {
            explain = true
            binding.explainerCardView.visibility = View.GONE
            binding.guesserCardView.visibility = View.VISIBLE
        }
        binding.guesserBluffButton.setOnClickListener {
            binding.guesserCardView.visibility = View.GONE
            viewModel.checkResult(explain, guess)
            binding.greetText.text = viewModel.result
            binding.congoCard.visibility = View.VISIBLE
            binding.exitButton.visibility = View.VISIBLE
        }
        binding.guesserTruthButton.setOnClickListener {
            guess = true
            binding.guesserCardView.visibility = View.GONE
            viewModel.checkResult(explain, guess)
            binding.greetText.text = viewModel.result
            binding.congoCard.visibility = View.VISIBLE
            binding.exitButton.visibility = View.VISIBLE
        }
        binding.playAgainButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }
        binding.newGameButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_playFragment)
        }
        binding.exitButton.setOnClickListener {
            binding.congoCard.visibility = View.GONE
            binding.exitButton.visibility = View.GONE
            binding.exitCardView.visibility = View.VISIBLE
        }
        binding.cancelButton.setOnClickListener {
            binding.congoCard.visibility = View.VISIBLE
            binding.exitButton.visibility = View.VISIBLE
            binding.exitCardView.visibility = View.GONE
        }
        binding.exitConfirmButton.setOnClickListener {
            exitProcess(0)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.exitCardView.isVisible) {
                        binding.congoCard.visibility = View.VISIBLE
                        binding.exitButton.visibility = View.VISIBLE
                        binding.exitCardView.visibility = View.GONE
                    } else {
                        findNavController().navigate(R.id.action_resultFragment_to_playFragment)
                    }
                }
            })

    }

}