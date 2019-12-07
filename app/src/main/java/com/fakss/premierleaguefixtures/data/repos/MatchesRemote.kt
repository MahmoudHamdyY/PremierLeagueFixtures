package com.fakss.premierleaguefixtures.data.repos

import com.fakss.premierleaguefixtures.data.entities.Competition
import io.reactivex.Observable

interface MatchesRemote {
    fun getCompetition(competitionId: Int): Observable<Competition>
}