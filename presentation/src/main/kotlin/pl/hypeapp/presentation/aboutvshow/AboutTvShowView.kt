package pl.hypeapp.presentation.aboutvshow

import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.presentation.base.View

interface AboutTvShowView : View {

    fun getModel(): TvShowModel

    fun setSummary(summary: String?)

    fun setEpisodeRuntime(runtime: Long?)

    fun setGenre(genre: String?)

    fun setNetwork(network: String?)

    fun openBrowserIntent(url: String?)

    fun setOfficialSiteVisible()

}
