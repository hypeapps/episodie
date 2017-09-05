package pl.hypeapp.domain.model

data class TopListModel(
        var tvShows: List<TvShowModel>,
        val pageableRequest: PageableRequest)
