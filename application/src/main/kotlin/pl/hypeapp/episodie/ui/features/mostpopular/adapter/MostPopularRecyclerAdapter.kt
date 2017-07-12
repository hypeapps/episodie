package pl.hypeapp.episodie.ui.features.mostpopular.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import pl.hypeapp.episodie.adapter.ErrorDelegateAdapter
import pl.hypeapp.episodie.adapter.LoadingDelegateAdapter
import pl.hypeapp.episodie.adapter.ViewType
import pl.hypeapp.episodie.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.viewmodel.TvShowViewModel

class MostPopularRecyclerAdapter(val totalItemCount: Int,
                                 onViewSelectedListener: ViewTypeDelegateAdapter.OnViewSelectedListener,
                                 onRetryListener: ViewTypeDelegateAdapter.OnRetryListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>

    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    private val loadingItem = object : ViewType {
        override fun getViewType(): Int = ViewType.LOADING
    }

    private val errorItem = object : ViewType {
        override fun getViewType(): Int = ViewType.ERROR
    }

    init {
        delegateAdapters.put(ViewType.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(ViewType.ITEM, MostPopularDelegateAdapter(onViewSelectedListener))
        delegateAdapters.put(ViewType.ERROR, ErrorDelegateAdapter(onRetryListener))
        items = ArrayList()
        addLoadingView()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun addItems(items: List<TvShowViewModel>) {
        // Remove loading  or error item view
        val initPosition = this.items.size - 1
        this.items.removeAt(initPosition)
        notifyItemRemoved(initPosition)
        // Needs to clear list otherwise elements are repeating (look at MosPopularViewModel)
        this.items.clear()
        items.forEach({
            if (this.items.size < totalItemCount) this.items.add(it)
        })
        if (isThereItemsToLoad()) {
            addLoadingView()
        }
        notifyItemRangeChanged(initPosition, this.items.size)
    }

    fun addErrorItem() {
        val initPosition = this.items.size - 1
        this.items.removeAt(initPosition)
        notifyItemRemoved(initPosition)
        addErrorView()
        notifyItemInserted(initPosition)
    }

    private fun addErrorView() {
        items.add(errorItem)
    }

    private fun addLoadingView() {
        items.add(loadingItem)
    }

    private fun isThereItemsToLoad() = items.size < totalItemCount

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex

}
