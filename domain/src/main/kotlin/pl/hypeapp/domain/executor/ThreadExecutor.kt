package pl.hypeapp.domain.executor

import io.reactivex.Scheduler

interface ThreadExecutor {
    fun getScheduler(): Scheduler
}
