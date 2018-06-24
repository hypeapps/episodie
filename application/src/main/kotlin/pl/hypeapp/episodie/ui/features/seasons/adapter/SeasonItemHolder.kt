package pl.hypeapp.episodie.ui.features.seasons.adapter

import com.jakewharton.rxbinding2.view.RxView
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.item_season.view.*
import pl.hypeapp.domain.model.tvshow.SeasonModel
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.loadImage
import pl.hypeapp.episodie.extensions.manageWatchStateIcon
import pl.hypeapp.episodie.extensions.setFullRuntime
import pl.hypeapp.episodie.extensions.setSPrefix
import java.util.concurrent.TimeUnit

class SeasonItemHolder constructor(private val seasonsModel: SeasonModel,
                                   private val onToggleGroupListener: OnToggleGroupListener,
                                   private val onChangeWatchStateListener: OnChangeWatchStateListener)
    : Item<ViewHolder>(), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    override fun getLayout(): Int = R.layout.item_season

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }

    override fun bind(viewHolder: ViewHolder, position: Int) = with(viewHolder.itemView) {
        ripple_view_item_season.setOnClickListener {
            onToggleGroupListener.onToggleExpandableGroup(expandableGroup)
        }
        RxView.clicks(image_view_item_season_add_to_watched)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onChangeWatchStateListener.onChangeSeasonWatchState(seasonsModel, image_view_item_season_add_to_watched) }
        bindData(viewHolder)
        viewHolder.setIsRecyclable(false)
    }

    private fun bindData(viewHolder: ViewHolder) = with(viewHolder.itemView) {
        if (seasonsModel.imageMedium != null) {
            image_view_item_season_cover.loadImage(seasonsModel.imageMedium)
        } else {
            // if cover of headerViewModel is null replace it by first screen of headerViewModel's episode.
            seasonsModel.episodes?.let {
                if (!it.isEmpty()) image_view_item_season_cover.loadImage(it[0].imageMedium)
            }
        }
        text_view_item_season_season_number.setSPrefix(seasonsModel.seasonNumber!!)
        text_view_item_season_runtime.setFullRuntime(seasonsModel.fullRuntime)
        text_view_item_season_episode_order.text = String.format(resources.getString(R.string.item_season_order_format),
                seasonsModel.episodeOrder)
        image_view_item_season_add_to_watched.manageWatchStateIcon(seasonsModel.watchState)
    }

}
