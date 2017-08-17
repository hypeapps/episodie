package pl.hypeapp.presentation.tvshowinfo

import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.presentation.base.View

interface TvShowInfoView : View {

    fun getModel(): TvShowModel

    fun setSummary(summary: String?)

    fun setEpisodeRuntime(runtime: Long?)

    fun setGenre(genre: String?)

    fun setNetwork(network: String?)

    fun openBrowserIntent(url: String?)

    fun setOfficialSiteVisible()

}
