package pl.hypeapp.episodie.ui.features.search.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_search_suggestion.view.*
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.inflate
import pl.hypeapp.episodie.extensions.loadImage
import pl.hypeapp.episodie.extensions.setRuntime
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.viewmodel.BasicSearchResultViewModel

class SearchSuggestionsDelegateAdapter(val onSearchSuggestionClickListener: OnSearchSuggestionClickListener)
    : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = SearchSuggestionsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as SearchSuggestionsViewHolder).bind(item as BasicSearchResultViewModel)
    }

    inner class SearchSuggestionsViewHolder(parent: ViewGroup)
        : RecyclerView.ViewHolder(parent.inflate(R.layout.item_search_suggestion)) {

        fun bind(model: BasicSearchResultViewModel) = with(itemView) {
            image_view_item_search_suggestion_cover.loadImage(model.basicSearchResultModel.imageMedium)
            text_view_item_search_suggestion_title.text = model.basicSearchResultModel.name
            text_view_item_search_suggestion_ep_order.text = String.format(context.getString(R.string.item_season_order_format),
                    model.basicSearchResultModel.episodeOrder)
            text_view_item_search_suggestion_runtime.setRuntime(model.basicSearchResultModel.runtime)
            ripple_view_item_search_suggestion.setOnRippleCompleteListener {
                onSearchSuggestionClickListener.onItemClick(model, image_view_item_search_suggestion_cover)
            }
        }
    }
}
