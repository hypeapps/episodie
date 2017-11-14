package pl.hypeapp.episodie.ui.features.seasontracker.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.graphics.Color
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import kotlinx.android.synthetic.main.item_season_tracker_episode.view.*
import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.*
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.viewmodel.seasontracker.EpisodeViewModel
import pl.hypeapp.materialtimelineview.MaterialTimelineView
import java.util.*

class EpisodeDelegateAdapter(private val listener: OnEpisodeWatchedListener) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = EpisodeViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as EpisodeViewHolder).bind(item as EpisodeViewModel)
        holder.setIsRecyclable(false)
    }

    inner class EpisodeViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_season_tracker_episode)) {

        fun bind(episodeViewModel: EpisodeViewModel) = with(itemView) {
            image_view_item_episode_screen.loadImage(episodeViewModel.episodeModel.imageMedium)
            setEpisodeInfo(episodeViewModel)
            setPremiereDate(episodeViewModel.episodeModel.premiereDate)
            styleTimeline(episodeViewModel)
            image_view_item_episode_add_to_watched.manageWatchStateIcon(episodeViewModel.episodeModel.watchState)
            image_view_item_episode_add_to_watched.setOnClickListener {
                setOnClickListener(episodeViewModel, it)
            }
        }

        private fun setOnClickListener(episodeViewModel: EpisodeViewModel, it: View) = with(itemView) {
            when (episodeViewModel.episodeModel.watchState) {
                WatchState.WATCHED -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startCircularRevealAnimation({
                            listener.onEpisodeDeselect(episodeViewModel.episodeModel.episodeId!!, it)
                        }, ContextCompat.getColor(context, R.color.background_dark))
                    } else {
                        setItemActive()
                        listener.onEpisodeDeselect(episodeViewModel.episodeModel.episodeId!!, it)
                    }
                }
                WatchState.NOT_WATCHED -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startCircularRevealAnimation({
                            listener.onEpisodeSelect(episodeViewModel.episodeModel.episodeId!!, it)
                        }, ContextCompat.getColor(context, R.color.episode_item_watched))
                    } else {
                        setItemWatched()
                        listener.onEpisodeSelect(episodeViewModel.episodeModel.episodeId!!, it)
                    }
                }
            }
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private fun startCircularRevealAnimation(onAnimationEnd: () -> Unit, revealColorRes: Int) = with(itemView) {
            val cx = (image_view_item_episode_add_to_watched.left + image_view_item_episode_add_to_watched.right) / 2
            val cy = (image_view_item_episode_add_to_watched.top + image_view_item_episode_add_to_watched.bottom) / 2
            val endRadius = Math.hypot(itemView.width.toDouble(), itemView.height.toDouble()).toFloat()
            view_episode_season_tracker_background.setBackgroundColor(revealColorRes)
            val circularReveal: Animator = ViewAnimationUtils.createCircularReveal(view_episode_season_tracker_background, cx, cy, 0f, endRadius)
            circularReveal.interpolator = AccelerateInterpolator()
            view_episode_season_tracker_background.viewVisible()
            circularReveal.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    view_episode_season_tracker_background.viewGone()
                    setItemWatched()
                    onAnimationEnd()
                }
            })
            circularReveal.start()
        }

        private fun styleTimeline(episodeViewModel: EpisodeViewModel) = with(itemView) {
            timeline_view_item_episode.topRadioColor = Color.WHITE
            if (episodeViewModel.episodeModel.watchState == WatchState.WATCHED) {
                setItemWatched()
                text_view_item_episode_premiered.setTextColor(Color.WHITE)
            }
            if (episodeViewModel.isNextEpisodeWatched == WatchState.WATCHED) {
                timeline_view_item_episode.bottomRadioColor = ContextCompat.getColor(context, R.color.episode_item_watched)
            } else {
                timeline_view_item_episode.bottomRadioColor = Color.WHITE
            }
            if (episodeViewModel.isLastEpisode) {
                timeline_view_item_episode.position = MaterialTimelineView.POSITION_LAST
            } else {
                timeline_view_item_episode.position = MaterialTimelineView.POSITION_MIDDLE
            }
        }

        private fun setPremiereDate(premiereDate: Date?) = with(itemView) {
            if (premiereDate != null) {
                if (isEpisodeAfterPremiereDate(premiereDate)) {
                    text_view_item_episode_premiered.text = String
                            .format(resources.getString(R.string.item_all_format_premiered), DateFormat.format("yyyy-MM-dd", premiereDate))
                    setItemActive()
                } else {
                    setItemInactive()
                    text_view_item_episode_premiered.setTextColor(Color.WHITE)
                    text_view_item_episode_premiered.text = String
                            .format(resources.getString(R.string.item_all_format_premiere), DateFormat.format("yyyy-MM-dd", premiereDate))
                }
            } else {
                text_view_item_episode_premiered.apply {
                    text = context.getString(R.string.item_episode_premiere_eta)
                    setTextColor(Color.WHITE)
                }
                setItemInactive()
            }
        }

        private fun setItemWatched() = with(itemView) {
            timeline_view_item_episode.setBackgroundColor(ContextCompat.getColor(context, R.color.episode_item_watched))
            timeline_view_item_episode.topRadioColor = ContextCompat.getColor(context, R.color.episode_item_watched)
            text_view_item_episode_premiered.setTextColor(Color.WHITE)
        }

        private fun setItemActive() = with(itemView) {
            image_view_item_episode_add_to_watched.viewVisible()
            timeline_view_item_episode.setBackgroundColor(ContextCompat.getColor(context, R.color.background_dark))
        }

        private fun setItemInactive() = with(itemView) {
            timeline_view_item_episode.setBackgroundColor(ContextCompat.getColor(context, R.color.episode_item_inactive))
            image_view_item_episode_add_to_watched.viewGone()
        }

        private fun setEpisodeInfo(episodeViewModel: EpisodeViewModel) = with(itemView) {
            if (episodeViewModel.episodeModel.summary != null) {
                text_view_item_episode_summary_header.viewVisible()
                text_view_item_episode_summary.viewVisible()
                text_view_item_episode_summary.text = episodeViewModel.episodeModel.summary
            } else {
                text_view_item_episode_summary_header.viewGone()
                text_view_item_episode_summary.viewGone()
            }
            text_view_item_episode_runtime.setFullRuntime(episodeViewModel.episodeModel.runtime)
            text_view_item_episode_number.setHashTagPrefix(episodeViewModel.episodeModel.episodeNumber ?: 0)
            text_view_item_episode_title.text = episodeViewModel.episodeModel.name
        }

        private fun isEpisodeAfterPremiereDate(date: Date): Boolean = date.let {
            return Calendar.getInstance().time.after(it)
        }

    }
}
