package pl.hypeapp.domain.model.collections

import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.model.tvshow.TvShowModel

data class TopListModel(
        var tvShows: List<TvShowModel>,
        val pageableRequest: PageableRequest)
