package com.fakss.premierleaguefixtures.data.repos

import com.fakss.premierleaguefixtures.data.entities.Competition
import com.fakss.premierleaguefixtures.data.entities.DBMatch
import io.reactivex.Completable
import io.reactivex.Observable

interface Repo{
    fun getAllFavorites(): Observable<List<DBMatch>>
    fun addToFavorite(match: DBMatch): Completable
    fun removeFromFavorite(match: DBMatch): Completable
    fun updateFavoriteMatches(matches: List<DBMatch>): Completable
    fun getCompetition(competitionId: Int): Observable<Competition>
}