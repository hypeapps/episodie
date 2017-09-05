package pl.hypeapp.dataproviders.entity

import com.google.gson.annotations.SerializedName

data class SeasonEntity constructor(@SerializedName("seasonApiId") val seasonId: String,
                                    @SerializedName("tvShowApiId") val tvShowId: String,
                                    val seasonNumber: Int,
                                    val runtime: Long,
                                    val premiereDate: String,
                                    val imageMedium: String,
                                    val episodes: List<EpisodeEntity>)
