package pl.hypeapp.dataproviders.executor

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import pl.hypeapp.domain.executor.ThreadExecutor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobExecutor @Inject constructor() : ThreadExecutor {
    override fun getScheduler(): Scheduler {
        return Schedulers.io()
    }
}