package pl.hypeapp.domain.repository

import io.reactivex.Single
import pl.hypeapp.domain.model.MostPopularModel
import pl.hypeapp.domain.model.PageableRequest

interface MostPopularRepository {

    fun getMostPopular(pageableRequest: PageableRequest, update: Boolean): Single<MostPopularModel>

}
