package pl.hypeapp.episodie.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_error.view.*
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.inflate

class ErrorDelegateAdapter(val onRetryListener: ViewTypeDelegateAdapter.OnRetryListener) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ErrorViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) = (holder as ErrorViewHolder).bind()

    inner class ErrorViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_error)) {

        fun bind() = with(itemView) {
            button_item_error_retry.setOnClickListener({ onRetryListener.onRetry() })
        }
    }
}
