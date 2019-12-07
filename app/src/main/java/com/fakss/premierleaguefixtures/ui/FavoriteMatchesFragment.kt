package com.fakss.premierleaguefixtures.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.fakss.premierleaguefixtures.R
import com.fakss.premierleaguefixtures.data.entities.CardItem
import com.fakss.premierleaguefixtures.data.rebuildCard
import kotlinx.android.synthetic.main.matches_list_lo.*

class FavoriteMatchesFragment : Fragment(), MatchesAdapter.MatchesAdapterListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: MatchesAdapter
    private var cards: MutableList<CardItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.matches_list_lo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initObservables()

        viewModel.getAllFavorite()
    }

    private fun initData() {
        viewModel = ViewModelProviders.of(requireActivity()).get(HomeViewModel::class.java)
        adapter = MatchesAdapter(this)
        matches_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        matches_rv.adapter = adapter
    }

    private fun initObservables() {
        viewModel.favoriteCards.observe(this, Observer { items ->
            cards = items.toMutableList()
            adapter.submitList(cards.map { CardItem(it.type, it.date, it.match?.copy()) })

            matches_rv.scrollToPosition(0)
        })
    }

    override fun toggleFavorite(match: CardItem.MatchItem) {
        viewModel.removeFromFavorite(match.toDBMatch())
        val pos =
            cards.indexOfFirst { it.type == CardItem.CardType.MATCH && it.match!!.id == match.id }
        cards.removeAt(pos)
        cards = cards.rebuildCard().toMutableList()
        adapter.submitList(cards.map { CardItem(it.type, it.date, it.match?.copy()) })
    }
}