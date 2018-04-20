package pl.hypeapp.episodie.ui.features.premieres.adapter

import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_premiere.view.*
import pl.hypeapp.domain.model.premiere.PremiereDateModel
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.*
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.viewmodel.premiere.PremiereViewModel
import pl.hypeapp.materialtimelineview.MaterialTimelineView
import java.util.*


class PremieresDelegateAdapter(val onPremiereViewSelectedListener: OnPremiereViewSelectedListener) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = PremiereViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as PremiereViewHolder).bind(item as PremiereViewModel)
        holder.setIsRecyclable(false)
    }

    inner class PremiereViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_premiere)) {

        fun bind(model: PremiereViewModel) = with(itemView) {
            text_view_item_premiere_title.text = model.premiereDateModel.name
            text_view_item_premiere_premiere_date.setDateFormat(model.premiereDateModel.premiereDateFormatted())
            image_view_item_premiere_cover.loadImage(model.premiereDateModel.imageMedium)
            text_view_item_premiere_status.text = String.format(context.getString(R.string.tv_show_details_status), model.premiereDateModel.status)
            model.premiereDateModel.genre?.let {
                if (it.isNotEmpty()) {
                    text_view_item_premiere_genre.text = String.format(context.getString(R.string.item_premiere_genre), model.premiereDateModel.genre)
                    text_view_item_premiere_genre.viewVisible()
                }
            }
            styleTimelineView(model)
            setDaysToGo(model.premiereDateModel.premiereDateFormatted())
            setNotificationClickListener(model.premiereDateModel)
            setOnClickListener {
                onPremiereViewSelectedListener.onPremiereItemClick(model.premiereDateModel, image_view_item_premiere_cover)
            }
        }

        private fun styleTimelineView(model: PremiereViewModel) = with(itemView) {
            when {
                model.isFirstItem -> timeline_view_item_premiere.position = MaterialTimelineView.POSITION_FIRST
                model.isLastItem -> timeline_view_item_premiere.position = MaterialTimelineView.POSITION_LAST
                else -> timeline_view_item_premiere.position = MaterialTimelineView.POSITION_MIDDLE
            }
        }

        private fun setNotificationClickListener(model: PremiereDateModel) = with(itemView) {
            if (isAfterPremiereDate(model.premiereDateFormatted())) {
                image_view_item_premiere_notification_icon.viewGone()
            } else {
                image_view_item_premiere_notification_icon.viewVisible()
                if (model.notificationScheduled) {
                    image_view_item_premiere_notification_icon.setColorFilter(ContextCompat.getColor(context, android.R.color.white),
                            PorterDuff.Mode.SRC_IN)
                } else {
                    image_view_item_premiere_notification_icon.setColorFilter(ContextCompat.getColor(context, R.color.episode_item_inactive),
                            PorterDuff.Mode.SRC_IN)
                }
                image_view_item_premiere_notification_icon.setOnClickListener {
                    if (model.notificationScheduled) {
                        onPremiereViewSelectedListener.onNotificationDismiss(model)
                        image_view_item_premiere_notification_icon.setColorFilter(ContextCompat.getColor(context, R.color.episode_item_inactive),
                                PorterDuff.Mode.SRC_IN)
                    } else {
                        onPremiereViewSelectedListener.onNotificationSchedule(model)
                        image_view_item_premiere_notification_icon.setColorFilter(ContextCompat.getColor(context, android.R.color.white),
                                PorterDuff.Mode.SRC_IN)
                    }
                }
            }
        }

        private fun setDaysToGo(premiereDate: Date) = with(itemView) {
            if (isAfterPremiereDate(premiereDate)) {
                text_view_days_to_go.viewInvisible()
            } else {
                text_view_days_to_go.viewVisible()
                val diffDays: Long = daysBetween(Calendar.getInstance().time, premiereDate)
                text_view_days_to_go.text = resources.getQuantityString(
                        R.plurals.days_to_go,
                        diffDays.toInt(),
                        diffDays.toInt())
            }
        }
    }
}
