package com.fakss.premierleaguefixtures.data.db

import com.fakss.premierleaguefixtures.App
import com.fakss.premierleaguefixtures.data.repos.MatchesCache
import com.fakss.premierleaguefixtures.data.repos.MatchesCacheImp

object DbData {

    private val db: AppDatabase by lazy { AppDatabase.getInstance(App.appContext()) }

    fun getMatchDb(): MatchesCache = MatchesCacheImp(db.getMatchDao())
}