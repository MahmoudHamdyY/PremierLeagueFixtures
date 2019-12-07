package com.fakss.premierleaguefixtures.data

import com.fakss.premierleaguefixtures.data.entities.CardItem
import com.fakss.premierleaguefixtures.getCalendar
import com.fakss.premierleaguefixtures.isSameDate

fun List<CardItem.MatchItem>.buildCard(): List<CardItem> {
    val cards: MutableList<CardItem> = ArrayList()
    if (isEmpty())
        return cards
    cards.add(CardItem(CardItem.CardType.HEADER, this[0].date, null))
    cards.add(CardItem(CardItem.CardType.MATCH, null, this[0]))
    for (i in 1 until this.size) {
        if (cards.firstOrNull {
                it.type == CardItem.CardType.HEADER
                        && it.date?.getCalendar()?.isSameDate(this[i].date.getCalendar()) == true
            } == null)
            cards.add(CardItem(CardItem.CardType.HEADER, this[i].date, null))
        cards.add(CardItem(CardItem.CardType.MATCH, null, this[i]))
    }
    return cards
}

fun List<CardItem>.rebuildCard(): List<CardItem> {
    val matches = filter { it.type == CardItem.CardType.MATCH }.map { it.match!! }
    return matches.buildCard()
}