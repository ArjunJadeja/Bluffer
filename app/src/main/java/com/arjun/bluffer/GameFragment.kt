package com.arjun.bluffer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.arjun.bluffer.databinding.FragmentGameBinding

private const val TIMER_VALUE = 15000L

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadImage()
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameBinding.bind(view)

        val playerList = listOf(
            sharedViewModel.playerOne.value.toString(),
            sharedViewModel.playerTwo.value.toString()
        )
        val selectedPlayer = selectedPlayer(playerList)

        binding.selectedPlayerName.text = "$selectedPlayer will explain the context in the image"

        viewModel.seconds.observe(viewLifecycleOwner) {
            binding.timer.text = "00 : $it"
            if (it.equals(10)) {
                Toast.makeText(activity, "$it sec remaining", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.finished.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_gameFragment_to_resultFragment)
            }
        }

        binding.startButton.setOnClickListener {
            binding.selectedPlayerCardView.visibility = View.GONE
            binding.timerCard.visibility = View.VISIBLE
            binding.imageView.visibility = View.VISIBLE
            viewModel.timerValue.value = TIMER_VALUE
            viewModel.startTimer()
        }
        binding.resumeButton.setOnClickListener {
            binding.resumeCardView.visibility = View.GONE
            binding.imageView.visibility = View.VISIBLE
            viewModel.startTimer()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_gameFragment_to_playFragment)
                }
            })

    }

    override fun onPause() {
        super.onPause()
        viewModel.seconds.observe(viewLifecycleOwner) {
            viewModel.timerValue.value = 1000 * it.toLong()
        }
        viewModel.stopTimer()
        binding.imageView.visibility = View.GONE
        binding.resumeCardView.visibility = View.VISIBLE
    }


    private fun selectedPlayer(playerList: List<String>): String {
        return playerList.random()
    }

    private fun loadImage() {
        sharedViewModel.memeImage.observe(viewLifecycleOwner) {
            val imgUri = it.imageUrl!!.toUri().buildUpon().scheme("https").build()
            binding.imageView.load(imgUri)
        }
    }

}