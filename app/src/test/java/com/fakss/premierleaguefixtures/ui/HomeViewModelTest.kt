package com.fakss.premierleaguefixtures.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fakss.premierleaguefixtures.data.RxObservableSchedulers
import com.fakss.premierleaguefixtures.data.api.Api
import com.fakss.premierleaguefixtures.data.buildCard
import com.fakss.premierleaguefixtures.data.db.MatchDao
import com.fakss.premierleaguefixtures.data.entities.*
import com.fakss.premierleaguefixtures.data.repos.MatchesCacheImp
import com.fakss.premierleaguefixtures.data.repos.MatchesRemoteImp
import com.fakss.premierleaguefixtures.data.repos.RepoImp
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Maybe
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

class HomeViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var matchDao: MatchDao
    private lateinit var api: Api
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        matchDao = Mockito.mock(MatchDao::class.java)
        api = Mockito.mock(Api::class.java)
        viewModel = HomeViewModel(
            RepoImp(MatchesRemoteImp(api), MatchesCacheImp(matchDao)),
            RxObservableSchedulers.TEST_SCHEDULER
        )
    }

    @Test
    fun `get all matches with successful response then build cards`() {
        val cardsObserver: Observer<List<CardItem>> = mock()
        viewModel.cards.observeForever(cardsObserver)
        val competitionId = 1
        val competition = Competition(
            listOf(
                Match(
                    0, "2018-08-10T19:00:00Z", Match.MatchStatus.SCHEDULED,
                    Score(null, Score.ScoreGoals(0, 0)), Team(1, "LP"), Team(2, "MC")
                )
            )
        )

        whenever(api.getCompetition(competitionId)) doReturn Observable.just(competition)
        whenever(matchDao.getAll()) doReturn Maybe.just(listOf())

        viewModel.getAllMatches(competitionId)

        verify(cardsObserver).onChanged(competition.map().buildCard())
    }

    @Test
    fun `get all matches with successful response then build cards and set favorite items`() {
        val cardsObserver: Observer<List<CardItem>> = mock()
        viewModel.cards.observeForever(cardsObserver)
        val competitionId = 1
        val competition = Competition(
            listOf(
                Match(
                    0, "2018-08-10T19:00:00Z", Match.MatchStatus.SCHEDULED,
                    Score(null, Score.ScoreGoals(0, 0)), Team(1, "LP"), Team(2, "MC")
                )
            )
        )

        whenever(api.getCompetition(competitionId)) doReturn Observable.just(competition)
        whenever(matchDao.getAll()) doReturn Maybe.just(
            listOf(
                DBMatch(0, "2018-08-10T19:00:00Z", Match.MatchStatus.SCHEDULED, "LP", "MC", 0, 0)
            )
        )

        viewModel.getAllMatches(competitionId)

        verify(cardsObserver).onChanged(competition.map().apply {
            get(0).isFavorite = true
        }.buildCard())
    }

    @Test
    fun `get all matches with successful response then update favorite list`() {
        val cardsObserver: Observer<List<CardItem>> = mock()
        viewModel.cards.observeForever(cardsObserver)
        val competitionId = 1
        val competition = Competition(
            listOf(
                Match(
                    0, "2018-08-10T19:00:00Z", Match.MatchStatus.SCHEDULED,
                    Score(null, Score.ScoreGoals(0, 0)), Team(1, "LP"), Team(2, "MC")
                )
            )
        )

        whenever(api.getCompetition(competitionId)) doReturn Observable.just(competition)
        whenever(matchDao.getAll()) doReturn Maybe.empty()

        viewModel.getAllMatches(competitionId)

        verify(matchDao).updateAll(competition.map().map { it.toDBMatch() })
    }

    @Test
    fun `get all matches with successful response then verify loading state`() {
        val progressObserver: Observer<Boolean> = mock()
        viewModel.inProgress.observeForever(progressObserver)
        val competitionId = 1
        val competition = Competition(
            listOf(
                Match(
                    0, "2018-08-10T19:00:00Z", Match.MatchStatus.SCHEDULED,
                    Score(null, Score.ScoreGoals(0, 0)), Team(1, "LP"), Team(2, "MC")
                )
            )
        )

        whenever(api.getCompetition(competitionId)) doReturn Observable.just(competition)
        whenever(matchDao.getAll()) doReturn Maybe.just(listOf())

        viewModel.getAllMatches(competitionId)

        verify(progressObserver).onChanged(true)
        verify(progressObserver).onChanged(false)
    }
}