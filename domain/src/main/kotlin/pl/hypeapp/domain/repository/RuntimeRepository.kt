package pl.hypeapp.domain.repository

import io.reactivex.Single

interface RuntimeRepository {

    fun getUserFullRuntime(): Single<Long>

}
