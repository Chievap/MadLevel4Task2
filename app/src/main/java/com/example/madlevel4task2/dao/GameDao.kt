package com.example.madlevel4task2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.model.GameCountResult

@Dao
interface GameDao {

    @Query("SELECT * FROM Game")
    suspend fun getAllGames(): List<Game>

    @Query("SELECT SUM(CASE WHEN gameResult = 'WIN' THEN 1 ELSE 0 END) as wins, SUM(CASE WHEN gameResult = 'DRAW' THEN 1 ELSE 0 END) as draws, SUM(CASE WHEN gameResult = 'LOSE' THEN 1 ELSE 0 END) as losses FROM Game")
    suspend fun getAllStatistics(): GameCountResult

    @Insert
    suspend fun insertGame(game: Game)

    @Query("DELETE FROM Game")
    suspend fun deleteAllGames()

}