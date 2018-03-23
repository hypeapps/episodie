package pl.hypeapp.domain.repository

import io.reactivex.Single
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel

interface AllSeasonsRepository {

    fun getAllSeasonsAfterPremiereDate(tvShowId: String, update: Boolean): Single<TvShowExtendedModel>

    fun getAllSeason(tvShowId: String, update: Boolean): Single<TvShowExtendedModel>

}
