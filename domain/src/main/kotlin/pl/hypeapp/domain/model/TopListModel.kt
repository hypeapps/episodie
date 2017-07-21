package pl.hypeapp.domain.model

data class TopListModel(
        val tvShows: List<TvShowModel>,
        val pageableRequest: PageableRequest)
