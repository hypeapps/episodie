package pl.hypeapp.episodie.ui.features.premieres.adapter

import android.view.View
import pl.hypeapp.domain.model.premiere.PremiereDateModel

interface OnPremiereViewSelectedListener {

    fun onNotificationSchedule(model: PremiereDateModel)

    fun onPremiereItemClick(model: PremiereDateModel, view: View)

    fun onNotificationDismiss(model: PremiereDateModel)
}
