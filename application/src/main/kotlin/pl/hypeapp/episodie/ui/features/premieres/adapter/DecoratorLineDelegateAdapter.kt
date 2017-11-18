package pl.hypeapp.episodie.ui.features.premieres.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.inflate
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter


class DecoratorLineDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            object : RecyclerView.ViewHolder(parent.inflate(R.layout.item_premiere_divider)) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {}
}
