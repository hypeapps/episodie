package pl.hypeapp.episodie.ui.features.search.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_search_suggestion.view.*
import kotlinx.android.synthetic.main.item_top_list.view.coordinator_layout_item_top_list
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.*
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.viewmodel.TvShowViewModel

class SearchSuggestionsDelegateAdapter(val onSearchSuggestionClickListener: OnSearchSuggestionClickListener)
    : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = SearchSuggestionsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as SearchSuggestionsViewHolder).bind(item as TvShowViewModel)
        holder.setIsRecyclable(false)
    }

    inner class SearchSuggestionsViewHolder(parent: ViewGroup)
        : RecyclerView.ViewHolder(parent.inflate(R.layout.item_search_suggestion)) {

        fun bind(model: TvShowViewModel) = with(itemView) {
            model.tvShow?.let {
                if (adapterPosition % 2 != 0) {
                    parent_layout_item_search_suggestion.setBackgroundColor(ContextCompat.getColor(context, R.color.item_top_list_background))
                }
                image_view_item_search_suggestion_cover.loadImage(it.imageMedium)
                text_view_item_search_suggestion_title.text = it.name
                image_view_item_search_suggestion_diamond.manageWatchStateIcon(model.tvShow.watchState)
                if(it.episodeOrder == 0){
                    image_view_item_search_suggestion_diamond.viewInvisible()
                } else {
                    image_view_item_search_suggestion_diamond.viewVisible()
                }
                if (it.watchedEpisodes != 0) {
                    text_view_item_search_suggestion_ep_order.text = String.format(resources.getString(R.string.divider_format),
                            model.tvShow.watchedEpisodes,
                            model.tvShow.episodeOrder)
                } else {
                    text_view_item_search_suggestion_ep_order.text = it.episodeOrder.toString()
                }
                if (it.watchingTime != 0L) {
                    text_view_item_search_suggestion_runtime.text = String.format(resources.getString(R.string.divider_format),
                            getFullRuntimeFormatted(resources, it.watchingTime),
                            getFullRuntimeFormatted(resources, it.fullRuntime))
                } else {
                    text_view_item_search_suggestion_runtime.setFullRuntime(it.fullRuntime)
                }
                ripple_view_item_search_suggestion.setOnRippleCompleteListener {
                    onSearchSuggestionClickListener.onItemClick(model.tvShow, image_view_item_search_suggestion_cover)
                }
                image_view_item_search_suggestion_diamond.setOnClickListener {
                    onSearchSuggestionClickListener.onChangeWatchState(model.tvShow, image_view_item_search_suggestion_diamond)
                }
            }
        }
    }
}
