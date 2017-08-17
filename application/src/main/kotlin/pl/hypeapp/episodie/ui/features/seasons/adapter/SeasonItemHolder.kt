package pl.hypeapp.episodie.ui.features.seasons.adapter

import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_season.view.*
import pl.hypeapp.domain.model.SeasonModel
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.loadImage
import pl.hypeapp.episodie.extensions.setFullRuntime
import pl.hypeapp.episodie.extensions.setSPrefix

class SeasonItemHolder constructor(val seasonsModel: SeasonModel, val onToggleGroupListener: OnToggleGroupListener)
    : Item<ViewHolder>(), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    override fun getLayout(): Int = R.layout.item_season

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.ripple_view_item_season.setOnClickListener {
            onToggleGroupListener.onToggleExpandableGroup(expandableGroup)
        }
        bindData(viewHolder)
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }

    private fun bindData(viewHolder: ViewHolder) = with(viewHolder.itemView) {
        if (seasonsModel.imageMedium != null) {
            image_view_item_season_cover.loadImage(seasonsModel.imageMedium)
        } else {
            // if cover of season is null replace it by first screen of season's episode.
            image_view_item_season_cover.loadImage(seasonsModel.episodes!![0].imageMedium)
        }
        text_view_item_season_season_number.setSPrefix(seasonsModel.seasonNumber!!)
        text_view_item_season_runtime.setFullRuntime(seasonsModel.runtime)
        text_view_item_season_episode_order.text = String.format(resources.getString(R.string.item_season_order_format),
                seasonsModel.episodeOrder)
    }

}
