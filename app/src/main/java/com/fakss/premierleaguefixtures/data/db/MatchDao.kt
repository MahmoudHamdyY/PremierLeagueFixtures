package com.fakss.premierleaguefixtures.data.db

import androidx.room.*
import com.fakss.premierleaguefixtures.data.entities.DBMatch
import io.reactivex.Maybe

@Dao
abstract class MatchDao {

    @Query("SELECT * FROM matches")
    abstract fun getAll(): Maybe<List<DBMatch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(match: DBMatch)

    @Delete
    abstract fun delete(match: DBMatch)

    @Update
    abstract fun updateAll(matches: List<DBMatch>)
}