package com.example.madlevel4task2.model

import android.content.res.Resources
import android.provider.Settings.Global.getString
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.madlevel4task2.R
import java.security.AccessController.getContext
import java.util.*

@Entity
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val date: Date = Date(),
    val computerMove: GameMove,
    val playerMove: GameMove,
    val gameResult: GameResult
)
// All the moves available to play
enum class GameMove {
    ROCK, PAPER, SCISSORS;

    // Return the drawable with the correct id
    fun getDrawable(): Int {
        return when (this) {
            ROCK -> R.drawable.rock
            PAPER -> R.drawable.paper
            SCISSORS -> R.drawable.scissors
        }
    }

    companion object {
        fun getDrawable(gameMove: GameMove): Int {
            return when (gameMove) {
                ROCK -> R.drawable.rock
                PAPER -> R.drawable.paper
                SCISSORS -> R.drawable.scissors
            }
        }
    }
}

enum class GameResult {
    WIN, LOSE, DRAW;

    // Return the result of the game as a string
    fun getResultAsString(): String {
        return when (this) {
            WIN -> Resources.getSystem().getString(R.string.you_win)
            LOSE -> Resources.getSystem().getString(R.string.computer_wins)
            DRAW -> Resources.getSystem().getString(R.string.draw)
        }
    }
}