package pl.hypeapp.domain.repository

import io.reactivex.Single
import pl.hypeapp.domain.model.tvshow.TvShowModel

interface SearchRepository {

    fun basicSearch(query: String): Single<List<TvShowModel>>

}
