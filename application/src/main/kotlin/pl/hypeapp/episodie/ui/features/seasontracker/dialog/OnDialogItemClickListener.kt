package pl.hypeapp.episodie.ui.features.seasontracker.dialog

import pl.hypeapp.domain.model.tvshow.SeasonModel

interface OnDialogItemClickListener {

    fun onDialogItemSelected(season: SeasonModel)

}
