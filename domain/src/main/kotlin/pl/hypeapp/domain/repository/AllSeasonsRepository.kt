package pl.hypeapp.domain.repository

import io.reactivex.Single
import pl.hypeapp.domain.model.AllSeasonsModel

interface AllSeasonsRepository {

    fun getAllSeasons(tvShowId: String, update: Boolean): Single<AllSeasonsModel>

}
