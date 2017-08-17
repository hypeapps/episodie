package pl.hypeapp.dataproviders.entity

import com.google.gson.annotations.SerializedName

data class TvShowEntity(@SerializedName("tvShowApiId")
                        val id: String,
                        val imdbId: String,
                        val name: String,
                        val network: String,
                        val genre: String,
                        val summary: String,
                        val officialSite: String,
                        val status: String,
                        @SerializedName("runtime")
                        val episodeRuntime: Long,
                        val fullRuntime: Long,
                        val premiered: String,
                        val imageMedium: String,
                        val imageOriginal: String)
