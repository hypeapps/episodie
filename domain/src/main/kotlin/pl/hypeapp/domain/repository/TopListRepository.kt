package pl.hypeapp.domain.repository

import io.reactivex.Single
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.model.collections.TopListModel

interface TopListRepository {

    fun getTopList(pageableRequest: PageableRequest, update: Boolean): Single<TopListModel>

}
