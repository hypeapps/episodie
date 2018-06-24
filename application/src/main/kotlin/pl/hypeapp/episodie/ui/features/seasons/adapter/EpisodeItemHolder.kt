package pl.hypeapp.episodie.ui.features.seasons.adapter

import android.text.format.DateFormat
import com.jakewharton.rxbinding2.view.RxView
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.item_episode.view.*
import pl.hypeapp.domain.model.tvshow.EpisodeModel
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.loadImage
import pl.hypeapp.episodie.extensions.manageWatchStateIcon
import pl.hypeapp.episodie.extensions.setFullRuntime
import pl.hypeapp.episodie.extensions.setHashTagPrefix
import java.util.concurrent.TimeUnit

class EpisodeItemHolder(val model: EpisodeModel,
                        private val onToggleGroupListener: OnToggleGroupListener,
                        private val onChangeWatchStateListener: OnChangeWatchStateListener) : Item<ViewHolder>() {

    override fun getLayout(): Int = R.layout.item_episode

    override fun bind(viewHolder: ViewHolder, position: Int) = with(viewHolder.itemView) {
        ripple_view_item_episode.setOnRippleCompleteListener {
            onToggleGroupListener.onToggleGroup()
        }
        RxView.clicks(image_view_item_episode_add_to_watched)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onChangeWatchStateListener.onChangeEpisodeWatchState(model, image_view_item_episode_add_to_watched) }
        bindData(viewHolder)
    }

    private fun bindData(viewHolder: ViewHolder) = with(viewHolder.itemView) {
        image_view_item_episode_screen.loadImage(model.imageMedium)
        text_view_item_episode_summary.text = model.summary
        text_view_item_episode_runtime.setFullRuntime(model.runtime)
        text_view_item_episode_number.setHashTagPrefix(model.episodeNumber ?: 0)
        text_view_item_episode_title.text = model.name
        text_view_item_episode_premiered.text = String.format(resources.getString(R.string.item_all_format_premiered),
                DateFormat.format("yyyy-MM-dd", model.premiereDate))
        image_view_item_episode_add_to_watched.manageWatchStateIcon(model.watchState)
    }

}
