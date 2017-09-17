package pl.hypeapp.domain.model

class SeasonModel constructor(val seasonId: String?,
                              val tvShowId: String?,
                              val seasonNumber: Int?,
                              val episodeOrder: Int?,
                              val runtime: Long?,
                              val premiereDate: String?,
                              val imageMedium: String?,
                              var watchState: Int = 0,
                              val episodes: List<EpisodeModel>?)
