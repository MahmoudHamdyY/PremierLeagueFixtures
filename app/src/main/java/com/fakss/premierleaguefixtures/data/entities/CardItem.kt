package com.fakss.premierleaguefixtures.data.entities

data class CardItem(
    val type: CardType,
    val date: String?,
    val match: MatchItem?
) {
    enum class CardType {
        HEADER,
        MATCH
    }

    data class MatchItem(
        val id: Int,
        val date: String,
        val status: Match.MatchStatus,
        val homeTeamName: String,
        val awayTeamName: String,
        val homeTeamScore: Int,
        val awayTeamScore: Int,
        var isFavorite: Boolean = false
    ) {
        fun toDBMatch(): DBMatch =
            DBMatch(id, date, status, homeTeamName, awayTeamName, homeTeamScore, awayTeamScore)

        override fun equals(other: Any?): Boolean {
            if (other is MatchItem) {
                return id == other.id
                        && status == other.status
                        && homeTeamScore == other.homeTeamScore
                        && awayTeamScore == other.awayTeamScore
                        && isFavorite == other.isFavorite
            }
            return super.equals(other)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is CardItem) {
            return type == other.type
                    && if (type == CardType.HEADER) date == other.date else match == other.match
        }
        return super.equals(other)
    }
}