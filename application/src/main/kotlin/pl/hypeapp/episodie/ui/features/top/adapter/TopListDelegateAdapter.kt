package pl.hypeapp.episodie.ui.features.top.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.item_top_list.view.*
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.*
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.viewmodel.TvShowViewModel

class TopListDelegateAdapter(val onViewSelectedListener: TopListOnViewSelectedListener) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = TopListViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder.setIsRecyclable(false)
        holder as TopListViewHolder
        holder.bind(item as TvShowViewModel)
    }

    inner class TopListViewHolder(parent: ViewGroup) :
            RecyclerView.ViewHolder(parent.inflate(R.layout.item_top_list)) {

        fun bind(item: TvShowViewModel) = with(itemView) {
            if (adapterPosition % 2 == 0) {
                coordinator_layout_item_top_list.setBackgroundColor(ContextCompat.getColor(context, R.color.item_top_list_background))
            }
            text_view_top_list_position.setZeroPrefixUnderTen(adapterPosition + 1)
            item.tvShow?.let {
                image_view_item_top_list_cover.loadImage(it.imageMedium)
                text_view_item_top_list_title.text = it.name
                text_view_item_top_list_runtime.setRuntime(it.fullRuntime)
                ripple_view_item_top_list.setOnRippleCompleteListener { onViewSelected(item.tvShow) }
                image_view_item_top_list_diamond.setOnClickListener { view ->
                    onViewSelectedListener.onChangeWatchState(it, view as ImageView)
                }
                image_view_item_top_list_diamond.manageWatchStateIcon(it.watchState)
            }
        }

        private fun onViewSelected(tvShowModel: TvShowModel) = with(itemView) {
            onViewSelectedListener.onItemSelected(
                    tvShowModel,
                    image_view_item_top_list_cover,
                    text_view_item_top_list_title,
                    text_view_item_top_list_runtime)
        }

    }
}
