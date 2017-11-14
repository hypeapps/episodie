package pl.hypeapp.episodie.ui.features.seasontracker.dialog

import android.support.v4.content.ContextCompat
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_season.view.*
import pl.hypeapp.domain.model.SeasonModel
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.loadImage
import pl.hypeapp.episodie.extensions.setFullRuntime
import pl.hypeapp.episodie.extensions.setSPrefix

internal class SeasonItemHolder(private val seasonModel: SeasonModel,
                                private val onDialogItemClickListener: OnDialogItemClickListener) : Item<ViewHolder>() {

    override fun getLayout(): Int = R.layout.item_season

    override fun bind(viewHolder: ViewHolder, position: Int) {
        bindData(viewHolder, seasonModel)
    }

    private fun bindData(viewHolder: ViewHolder, seasonModel: SeasonModel) = with(viewHolder.itemView) {
        image_view_item_season_add_to_watched.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_select))
        ripple_view_item_season.setOnClickListener({
            onDialogItemClickListener.onDialogItemSelected(seasonModel)
        })
        if (seasonModel.imageMedium != null) {
            image_view_item_season_cover.loadImage(seasonModel.imageMedium)
        } else {
            // if cover of headerViewModel is null replace it by first screen of headerViewModel's episode.
            seasonModel.episodes?.let {
                if (!it.isEmpty()) image_view_item_season_cover.loadImage(it[0].imageMedium)
            }
        }
        text_view_item_season_season_number.setSPrefix(seasonModel.seasonNumber!!)
        text_view_item_season_runtime.setFullRuntime(seasonModel.runtime)
        text_view_item_season_episode_order.text = String.format(resources.getString(R.string.item_season_order_format),
                seasonModel.episodeOrder)
    }

}
