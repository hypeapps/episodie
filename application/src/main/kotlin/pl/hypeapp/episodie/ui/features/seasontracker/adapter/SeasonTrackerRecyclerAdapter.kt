package pl.hypeapp.episodie.ui.features.seasontracker.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.viewmodel.seasontracker.EpisodeViewModel
import pl.hypeapp.episodie.ui.viewmodel.seasontracker.HeaderViewModel
import pl.hypeapp.episodie.ui.viewmodel.seasontracker.RemainingTimeViewModel

class SeasonTrackerRecyclerAdapter(onEpisodeWatchedListener: OnEpisodeWatchedListener,
                                   onSelectedListener: HeaderDelegateAdapter.OnSelectedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<ViewType>

    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    init {
        delegateAdapters.put(ViewType.SeasonTrackerViewType.EPISODE_ITEM, EpisodeDelegateAdapter(onEpisodeWatchedListener))
        delegateAdapters.put(ViewType.SeasonTrackerViewType.HEADER, HeaderDelegateAdapter(onSelectedListener))
        delegateAdapters.put(ViewType.SeasonTrackerViewType.TIME_REMAINING_ITEM, RemainingTimeDelegateAdapter())
        items = ArrayList()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun addHeader(item: HeaderViewModel) {
        this.items.add(item)
        notifyItemInserted(itemCount)
    }

    fun addEpisodes(items: List<EpisodeViewModel>) {
        items.forEach {
            this.items.add(RemainingTimeViewModel(it.episodeModel.runtime, it.episodeModel.watchState))
            this.items.add(it)
        }
        notifyItemInserted(itemCount)
        notifyDataSetChanged()
    }

    fun updateItems(header: HeaderViewModel, items: List<EpisodeViewModel>) {
        this.items.clear()
        addHeader(header)
        addEpisodes(items)
    }

}
