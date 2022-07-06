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

private const val TIMER_VALUE = 30
private const val MILLIS = 1000L
private const val INITIAL_DELAY = 1000L

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    private val viewModel: GameViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var timeIncreased = false
    private var statusOk = true

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

        checkNetwork()
        loadPlayersRole()
        loadImage()

        viewModel.seconds.observe(viewLifecycleOwner) {
            val time = it.toString().padStart(2, '0')
            binding.timer.text = "00:$time"
            if (it.equals(10)) {
                Toast.makeText(activity, "$it sec remaining", Toast.LENGTH_SHORT).show()
            } else if (it <= 10) {
                if (!timeIncreased) {
                    binding.increaseTimeButton.visibility = View.VISIBLE
                }
            }
        }

        viewModel.finished.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_gameFragment_to_resultFragment)
            }
        }

        binding.startButton.setOnClickListener {
            loadGame()
            viewModel.timerValue.value = TIMER_VALUE * MILLIS
            viewModel.startTimer()
        }

        binding.resumeButton.setOnClickListener {
            hideResumeCard()
            viewModel.startTimer()
        }

        binding.increaseTimeButton.setOnClickListener {
            timeIncreased = true
            binding.increaseTimeButton.visibility = View.GONE
            increaseTime()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_gameFragment_to_playFragment)
                }
            }
        )

    }

    override fun onPause() {
        super.onPause()
        pauseTimer()
        showResumeCard()
    }

    private fun checkNetwork() {
        sharedViewModel.status.observe(viewLifecycleOwner) {
            if (it == false) statusOk = false
        }
        Handler(Looper.getMainLooper()).postDelayed({
            if (!statusOk) {
                Toast.makeText(
                    activity,
                    "Unknown Error Occurred\nCheck Network Connection",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigate(R.id.action_gameFragment_to_playFragment)
            } else {
                binding.progressBar.visibility = View.GONE
                binding.playerRolesCardView.visibility = View.VISIBLE
            }
        }, INITIAL_DELAY)
    }

    private fun loadImage() {
        sharedViewModel.image.observe(viewLifecycleOwner) {
            val imgUri = it.imageUrl!!.toUri().buildUpon().scheme("https").build()
            binding.imageView.load(imgUri) {
                crossfade(true)
                crossfade(500)
                transformations(RoundedCornersTransformation(50f))
                error(R.drawable.ic_broken_image)
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun loadPlayersRole() {
        binding.selectedPlayerName.text =
            "${sharedViewModel.explainer.value.toString()} you will hold the phone and explain the context in the image to ${sharedViewModel.guesser.value.toString()}"
    }

    private fun loadGame() {
        binding.playerRolesCardView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        binding.timerCard.visibility = View.VISIBLE
        binding.imageView.visibility = View.VISIBLE
    }

    private fun pauseTimer() {
        viewModel.seconds.observe(viewLifecycleOwner) {
            viewModel.timerValue.value = it * MILLIS
        }
        viewModel.stopTimer()
    }

    private fun increaseTime() {
        viewModel.stopTimer()
        viewModel.timerValue.value = (viewModel.seconds.value!! + TIMER_VALUE) * MILLIS
        viewModel.startTimer()
    }

    private fun showResumeCard() {
        binding.imageView.visibility = View.INVISIBLE
        binding.resumeCardView.visibility = View.VISIBLE
        binding.increaseTimeButton.visibility = View.GONE
    }

    private fun hideResumeCard() {
        binding.resumeCardView.visibility = View.GONE
        binding.imageView.visibility = View.VISIBLE
    }

}