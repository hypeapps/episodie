package pl.hypeapp.dataproviders.entity

import com.google.gson.annotations.SerializedName

data class MostPopularEntity(
        @SerializedName("content")
        val tvShows: List<TvShowEntity>,
        val last: Boolean,
        val totalPages: Int,
        val totalElements: Int,
        val size: Int,
        @SerializedName("number")
        val page: Int,
        val first: Boolean,
        val numberOfElements: Int)
