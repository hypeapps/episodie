package pl.hypeapp.domain.repository

import io.reactivex.Single
import pl.hypeapp.domain.model.search.BasicSearchResultModel

interface SearchRepository {

    fun basicSearch(query: String): Single<List<BasicSearchResultModel>>

}
