package pl.hypeapp.domain.repository

import io.reactivex.Single
import pl.hypeapp.domain.model.TvShowModel

interface Repository {
    fun getTvShow(tvShowId: String): Single<TvShowModel>
}