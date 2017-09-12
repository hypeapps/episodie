package pl.hypeapp.presentation.tvshowsummary

import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.presentation.base.View

interface TvShowSummaryView : View {

    fun getModel(): TvShowModel

    fun setSummary(summary: String?)

    fun setEpisodeRuntime(runtime: Long?)

    fun setGenre(genre: String?)

    fun setNetwork(network: String?)

    fun openBrowserIntent(url: String?)

    fun setOfficialSiteVisible()

}
