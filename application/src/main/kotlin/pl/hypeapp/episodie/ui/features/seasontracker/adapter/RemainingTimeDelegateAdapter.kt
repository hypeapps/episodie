package pl.hypeapp.episodie.ui.features.seasontracker.adapter

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_season_tracker_remaining_time.view.*
import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.inflate
import pl.hypeapp.episodie.extensions.setRuntime
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.viewmodel.seasontracker.RemainingTimeViewModel

class RemainingTimeDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = RemainingTimeViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as RemainingTimeViewHolder).bind(item as RemainingTimeViewModel)
    }

    inner class RemainingTimeViewHolder(parent: ViewGroup)
        : RecyclerView.ViewHolder(parent.inflate(R.layout.item_season_tracker_remaining_time)) {

        fun bind(item: RemainingTimeViewModel) = with(itemView) {
            text_view_remaining_time.setRuntime(item.runtime)
            if (item.watchState == WatchState.WATCHED) {
                timeline_view_item_remaining_time.lineColor = ContextCompat.getColor(context, R.color.episode_item_watched)
                timeline_view_item_remaining_time.bottomRadioColor = ContextCompat.getColor(context, R.color.episode_item_watched)
                timeline_view_item_remaining_time.topRadioColor = ContextCompat.getColor(context, R.color.episode_item_watched)
            } else {
                timeline_view_item_remaining_time.lineColor = Color.WHITE
                timeline_view_item_remaining_time.bottomRadioColor = Color.WHITE
                timeline_view_item_remaining_time.topRadioColor = Color.WHITE
            }
        }

    }
}
