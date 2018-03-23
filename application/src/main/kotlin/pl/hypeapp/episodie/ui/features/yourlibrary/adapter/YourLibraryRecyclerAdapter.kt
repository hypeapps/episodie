package pl.hypeapp.episodie.ui.features.yourlibrary.adapter;

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import pl.hypeapp.episodie.ui.base.adapter.ErrorDelegateAdapter
import pl.hypeapp.episodie.ui.base.adapter.LoadingDelegateAdapter
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.viewmodel.TvShowViewModel

class YourLibraryRecyclerAdapter(viewItemDelegateAdapter: ViewTypeDelegateAdapter,
                                 onRetryListener: ViewTypeDelegateAdapter.OnRetryListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<ViewType>

    private val loadingItem = object : ViewType {
        override fun getViewType(): Int = ViewType.LOADING
    }

    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    init {
        delegateAdapters.put(ViewType.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(ViewType.ITEM, viewItemDelegateAdapter)
        delegateAdapters.put(ViewType.ERROR, ErrorDelegateAdapter(onRetryListener))
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
            delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])

    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun addItems(items: List<TvShowViewModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyItemInserted(itemCount)
//        notifyDataSetChanged()
    }

}
