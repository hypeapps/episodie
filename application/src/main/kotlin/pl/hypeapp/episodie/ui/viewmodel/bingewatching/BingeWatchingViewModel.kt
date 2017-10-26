package pl.hypeapp.episodie.ui.viewmodel.bingewatching

import android.arch.lifecycle.ViewModel
import pl.hypeapp.domain.model.BingeWatchingModel
import pl.hypeapp.domain.model.SeasonModel

class BingeWatchingViewModel : ViewModel() {

    var bingeWatchingModel: BingeWatchingModel? = null

    var season: HeaderViewModel? = null

    var episodes: ArrayList<EpisodeViewModel>? = null

    fun retainModel(bingeWatchingModel: BingeWatchingModel) {
        this.bingeWatchingModel = bingeWatchingModel
    }

    fun retainSeasonModel(seasonModel: SeasonModel) {
        episodes = ArrayList()
        seasonModel.episodes?.forEach {
            val isWatched = bingeWatchingModel?.watchedEpisodes?.contains(it.episodeId)!!
            episodes?.add(EpisodeViewModel(it, isWatched))
        }
        val watchedEpisodes = bingeWatchingModel?.watchedEpisodes?.size
        var watchedEpisodesRuntime: Long = 0
        bingeWatchingModel?.watchedEpisodes?.forEach { episodeId ->
            seasonModel.episodes?.filter { it.episodeId == episodeId }
                    ?.map { watchedEpisodesRuntime += it.runtime!! }
        }
        season = HeaderViewModel(seasonModel, watchedEpisodes!!, watchedEpisodesRuntime)
    }

}
