package pl.hypeapp.episodie.ui.viewmodel.seasontracker

import android.arch.lifecycle.ViewModel
import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.domain.model.collections.SeasonTrackerModel
import pl.hypeapp.domain.model.tvshow.EpisodeModel
import pl.hypeapp.domain.model.tvshow.SeasonModel
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel

class SeasonTrackerViewModel : ViewModel() {

    var seasonTrackerModel: SeasonTrackerModel? = null

    var headerViewModel: HeaderViewModel? = null

    var episodes: ArrayList<EpisodeViewModel>? = null

    var seasonModel: SeasonModel? = null

    var title: String = ""

    var imageMedium: String = ""

    var imageOriginal: String = ""

    fun retainModel(seasonTrackerModel: SeasonTrackerModel) {
        this.seasonTrackerModel = seasonTrackerModel
    }

    fun clearModel() {
        this.seasonTrackerModel = null
        this.headerViewModel = null
        this.episodes = null
        this.seasonModel = null
        this.title = ""
        this.imageMedium = ""
        this.imageOriginal = ""
    }

    fun addEpisode(episodeId: String) {
        this.seasonTrackerModel?.watchedEpisodes?.add(episodeId)
        retainSeasonModel(this.seasonModel!!)
    }

    fun removeEpisode(episodeId: String) {
        this.seasonTrackerModel?.watchedEpisodes?.remove(episodeId)
        retainSeasonModel(this.seasonModel!!)
    }

    fun retainSeasonModel(seasonModel: SeasonModel, tvShowExtendedModel: TvShowExtendedModel? = null) {
        this.seasonModel = seasonModel
        tvShowExtendedModel?.let {
            this.title = tvShowExtendedModel.name ?: ""
            this.imageMedium = tvShowExtendedModel.imageMedium ?: ""
            this.imageOriginal = tvShowExtendedModel.imageOriginal ?: ""
        }
        episodes = ArrayList()
        seasonModel.episodes?.forEach {
            mapWatchedEpisodes(it)
        }
        episodes!![episodes!!.size - 1].isLastEpisode = true
        val watchedEpisodes = seasonTrackerModel?.watchedEpisodes?.size
        var watchedEpisodesRuntime: Long = 0
        // sum watching time
        seasonTrackerModel?.watchedEpisodes?.forEach { episodeId ->
            seasonModel.episodes
                    ?.filter { it.episodeId == episodeId }
                    ?.map { watchedEpisodesRuntime += it.runtime!! }
        }
        headerViewModel = HeaderViewModel(seasonModel, title, watchedEpisodes!!, watchedEpisodesRuntime)
    }

    private fun mapWatchedEpisodes(it: EpisodeModel) {
        val isWatched: String = if (seasonTrackerModel?.watchedEpisodes?.contains(it.episodeId)!!)
            WatchState.WATCHED else WatchState.NOT_WATCHED
        it.watchState = isWatched
        val episodeViewModel = EpisodeViewModel(it)
        episodes?.add(episodeViewModel)
        if (episodes!!.indexOf(episodeViewModel) != 0) {
            episodes!![episodes!!.indexOf(episodeViewModel) - 1].isNextEpisodeWatched = isWatched
        }
    }

}
