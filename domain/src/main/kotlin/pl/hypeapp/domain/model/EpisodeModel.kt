package pl.hypeapp.domain.model

import java.util.*

data class EpisodeModel constructor(val episodeId: String?,
                                    val tvShowId: String?,
                                    val seasonId: String?,
                                    val name: String?,
                                    val seasonNumber: Int?,
                                    val episodeNumber: Int?,
                                    val premiereDate: Date?,
                                    val runtime: Long?,
                                    val imageMedium: String?,
                                    val summary: String?,
                                    var watchState: Int = 0)
