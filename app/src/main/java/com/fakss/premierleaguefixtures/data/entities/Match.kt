package com.fakss.premierleaguefixtures.data.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Match(
    @SerializedName("id") val id: Int,
    @SerializedName("utcDate") val utcDate: String,
    @SerializedName("status") val status: MatchStatus,
    @SerializedName("score") val score: Score,
    @SerializedName("homeTeam") val homeTeam: Team,
    @SerializedName("awayTeam") val awayTeam: Team
) : Serializable {
    enum class MatchStatus : Serializable {
        FINISHED,
        SCHEDULED,
        POSTPONED,
        LIVE,
        IN_PLAY,
        PAUSED,
        SUSPENDED,
        CANCELED
    }

    fun toMatchItem(): CardItem.MatchItem = CardItem.MatchItem(
        id, utcDate, status, homeTeam.name, awayTeam.name, score.fullTime.homeTeam,
        score.fullTime.awayTeam
    )
}
