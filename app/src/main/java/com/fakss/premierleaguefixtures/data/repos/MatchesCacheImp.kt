package com.fakss.premierleaguefixtures.data.repos

import com.fakss.premierleaguefixtures.data.db.MatchDao
import com.fakss.premierleaguefixtures.data.entities.DBMatch
import io.reactivex.Completable
import io.reactivex.Observable

class MatchesCacheImp(private val dao: MatchDao) : MatchesCache {

    override fun getAllFavorites(): Observable<List<DBMatch>> {
        return dao.getAll().toObservable()
    }

    override fun addToFavorite(match: DBMatch): Completable {
        return Completable.fromCallable { dao.insert(match) }
    }

    override fun removeFromFavorite(match: DBMatch): Completable {
        return Completable.fromCallable { dao.delete(match) }
    }

    override fun updateFavoriteMatches(matches: List<DBMatch>): Completable {
        return Completable.fromCallable { dao.updateAll(matches) }
    }

}