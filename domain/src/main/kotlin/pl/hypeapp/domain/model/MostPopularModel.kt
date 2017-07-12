package pl.hypeapp.domain.model

data class MostPopularModel(
        val tvShows: List<TvShowModel>,
        val pageableRequest: PageableRequest)
