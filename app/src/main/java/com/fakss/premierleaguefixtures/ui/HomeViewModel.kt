package com.fakss.premierleaguefixtures.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fakss.premierleaguefixtures.data.RxObservableSchedulers
import com.fakss.premierleaguefixtures.data.buildCard
import com.fakss.premierleaguefixtures.data.db.DbData
import com.fakss.premierleaguefixtures.data.entities.CardItem
import com.fakss.premierleaguefixtures.data.entities.Competition
import com.fakss.premierleaguefixtures.data.entities.DBMatch
import com.fakss.premierleaguefixtures.data.repos.MatchesRemoteImp
import com.fakss.premierleaguefixtures.data.repos.Repo
import com.fakss.premierleaguefixtures.data.repos.RepoImp
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val repo: Repo,
    private val rxObservableSchedulers: RxObservableSchedulers
) : ViewModel() {

    private var disposable: CompositeDisposable = CompositeDisposable()
    var inProgress = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()
    var cards = MutableLiveData<List<CardItem>>()
    var favoriteCards = MutableLiveData<List<CardItem>>()

    fun getAllMatches(competitionId: Int) {
        disposable.add(
            repo.getCompetition(competitionId)
                .compose(rxObservableSchedulers.applySchedulers())
                .doOnSubscribe {
                    inProgress.value = true
                }.subscribe({
                    setFavorites(it)
                    updateFavorite(it.map().map { matchItem -> matchItem.toDBMatch() })
                }, {
                    error.value = it.message
                    inProgress.value = false
                })
        )
    }

    private fun setFavorites(competition: Competition) {
        val matches = competition.map()
        disposable.add(
            repo.getAllFavorites()
                .compose(rxObservableSchedulers.applySchedulers())
                .subscribe({ dbMatches ->
                    favoriteCards.value =
                        dbMatches.map { dbMatch -> dbMatch.toMatchItem() }.buildCard()
                    for (dbMatch in dbMatches) {
                        val pos = matches.indexOfFirst { matchItem -> matchItem.id == dbMatch.id }
                        matches[pos].isFavorite = true
                    }
                    cards.value = matches.buildCard()
                    inProgress.value = false
                }, {
                    error.value = it.message
                })
        )
    }

    private fun updateFavorite(matches: List<DBMatch>) {
        disposable.add(
            repo.updateFavoriteMatches(matches)
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun addToFavorite(match: DBMatch) {
        disposable.add(
            repo.addToFavorite(match)
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun removeFromFavorite(match: DBMatch) {
        disposable.add(
            repo.removeFromFavorite(match)
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun getAllFavorite() {
        disposable.add(
            repo.getAllFavorites()
                .compose(rxObservableSchedulers.applySchedulers())
                .doOnSubscribe {
                    inProgress.value = true
                }.subscribe({
                    favoriteCards.value = it.map { dbMatch -> dbMatch.toMatchItem() }
                        .sortedBy { matchItem -> matchItem.date }.buildCard()
                    inProgress.value = false
                }, {
                    error.value = it.message
                    inProgress.value = false
                })
        )
    }

    class HomeViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(
                    RepoImp(
                        MatchesRemoteImp(),
                        DbData.getMatchDb()
                    ),
                    RxObservableSchedulers.DEFAULT
                ) as T
            else -> throw IllegalArgumentException("viewModel is not exist")
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}