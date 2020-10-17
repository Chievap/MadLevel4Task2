package com.example.madlevel4task2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.madlevel4task2.converter.Converters
import com.example.madlevel4task2.dao.GameDao
import com.example.madlevel4task2.model.Game

// entities: the tables we have in our db,
// version: version of the database, when schema is modified you need update version and define migration strategy
// exportSchema: to export the schema into a directory, look up Database Migrations
@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GamesListRoomDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "GAMES_LIST_DATABASE"

        @Volatile // Make it visible to all running threadsd
        private var gameListRoomDatabaseInstance: GamesListRoomDatabase? = null

        fun getDatabaseInstance(context: Context): GamesListRoomDatabase? {
            if (gameListRoomDatabaseInstance == null) {

                synchronized(GamesListRoomDatabase::class.java) {
                    if (gameListRoomDatabaseInstance == null) {
                        gameListRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            GamesListRoomDatabase::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }
            return gameListRoomDatabaseInstance
        }
    }
}