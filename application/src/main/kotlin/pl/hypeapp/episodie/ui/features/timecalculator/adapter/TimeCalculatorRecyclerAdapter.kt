package pl.hypeapp.episodie.ui.features.timecalculator.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.viewmodel.BasicSearchResultViewModel

class TimeCalculatorRecyclerAdapter(viewItemDelegateAdapter: ViewTypeDelegateAdapter)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<ViewType>

    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    init {
        delegateAdapters.put(ViewType.ITEM, viewItemDelegateAdapter)
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

    fun addItem(item: BasicSearchResultViewModel) {
        items.add(item)
        notifyItemInserted(itemCount)
        notifyDataSetChanged()
    }

    fun deleteItem(adapterPosition: Int) {
        this.items.removeAt(adapterPosition)
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int): BasicSearchResultViewModel = this.items[position] as BasicSearchResultViewModel

}
