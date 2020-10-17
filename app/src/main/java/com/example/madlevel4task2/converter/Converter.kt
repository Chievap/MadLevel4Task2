package com.example.madlevel4task2.converter

import android.content.res.Resources
import androidx.room.TypeConverter
import com.example.madlevel4task2.model.GameMove
import com.example.madlevel4task2.model.GameResult
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { it -> Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromGameMove(value: GameMove?): String? {
        return value?.name
    }

    @TypeConverter
    fun toGameMove(value: String?): GameMove? {
        return when(value) {
            GameMove.ROCK.name -> GameMove.ROCK
            GameMove.PAPER.name -> GameMove.PAPER
            GameMove.SCISSORS.name -> GameMove.SCISSORS
            else -> throw Resources.NotFoundException("GameValue not found in enum!")
        }
    }

    @TypeConverter
    fun fromGameResult(value: GameResult?): String? {
        return value?.name
    }

    @TypeConverter
    fun toGameResult(value: String?): GameResult? {
        return when(value) {
            GameResult.WIN.name -> GameResult.WIN
            GameResult.DRAW.name -> GameResult.DRAW
            GameResult.LOSE.name -> GameResult.LOSE
            else -> throw Resources.NotFoundException("GameResult not found in enum!")
        }
    }
}