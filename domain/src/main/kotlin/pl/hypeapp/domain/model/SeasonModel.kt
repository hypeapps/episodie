package pl.hypeapp.domain.model

class SeasonModel constructor(val seasonId: String?,
                              val seasonNumber: Int?,
                              val episodeOrder: Int?,
                              val runtime: Long?,
                              val premiereDate: String?,
                              val imageMedium: String?,
                              val episodes: List<EpisodeModel>?)
