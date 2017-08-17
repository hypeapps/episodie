package pl.hypeapp.episodie.ui.base.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.inflate

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = LoadingViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {}

    class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_loading))

}
