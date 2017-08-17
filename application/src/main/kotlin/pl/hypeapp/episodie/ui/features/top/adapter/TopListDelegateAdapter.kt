package pl.hypeapp.episodie.ui.features.top.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_top_list.view.*
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.inflate
import pl.hypeapp.episodie.extensions.setRuntime
import pl.hypeapp.episodie.extensions.setZeroPrefixUnderTen
import pl.hypeapp.episodie.glide.GlideApp
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.viewmodel.TvShowViewModel

class TopListDelegateAdapter(val onViewSelectedLister: TopListOnViewSelectedListener) : ViewTypeDelegateAdapter {

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
                GlideApp.with(itemView)
                        .load(it.imageMedium)
                        .into(image_view_item_top_list_cover)
                text_view_item_top_list_title.text = it.name
                text_view_item_top_list_runtime.setRuntime(it.fullRuntime)
                ripple_view_item_top_list.setOnRippleCompleteListener {
                    onViewSelectedLister.onItemSelected(
                            item.tvShow,
                            image_view_item_top_list_cover,
                            text_view_item_top_list_title,
                            text_view_item_top_list_runtime)
                }
                image_view_item_top_list_diamond.setOnClickListener { onViewSelectedLister.onAddToWatched(item.tvShow.id!!) }
            }
        }
    }
}
