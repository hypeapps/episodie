package pl.hypeapp.episodie.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.item_loading.view.*
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.inflate

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = LoadingViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) = (holder as LoadingViewHolder).bind()

    class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_loading)) {

        fun bind() = with(itemView) {
            animation_view_item_loading.setAnimation(resources.getString(R.string.animation_loading), LottieAnimationView.CacheStrategy.Strong)
            animation_view_item_loading.loop(true)
            animation_view_item_loading.playAnimation()
        }
    }

}
