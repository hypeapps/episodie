package pl.hypeapp.dataproviders.entity.api

import com.google.gson.annotations.SerializedName

data class PremiereDateEntity(@SerializedName("tvShowApiId")
                              val id: String,
                              val imdbId: String,
                              val name: String,
                              val genre: String,
                              val summary: String,
                              val episodeOrder: Int,
                              val status: String,
                              @SerializedName("runtime")
                              val episodeRuntime: Long,
                              val fullRuntime: Long,
                              val premiere: String,
                              val imageMedium: String,
                              val imageOriginal: String)
