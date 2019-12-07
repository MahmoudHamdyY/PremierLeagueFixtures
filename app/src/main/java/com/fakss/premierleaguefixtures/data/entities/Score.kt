package com.fakss.premierleaguefixtures.data.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Score(
    @SerializedName("winner") val winner: WinnerType?,
    @SerializedName("fullTime") val fullTime: ScoreGoals
) : Serializable {
    enum class WinnerType : Serializable {
        HOME_TEAM,
        AWAY_TEAM
    }

    data class ScoreGoals(
        @SerializedName("homeTeam") val homeTeam: Int,
        @SerializedName("awayTeam") val awayTeam: Int
    ) : Serializable
}