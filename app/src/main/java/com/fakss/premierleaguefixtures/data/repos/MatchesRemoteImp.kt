package com.fakss.premierleaguefixtures.data.repos

import com.fakss.premierleaguefixtures.data.api.Api
import com.fakss.premierleaguefixtures.data.api.ApiService
import com.fakss.premierleaguefixtures.data.entities.Competition
import io.reactivex.Observable

class MatchesRemoteImp(private val api: Api = ApiService.getApiInstance()) : MatchesRemote {

    override fun getCompetition(competitionId: Int): Observable<Competition> {
        return api.getCompetition(competitionId)
    }

}