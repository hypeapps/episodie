package pl.hypeapp.domain.repository

import io.reactivex.Single
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.model.collections.MostPopularModel

interface MostPopularRepository {

    fun getMostPopular(pageableRequest: PageableRequest, update: Boolean): Single<MostPopularModel>

}
