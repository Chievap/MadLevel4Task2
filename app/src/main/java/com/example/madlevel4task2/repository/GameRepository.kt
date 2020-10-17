
package com.example.madlevel4task2.repository

import android.content.Context
import com.example.madlevel4task2.dao.GameDao
import com.example.madlevel4task2.database.GamesListRoomDatabase
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.model.GameCountResult

class GameRepository(context: Context) {
    private val gameDao: GameDao

    init {
        val database = GamesListRoomDatabase.getDatabaseInstance(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> = gameDao.getAllGames()

    suspend fun getGameStatistics(): GameCountResult = gameDao.getAllStatistics()

    suspend fun insertGame(game: Game) = gameDao.insertGame(game)

    suspend fun deleteAllGames() = gameDao.deleteAllGames()

}