package pl.hypeapp.domain.model

data class TvShowModel(
        val id: String?,
        val imdbId: String?,
        val name: String?,
        val episodeRuntime: Long?,
        val fullRuntime: Long?,
        val premiered: String?,
        val imageMedium: String?,
        val imageOriginal: String?)
