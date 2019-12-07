package com.fakss.premierleaguefixtures.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class DBMatch(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "status")
    val status: Match.MatchStatus,
    @ColumnInfo(name = "homeTeamName")
    val homeTeamName: String,
    @ColumnInfo(name = "awayTeamName")
    val awayTeamName: String,
    @ColumnInfo(name = "homeTeamScore")
    val homeTeamScore: Int,
    @ColumnInfo(name = "awayTeamScore")
    val awayTeamScore: Int
) {
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is DBMatch) other.id == id else false
    }

    fun toMatchItem(): CardItem.MatchItem = CardItem.MatchItem(
        id, date, status, homeTeamName, awayTeamName, homeTeamScore,
        awayTeamScore, true
    )
}