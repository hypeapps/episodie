package pl.hypeapp.episodie.ui.features.bingewatching.dialog

import pl.hypeapp.domain.model.SeasonModel

interface OnDialogItemClickListener {

    fun onDialogItemSelected(season: SeasonModel)

}
