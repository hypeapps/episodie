package pl.hypeapp.dataproviders.repository

import io.reactivex.Single
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.domain.repository.RuntimeRepository
import javax.inject.Inject

class RuntimeDataRepository @Inject constructor(private val dataFactory: DataFactory) : RuntimeRepository {

    override fun getUserFullRuntime(): Single<Long> {
        return dataFactory
                .createRuntimeDataSource()
                .getUserFullRuntime()
                .map { it.runtime }
    }

}
