package pl.hypeapp.domain.repository

import io.reactivex.Single
import pl.hypeapp.domain.model.BasicSearchResultModel

interface SearchRepository {

    fun basicSearch(query: String): Single<List<BasicSearchResultModel>>

}
