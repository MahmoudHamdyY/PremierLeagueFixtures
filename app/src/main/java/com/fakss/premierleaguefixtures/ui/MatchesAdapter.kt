package com.fakss.premierleaguefixtures.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fakss.premierleaguefixtures.R
import com.fakss.premierleaguefixtures.data.entities.CardItem
import com.fakss.premierleaguefixtures.data.entities.Match
import com.fakss.premierleaguefixtures.displayHeaderDate
import com.fakss.premierleaguefixtures.displayTime
import com.fakss.premierleaguefixtures.getCalendar
import kotlinx.android.synthetic.main.date_item.view.*
import kotlinx.android.synthetic.main.match_item.view.*

class MatchesAdapter(
    private val listener: MatchesAdapterListener,
    diffCallback: DiffUtil.ItemCallback<CardItem> = matchesDiffCallback()
) : ListAdapter<CardItem, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        const val DATE = 1
        const val MATCH = 2

        private fun matchesDiffCallback() = object : DiffUtil.ItemCallback<CardItem>() {
            override fun areItemsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                if (oldItem.type == newItem.type) {
                    return when (oldItem.type) {
                        CardItem.CardType.HEADER -> oldItem.date == newItem.date
                        CardItem.CardType.MATCH -> oldItem.match?.id == newItem.match?.id
                    }
                }
                return false
            }

            override fun areContentsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                if (oldItem.type == CardItem.CardType.HEADER)
                    return true
                return oldItem.match!!.isFavorite == newItem.match!!.isFavorite
                        && oldItem.match.status == newItem.match.status
                        && oldItem.match.awayTeamScore == newItem.match.awayTeamScore
                        && oldItem.match.homeTeamScore == newItem.match.homeTeamScore
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val root: View
        var viewHolder: RecyclerView.ViewHolder? = null
        when (viewType) {
            DATE -> {
                root =
                    LayoutInflater.from(parent.context).inflate(R.layout.date_item, parent, false)
                viewHolder = DateViewHolder(root)
            }
            MATCH -> {
                root =
                    LayoutInflater.from(parent.context).inflate(R.layout.match_item, parent, false)
                viewHolder = MatchViewHolder(root)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            DATE -> {
                val dateViewHolder =
                    DateViewHolder(holder.itemView)
                dateViewHolder.bindItem(getItem(position).date!!)
            }
            MATCH -> {
                val matchViewHolder =
                    MatchViewHolder(holder.itemView)
                val match = getItem(position).match!!
                matchViewHolder.bindItem(match)
                holder.itemView.is_favorite_iv.setOnClickListener {
                    listener.toggleFavorite(match)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            CardItem.CardType.HEADER -> DATE
            CardItem.CardType.MATCH -> MATCH
        }
    }

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(date: String) {
            itemView.date_tv.text = date.getCalendar().displayHeaderDate()
        }
    }

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(match: CardItem.MatchItem) {
            with(itemView) {
                home_team_name_tv.text = match.homeTeamName
                away_team_name_tv.text = match.awayTeamName
                is_favorite_iv.isActivated = match.isFavorite
                when (match.status) {
                    Match.MatchStatus.FINISHED, Match.MatchStatus.LIVE,
                    Match.MatchStatus.IN_PLAY -> {
                        match_result_tv.text = context.getString(
                            R.string.d_d,
                            match.homeTeamScore,
                            match.awayTeamScore
                        )
                        match_time_tv.visibility = View.GONE
                        match_result_tv.visibility = View.VISIBLE
                    }
                    else -> {
                        match_time_tv.text = match.date.getCalendar().displayTime()
                        match_time_tv.visibility = View.VISIBLE
                        match_result_tv.visibility = View.GONE
                    }
                }
            }
        }
    }

    interface MatchesAdapterListener {
        fun toggleFavorite(match: CardItem.MatchItem)
    }

}