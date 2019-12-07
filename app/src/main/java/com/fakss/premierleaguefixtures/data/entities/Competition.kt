package com.fakss.premierleaguefixtures.data.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Competition(@SerializedName("matches") val matches: List<Match>) : Serializable {
    fun map(): List<CardItem.MatchItem> = matches.map { it.toMatchItem() }
}