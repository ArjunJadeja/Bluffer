package com.arjun.bluffer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arjun.bluffer.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
//    private val sharedViewModel: SharedViewModel by activityViewModels()

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
            binding.card.visibility = View.GONE
            binding.otherPlayerGuess.visibility = View.VISIBLE
        }
        binding.guesserBluffButton.setOnClickListener {
            binding.otherPlayerGuess.visibility = View.GONE
            binding.greetText.visibility = View.VISIBLE
            binding.button.visibility = View.VISIBLE
        }
        binding.guesserTruthButton.setOnClickListener {
            binding.otherPlayerGuess.visibility = View.GONE
            binding.greetText.visibility = View.VISIBLE
            binding.button.visibility = View.VISIBLE
        }
    }

}