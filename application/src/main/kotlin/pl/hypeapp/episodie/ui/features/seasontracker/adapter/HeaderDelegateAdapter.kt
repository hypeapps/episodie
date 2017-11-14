package pl.hypeapp.episodie.ui.features.seasontracker.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.item_season_tracker_header.view.*
import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.getFullRuntimeFormatted
import pl.hypeapp.episodie.extensions.getRuntimeFormatted
import pl.hypeapp.episodie.extensions.inflate
import pl.hypeapp.episodie.extensions.loadImage
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.viewmodel.seasontracker.HeaderViewModel

class HeaderDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = HeaderViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as HeaderViewHolder).bind(item as HeaderViewModel)
        holder.setIsRecyclable(false)
    }

    inner class HeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_season_tracker_header)) {

        fun bind(item: HeaderViewModel) = with(itemView) {
            setCover(item)
            setTvShowInfo(item)
            setProgressBar(item)
            item.seasonModel.episodes?.first()?.let {
                if (it.watchState == WatchState.WATCHED) {
                    timeline_view_item_header.bottomRadioColor = ContextCompat.getColor(context, R.color.episode_item_watched)
                } else {
                    timeline_view_item_header.bottomRadioColor = ContextCompat.getColor(context, android.R.color.white)
                }
            }
        }

        private fun setCover(item: HeaderViewModel) = with(itemView) {
            if (item.seasonModel.imageMedium != null) {
                image_view_episode_cover.loadImage(item.seasonModel.imageMedium, ImageView.ScaleType.CENTER_CROP)
            } else {
                // if cover of headerViewModel is null replace it by first screen of headerViewModel's episode.
                item.seasonModel.episodes?.let {
                    if (!it.isEmpty()) image_view_episode_cover.loadImage(it[0].imageMedium)
                }
            }

        }

        private fun setTvShowInfo(item: HeaderViewModel) = with(itemView) {
            text_view_season_tracker_full_runtime.text = String.format(context.getString(R.string.divider_format),
                    getRuntimeFormatted(resources, item.watchedRuntime), getFullRuntimeFormatted(resources, item.seasonModel.runtime))
            text_view_season_tracker_episodes_watched.text = String.format(context.getString(R.string.divider_format),
                    2, item.seasonModel.episodeOrder)
            text_view_season_tracker_title.text = item.title
            text_view_season_tracker_season.text = String.format(context.getString(R.string.season_number_format),
                    item.seasonModel.seasonNumber)
            text_view_season_tracker_episodes_watched.text = String.format(context.getString(R.string.divider_format),
                    item.watchedEpisodes, item.seasonModel.episodeOrder)
        }

        private fun setProgressBar(item: HeaderViewModel) = with(itemView) {
            progress_bar_season_tracker_header.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    progressTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.episode_item_watched))
                } else {
                    progressDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
                }
                max = item.seasonModel.episodeOrder!!
                progress = item.watchedEpisodes
            }
        }
    }
}
