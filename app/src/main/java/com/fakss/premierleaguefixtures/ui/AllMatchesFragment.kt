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
import com.fakss.premierleaguefixtures.getCalendar
import com.fakss.premierleaguefixtures.isTodayOrFuture
import kotlinx.android.synthetic.main.matches_list_lo.*

class AllMatchesFragment : Fragment(), MatchesAdapter.MatchesAdapterListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: MatchesAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var cards: MutableList<CardItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.matches_list_lo, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        initObservables()

        viewModel.getAllMatches(2021)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        matches_rv.layoutManager = layoutManager
        matches_rv.adapter = adapter
    }

    private fun initData() {
        viewModel = ViewModelProviders.of(requireActivity()).get(HomeViewModel::class.java)
        adapter = MatchesAdapter(this)
    }

    private fun initObservables() {
        viewModel.cards.observe(this, Observer { items ->
            cards = items.toMutableList()
            adapter.submitList(cards.map { CardItem(it.type, it.date, it.match?.copy()) })
            scrollToToday()
        })
    }

    private fun scrollToToday() {
        val pos = cards.indexOfFirst { cardItem ->
            cardItem.type == CardItem.CardType.HEADER
                    && cardItem.date!!.getCalendar().isTodayOrFuture()
        }
        if (pos != -1)
            layoutManager.scrollToPosition(pos)
    }

    override fun toggleFavorite(match: CardItem.MatchItem) {
        val position =
            cards.indexOfFirst { it.type == CardItem.CardType.MATCH && it.match!!.id == match.id }
        if (cards[position].match?.isFavorite == true) {
            viewModel.removeFromFavorite(cards[position].match?.toDBMatch()!!)
            cards[position].match?.isFavorite = false
            adapter.submitList(cards.map { CardItem(it.type, it.date, it.match?.copy()) })
        } else {
            viewModel.addToFavorite(cards[position].match?.toDBMatch()!!)
            cards[position].match?.isFavorite = true
            adapter.submitList(cards.map { CardItem(it.type, it.date, it.match?.copy()) })
        }
    }

}