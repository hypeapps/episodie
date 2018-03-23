package pl.hypeapp.episodie.ui.features.yourlibrary.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_watched_show.view.*
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.inflate
import pl.hypeapp.episodie.extensions.loadImage
import pl.hypeapp.episodie.extensions.manageWatchStateIcon
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.viewmodel.TvShowViewModel

class YourLibraryDelegateAdapter(val onViewSelectedListener: ViewTypeDelegateAdapter.OnViewSelectedListener) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = WatchedShowViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder.setIsRecyclable(false)
        holder as WatchedShowViewHolder
        holder.bind(item as TvShowViewModel)
    }

    inner class WatchedShowViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_watched_show)) {

        fun bind(item: TvShowViewModel) = with(itemView) {
            item.tvShow?.let { model ->
                image_view_tv_show_cover.loadImage(model.imageMedium)
                image_view_watched_show_watching_status.manageWatchStateIcon(model.watchState)
                setOnClickListener { onViewSelected(model) }
            }
        }

        private fun onViewSelected(tvShowModel: TvShowModel) = with(itemView) {
            onViewSelectedListener.onItemSelected(tvShowModel, image_view_tv_show_cover)
        }
    }
}

