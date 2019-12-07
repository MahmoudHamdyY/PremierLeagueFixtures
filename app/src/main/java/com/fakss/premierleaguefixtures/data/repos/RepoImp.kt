package com.fakss.premierleaguefixtures.data.repos

import com.fakss.premierleaguefixtures.data.entities.Competition
import com.fakss.premierleaguefixtures.data.entities.DBMatch
import io.reactivex.Completable
import io.reactivex.Observable

class RepoImp(private val remote: MatchesRemote, private val cache: MatchesCache) : Repo {
    override fun getAllFavorites(): Observable<List<DBMatch>> {
        return cache.getAllFavorites()
    }

    override fun addToFavorite(match: DBMatch): Completable {
        return cache.addToFavorite(match)
    }

    override fun removeFromFavorite(match: DBMatch): Completable {
        return cache.removeFromFavorite(match)
    }

    override fun updateFavoriteMatches(matches: List<DBMatch>): Completable {
        return cache.updateFavoriteMatches(matches)
    }

    override fun getCompetition(competitionId: Int): Observable<Competition> {
        return remote.getCompetition(competitionId)
    }
}