package pl.hypeapp.episodie.ui.features.mostpopular.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_most_popular.view.*
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.adapter.ViewType
import pl.hypeapp.episodie.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.extensions.inflate
import pl.hypeapp.episodie.extensions.loadImage
import pl.hypeapp.episodie.extensions.setTvShowRuntime
import pl.hypeapp.episodie.ui.viewmodel.TvShowViewModel

class MostPopularDelegateAdapter(val onViewSelectedLister: ViewTypeDelegateAdapter.OnViewSelectedListener) :
        ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = MostPopularViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as MostPopularViewHolder
        holder.bind(item as TvShowViewModel)
    }

    inner class MostPopularViewHolder(parent: ViewGroup) :
            RecyclerView.ViewHolder(parent.inflate(R.layout.item_most_popular)) {

        fun bind(item: TvShowViewModel) = with(itemView) {
            text_view_item_most_popular_title.text = item.tvShow?.name
            text_view_most_popular_runtime.setTvShowRuntime(item.tvShow?.fullRuntime)
            image_view_most_popular_cover.loadImage(item.tvShow?.imageMedium)
            super.itemView.setOnClickListener { onViewSelectedLister.onItemSelected() }
        }
    }

}
