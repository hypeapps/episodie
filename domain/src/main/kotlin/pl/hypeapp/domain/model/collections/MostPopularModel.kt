package pl.hypeapp.domain.model.collections

import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.model.tvshow.TvShowModel

data class MostPopularModel(
        val tvShows: List<TvShowModel>,
        val pageableRequest: PageableRequest)
