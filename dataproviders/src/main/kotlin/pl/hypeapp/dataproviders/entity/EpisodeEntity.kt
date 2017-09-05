package pl.hypeapp.dataproviders.entity

import com.google.gson.annotations.SerializedName
import java.util.*

data class EpisodeEntity constructor(@SerializedName("episodeApiId") val episodeId: String,
                                     @SerializedName("tvShowApiId") val tvShowId: String,
                                     @SerializedName("seasonApiId") val seasonId: String,
                                     val name: String,
                                     val seasonNumber: Int,
                                     val episodeNumber: Int,
                                     val premiereDate: Date,
                                     val runtime: Long,
                                     val imageMedium: String,
                                     val summary: String)
