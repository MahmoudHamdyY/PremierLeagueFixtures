package com.fakss.premierleaguefixtures.data.api

import com.fakss.premierleaguefixtures.data.entities.Competition
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("competitions/{competition_id}/matches")
    fun getCompetition(
        @Path("competition_id") competitionId: Int
    ): Observable<Competition>
}