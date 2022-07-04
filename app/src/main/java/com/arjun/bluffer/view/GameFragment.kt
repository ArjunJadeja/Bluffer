package com.arjun.bluffer.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import coil.transform.RoundedCornersTransformation
import com.arjun.bluffer.R
import com.arjun.bluffer.databinding.FragmentGameBinding
import com.arjun.bluffer.viewmodel.GameViewModel
import com.arjun.bluffer.viewmodel.SharedViewModel

private const val TIMER_VALUE = 20000L
private const val DELAY_TIME = 500L

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameBinding.bind(view)

        sharedViewModel.getNewImage()
        loadImage()

        Handler(Looper.getMainLooper()).postDelayed({
            binding.progressBar.visibility = View.GONE
            binding.selectedPlayerCardView.visibility = View.VISIBLE
        }, DELAY_TIME)

        val playerList = listOf(
            sharedViewModel.playerOne.value.toString(),
            sharedViewModel.playerTwo.value.toString()
        )
        val explainer = selectedPlayer(playerList)
        val guesser = if (playerList.first() == explainer) {
            playerList.last()
        } else playerList.first()


        binding.selectedPlayerName.text =
            "$explainer you will hold the phone and explain the context in the image to $guesser"

        viewModel.seconds.observe(viewLifecycleOwner) {
            binding.timer.text = "00:$it"
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
            sharedViewModel.playersRole(explainer, guesser)
            binding.selectedPlayerCardView.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
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
        binding.imageView.visibility = View.INVISIBLE
        binding.resumeCardView.visibility = View.VISIBLE
    }

    private fun selectedPlayer(playerList: List<String>): String {
        return playerList.random()
    }

    private fun loadImage() {
        sharedViewModel.memeImage.observe(viewLifecycleOwner) {
            val imgUri = it.imageUrl!!.toUri().buildUpon().scheme("https").build()
            binding.imageView.load(imgUri) {
                crossfade(true)
                crossfade(500)
                transformations(RoundedCornersTransformation(50f))
            }
            binding.progressBar.visibility = View.GONE
        }
    }

}