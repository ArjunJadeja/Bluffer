package com.arjun.bluffer.view

import android.media.SoundPool
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

private const val TIMER_VALUE = 45
private const val INCREASE_TIMER_VALUE = 30
private const val MILLIS = 1000L

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    private val viewModel: GameViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val soundPool = SoundPool.Builder().setMaxStreams(2).build()
    private var clickSound = R.integer.integer_zero
    private var alarmSound = R.integer.integer_zero

    private lateinit var explainer: String
    private lateinit var guesser: String

    private var statusOk = false

    private var gameStarted = false

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
        loadImage()
        loadPlayerRoles()

        clickSound = soundPool.load(context, R.raw.click, 1)
        alarmSound = soundPool.load(context, R.raw.alarm, 1)

        viewModel.seconds.observe(viewLifecycleOwner) {
            val time = it.toString().padStart(2, '0')
            binding.timer.text = "00:$time"
            if (it <= 10) binding.increaseTimeButton.visibility = View.VISIBLE
            if (it.equals(0)) playAlarmSound()
        }

        viewModel.finished.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_gameFragment_to_resultFragment)
            }
        }

        binding.startButton.setOnClickListener {
            playClickSound()
            sharedViewModel.playersRole(explainer, guesser)
            startGame()
            gameStarted = true
            viewModel.timerValue.value = TIMER_VALUE * MILLIS
            viewModel.startTimer()
        }

        binding.resumeButton.setOnClickListener {
            playClickSound()
            hideResumeCard()
            viewModel.startTimer()
        }

        binding.increaseTimeButton.setOnClickListener {
            playClickSound()
            binding.increaseTimeButton.visibility = View.GONE
            increaseTime()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    playClickSound()
                    findNavController().navigate(R.id.action_gameFragment_to_playFragment)
                }
            }
        )

    }

    override fun onPause() {
        super.onPause()
        if (gameStarted) {
            pauseTimer()
            showResumeCard()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

    private fun checkNetwork() {
        sharedViewModel.isNetworkConnected.observe(viewLifecycleOwner) {
            statusOk = it
        }
        Handler(Looper.getMainLooper()).postDelayed({
            if (!statusOk) {
                Toast.makeText(
                    activity,
                    "No internet connection",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigate(R.id.action_gameFragment_to_playFragment)
            } else {
                binding.progressBar.visibility = View.GONE
                binding.playerRolesCardView.visibility = View.VISIBLE
            }
        }, MILLIS)
    }

    private fun loadImage() {
        if (sharedViewModel.imageStatus.value != false) {
            sharedViewModel.image.observe(viewLifecycleOwner) {
                val imgUri = it.imageUrl!!.toUri().buildUpon().scheme("https").build()
                binding.imageView.load(imgUri) {
                    crossfade(true)
                    crossfade(500)
                    transformations(RoundedCornersTransformation(50f))
                    error(R.drawable.ic_broken_image)
                }
            }
        }
    }

    private fun loadPlayerRoles() {
        val playerList = listOf(
            sharedViewModel.playerOne.value.toString(),
            sharedViewModel.playerTwo.value.toString()
        )
        explainer = selectedPlayer(playerList)
        guesser =
            if (playerList.first() == explainer) {
                playerList.last()
            } else playerList.first()

        binding.selectedPlayerName.text =
            "$explainer you will hold the phone and explain the context in the image to $guesser"
    }

    private fun selectedPlayer(playerList: List<String>): String {
        return playerList.random()
    }

    private fun playClickSound() {
        if (sharedViewModel.soundOn.value!!) {
            soundPool.play(clickSound, 1f, 1f, 1, 0, 1f)
        }
    }

    private fun playAlarmSound() {
        if (sharedViewModel.soundOn.value!!) {
            soundPool.play(alarmSound, 1f, 1f, 1, 0, 1f)
        }
    }

    private fun startGame() {
        binding.playerRolesCardView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        binding.timer.visibility = View.VISIBLE
        binding.imageView.visibility = View.VISIBLE
    }

    private fun pauseTimer() {
        viewModel.stopTimer()
        viewModel.seconds.observe(viewLifecycleOwner) {
            viewModel.timerValue.value = it * MILLIS
        }
    }

    private fun increaseTime() {
        viewModel.stopTimer()
        viewModel.timerValue.value = (viewModel.seconds.value!! + INCREASE_TIMER_VALUE) * MILLIS
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