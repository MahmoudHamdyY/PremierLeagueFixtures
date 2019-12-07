package com.fakss.premierleaguefixtures.data

import com.fakss.premierleaguefixtures.data.entities.CardItem
import com.fakss.premierleaguefixtures.data.entities.Match
import org.junit.Test

class CardBuilderKtTest {

    @Test
    fun `build list of match items then get list of card item sectioned by date`() {
        val matches = getMatches()
        val expectedCards = getExpectedCardss()

        assert(matches.buildCard() == expectedCards)
    }

    private fun getMatches(): List<CardItem.MatchItem> {
        val matches: MutableList<CardItem.MatchItem> = ArrayList()
        matches.add(
            CardItem.MatchItem(
                1, "2018-08-10T19:00:00Z",
                Match.MatchStatus.SCHEDULED, "LP", "MC",
                0, 0, false
            )
        )
        matches.add(
            CardItem.MatchItem(
                2, "2018-08-10T20:00:00Z",
                Match.MatchStatus.SCHEDULED, "LP", "MC",
                0, 0, false
            )
        )
        matches.add(
            CardItem.MatchItem(
                3, "2018-08-11T19:00:00Z",
                Match.MatchStatus.SCHEDULED, "LP", "MC",
                0, 0, false
            )
        )
        return matches
    }

    private fun getExpectedCardss(): List<CardItem> {
        val cards: MutableList<CardItem> = ArrayList()
        cards.add(CardItem(CardItem.CardType.HEADER, "2018-08-10T19:00:00Z", null))
        cards.add(
            CardItem(
                CardItem.CardType.MATCH, null, CardItem.MatchItem(
                    1, "2018-08-10T19:00:00Z",
                    Match.MatchStatus.SCHEDULED, "LP", "MC",
                    0, 0, false
                )
            )
        )
        cards.add(
            CardItem(
                CardItem.CardType.MATCH, null, CardItem.MatchItem(
                    2, "2018-08-10T30:00:00Z",
                    Match.MatchStatus.SCHEDULED, "LP", "MC",
                    0, 0, false
                )
            )
        )
        cards.add(CardItem(CardItem.CardType.HEADER, "2018-08-11T19:00:00Z", null))
        cards.add(
            CardItem(
                CardItem.CardType.MATCH, null, CardItem.MatchItem(
                    3, "2018-08-11T19:00:00Z",
                    Match.MatchStatus.SCHEDULED, "LP", "MC",
                    0, 0, false
                )
            )
        )
        return cards
    }
}