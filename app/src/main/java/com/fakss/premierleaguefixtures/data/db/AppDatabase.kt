package com.fakss.premierleaguefixtures.data.db

import android.content.Context
import androidx.room.*
import com.fakss.premierleaguefixtures.data.entities.DBMatch
import com.fakss.premierleaguefixtures.data.entities.Match

@Database(entities = [DBMatch::class], version = 1, exportSchema = false)
@TypeConverters(StatusConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMatchDao(): MatchDao

    companion object {

        private const val DB_NAME = "eplmatches.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context.applicationContext).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}

class StatusConverter {

    @TypeConverter
    fun toStatus(value: String?) = value?.let { Match.MatchStatus.valueOf(value) }

    @TypeConverter
    fun toString(value: Match.MatchStatus?) = value?.name
}