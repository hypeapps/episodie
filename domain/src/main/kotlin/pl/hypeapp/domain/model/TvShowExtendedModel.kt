package pl.hypeapp.domain.model

data class TvShowExtendedModel(val tvShowId: String?,
                               val name: String?,
                               val premiered: String?,
                               val status: String?,
                               val fullRuntime: Long?,
                               val imageMedium: String?,
                               val imageOriginal: String?,
                               val seasons: List<SeasonModel>?)
