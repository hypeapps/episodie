package pl.hypeapp.episodie.ui.features.mostpopular.adapter

import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.item_most_popular.view.image_view_item_most_popular_cover
import kotlinx.android.synthetic.main.item_most_popular.view.ripple_view_item_most_popular
import kotlinx.android.synthetic.main.item_most_popular.view.text_view_item_most_popular_runtime
import kotlinx.android.synthetic.main.item_most_popular.view.text_view_item_most_popular_title
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.inflate
import pl.hypeapp.episodie.extensions.setRuntime
import pl.hypeapp.episodie.glide.GlideApp
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
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
            item.tvShow?.let {
                GlideApp.with(this).load(it.imageMedium)
                        .placeholder(ColorDrawable(ContextCompat.getColor(itemView.context, R.color.primary_dark)))
                        .thumbnail(0.5f)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(image_view_item_most_popular_cover)
                text_view_item_most_popular_title.text = it.name
                text_view_item_most_popular_runtime.setRuntime(it.fullRuntime)
                ripple_view_item_most_popular.setOnRippleCompleteListener {
                    onViewSelectedLister.onItemSelected(
                            item.tvShow,
                            image_view_item_most_popular_cover,
                            text_view_item_most_popular_title,
                            text_view_item_most_popular_runtime)
                }
            }
        }
    }
}
