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
}

enum class GameResult {
    WIN, LOSE, DRAW;

    fun getResultAsString(): String? {
        return when (this) {
            WIN -> "You win"
            LOSE -> "Computer wins"
            DRAW -> "Draw"
        }
    }
}