package com.fakss.premierleaguefixtures.data.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Team(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
) : Serializable