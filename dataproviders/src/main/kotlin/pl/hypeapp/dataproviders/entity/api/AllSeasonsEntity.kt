package pl.hypeapp.dataproviders.entity.api

import com.google.gson.annotations.SerializedName

data class AllSeasonsEntity(
        @SerializedName("tvShowApiId")
        val tvShowId: String,
        val name: String,
        val premiered: String,
        val status: String,
        val fullRuntime: Long,
        val imageMedium: String,
        val imageOriginal: String,
        val seasons: List<SeasonEntity>)
