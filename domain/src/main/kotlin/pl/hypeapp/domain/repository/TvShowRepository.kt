package pl.hypeapp.domain.repository

import io.reactivex.Single
import pl.hypeapp.domain.model.tvshow.TvShowModel

interface TvShowRepository {

    fun getTvShow(tvShowId: String, update: Boolean): Single<TvShowModel>

}
