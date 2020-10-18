package com.example.madlevel4task2.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.madlevel4task2.R
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.model.GameMove
import com.example.madlevel4task2.model.GameResult
import com.example.madlevel4task2.repository.GameRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.item_game_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {
    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true) // the [onCreateOptionsMenu] of this class is invoked instead of the MainActivity
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameRepository = GameRepository(requireContext())

        fillStatisticsTitle()

        iv_rock_option.setOnClickListener {
            fillResultOfGame(GameMove.ROCK)
        }
        iv_paper_option.setOnClickListener {
            fillResultOfGame(GameMove.PAPER)
        }
        iv_scissors_option.setOnClickListener {
            fillResultOfGame(GameMove.SCISSORS)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.iv_action_history).isVisible = true
        requireActivity().toolbar.title = getString(R.string.app_name)
    }

    // Navigate to the history fragment
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.iv_action_history -> {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    private fun fillResultOfGame(playerGameMove: GameMove) {
        val imageResourcePlayer = GameMove.getDrawable(playerGameMove)
        val computerGameMove = GameMove.values().random()
        val imageResourceComputer = computerGameMove.getDrawable()
        val gameResult = getGameResult(computerGameMove, playerGameMove)

        iv_selected_hand_you.setImageResource(imageResourcePlayer)
        iv_selected_hand_computer.setImageResource(imageResourceComputer)
        tv_game_outcome_title.text = getGameResultMessage(gameResult)

        addGame(computerGameMove, playerGameMove, gameResult)
        fillStatisticsTitle()
    }

    private fun fillStatisticsTitle() {
        mainScope.launch {
            val gameStatistics = withContext(Dispatchers.IO) {
                gameRepository.getGameStatistics()
            }
            tv_win_draw_lose_stats.text = getString(
                R.string.win_draw_lose_stats,
                gameStatistics.wins.toString(),
                gameStatistics.draws.toString(),
                gameStatistics.losses.toString()
            )
        }
    }

    /**
     * This function calculates the outcome of the game and returns a [GameResult] object.
     */
    private fun getGameResult(computerGameMove: GameMove, playerGameMove: GameMove): GameResult {

        return if (computerGameMove == playerGameMove) {
            GameResult.DRAW
        } else if (computerGameMove == GameMove.ROCK && playerGameMove == GameMove.PAPER) {
            GameResult.WIN
        } else if (computerGameMove == GameMove.PAPER && playerGameMove == GameMove.SCISSORS) {
            GameResult.WIN
        } else if (computerGameMove == GameMove.SCISSORS && playerGameMove == GameMove.ROCK) {
            GameResult.WIN
        }
        // if it does not match any of the win criteria, return LOSE
        else {
            GameResult.LOSE
        }
    }

    private fun getGameResultMessage(gameResult: GameResult): String {
        return when (gameResult) {
            GameResult.WIN -> {
                getString(R.string.you_win)
            }
            GameResult.DRAW -> {
                getString(R.string.draw)
            }
            else -> {
                getString(R.string.computer_wins)
            }
        }
    }

    private fun addGame(
        computerGameMove: GameMove,
        playerGameMove: GameMove,
        gameResult: GameResult
    ) {

        mainScope.launch {
            val game = Game(
                computerMove = computerGameMove,
                playerMove = playerGameMove,
                gameResult = gameResult
            )

            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }

        }
    }
}