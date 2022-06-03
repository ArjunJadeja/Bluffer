package com.arjun.bluffer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import kotlin.random.Random

class PlayFragment : Fragment() {

    lateinit var playButton: Button
    lateinit var playerNamesCV: MaterialCardView
    lateinit var selectedPlayerCV: MaterialCardView
    lateinit var player1Name: TextInputLayout
    lateinit var player2Name: TextInputLayout
    lateinit var nextButton: MaterialButton
    lateinit var progressBar: ProgressBar
    lateinit var selectedPlayerName: TextView
    lateinit var okButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playButton = view.findViewById(R.id.play_button) as Button
        playerNamesCV = view.findViewById(R.id.player_names_card_view) as MaterialCardView
        selectedPlayerCV = view.findViewById(R.id.selected_player_card_view) as MaterialCardView
        player1Name = view.findViewById(R.id.player1_name) as TextInputLayout
        player2Name = view.findViewById(R.id.player2_name) as TextInputLayout
        nextButton = view.findViewById(R.id.next_button) as MaterialButton
        okButton = view.findViewById(R.id.ok_button) as MaterialButton
        selectedPlayerName = view.findViewById(R.id.selected_player_name) as TextView
        progressBar = view.findViewById(R.id.toss_progress_bar) as ProgressBar

        val playerOne = player1Name.editText?.text.toString()
        val playerTwo = player2Name.editText?.text.toString()
        val playerList = listOf<String>(playerOne, playerTwo)
        val randomIndex = Random.nextInt(playerList.size);
        val randomElement = playerList[randomIndex]
        val selectedPlayer = randomElement.toString()
        
        playButton.setOnClickListener {
            playButton.visibility = View.GONE
            playerNamesCV.visibility = View.VISIBLE
        }
        nextButton.setOnClickListener {
            playerNamesCV.visibility = View.GONE
            selectedPlayerName.text = "$selectedPlayer will Guess"
            selectedPlayerCV.visibility = View.VISIBLE
        }
        okButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_playFragment_to_gameFragment)
        }
    }

}