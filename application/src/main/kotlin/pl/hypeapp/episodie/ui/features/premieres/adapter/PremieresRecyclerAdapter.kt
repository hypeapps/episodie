package pl.hypeapp.episodie.ui.features.premieres.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import pl.hypeapp.episodie.ui.base.adapter.ErrorDelegateAdapter
import pl.hypeapp.episodie.ui.base.adapter.LoadingDelegateAdapter
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.viewmodel.premiere.PremiereViewModel

class PremieresRecyclerAdapter(onRetryListener: ViewTypeDelegateAdapter.OnRetryListener,
                               onPremiereViewSelectedListener: OnPremiereViewSelectedListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>

    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    private val loadingItem = object : ViewType {
        override fun getViewType(): Int = ViewType.LOADING
    }

    private val errorItem = object : ViewType {
        override fun getViewType(): Int = ViewType.ERROR
    }

    private val lineItem = object : ViewType {
        override fun getViewType(): Int = ViewType.DECORATOR
    }

    init {
        delegateAdapters.put(ViewType.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(ViewType.ITEM, PremieresDelegateAdapter(onPremiereViewSelectedListener))
        delegateAdapters.put(ViewType.ERROR, ErrorDelegateAdapter(onRetryListener))
        delegateAdapters.put(ViewType.DECORATOR, DecoratorLineDelegateAdapter())
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

    fun addItems(items: List<PremiereViewModel>, isLastPage: Boolean) {
        // Remove loading  or error item view
        val initPosition = this.items.size - 1
        this.items.removeAt(initPosition)
        notifyItemRemoved(initPosition)
        // Needs to clear list otherwise elements are repeating (look at MosPopularViewModel)
        this.items.clear()
        items.forEach {
            if (this.items.size > 0) {
                this.items.add(lineItem)
                this.items.add(it)
            } else {
                it.isFirstItem = true
                this.items.add(it)
            }
        }
        if (!isLastPage) {
            addLoadingView()
        } else {
            (this.items.last() as PremiereViewModel).isLastItem = true
        }
        notifyItemRangeChanged(initPosition, this.items.size)
    }

    fun updateItems(items: List<PremiereViewModel>, isLastPage: Boolean) {
        this.items.clear()
        addItems(items, isLastPage)
        notifyDataSetChanged()
    }

    fun addErrorItem() {
        val initPosition = this.items.size - 1
        this.items.removeAt(initPosition)
        notifyItemRemoved(initPosition)
        addErrorView()
        notifyItemInserted(initPosition)
    }

    private fun addErrorView() = items.add(errorItem)

    private fun addLoadingView() = items.add(loadingItem)

}
