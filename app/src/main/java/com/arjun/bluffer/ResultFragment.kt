package com.arjun.bluffer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.arjun.bluffer.databinding.FragmentResultBinding
import kotlin.system.exitProcess

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private val viewModel: ResultViewModel by viewModels()
//    private val sharedViewModel: SharedViewModel by activityViewModels()
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
            binding.card.visibility = View.GONE
            binding.otherPlayerGuess.visibility = View.VISIBLE
        }
        binding.explainerTruthButton.setOnClickListener {
            explain = true
            binding.card.visibility = View.GONE
            binding.otherPlayerGuess.visibility = View.VISIBLE
        }
        binding.guesserBluffButton.setOnClickListener {
            binding.otherPlayerGuess.visibility = View.GONE
            viewModel.checkResult(explain,guess)
            binding.greetText.text = viewModel.result
            binding.greetText.visibility = View.VISIBLE
            binding.playAgainButton.visibility = View.VISIBLE
            binding.exitButton.visibility = View.VISIBLE
        }
        binding.guesserTruthButton.setOnClickListener {
            guess = true
            binding.otherPlayerGuess.visibility = View.GONE
            viewModel.checkResult(explain,guess)
            binding.greetText.text = viewModel.result
            binding.greetText.visibility = View.VISIBLE
            binding.playAgainButton.visibility = View.VISIBLE
            binding.exitButton.visibility = View.VISIBLE
        }
        binding.playAgainButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_playFragment)
        }
        binding.exitButton.setOnClickListener {
            binding.greetText.visibility =View.GONE
            binding.playAgainButton.visibility = View.GONE
            binding.exitButton.visibility = View.GONE
            binding.exitCardView.visibility = View.VISIBLE
        }
        binding.cancelButton.setOnClickListener {
            binding.greetText.visibility = View.VISIBLE
            binding.playAgainButton.visibility = View.VISIBLE
            binding.exitButton.visibility = View.VISIBLE
            binding.exitCardView.visibility = View.GONE
        }
        binding.yesButton.setOnClickListener {
            exitProcess(0)
        }
    }

}