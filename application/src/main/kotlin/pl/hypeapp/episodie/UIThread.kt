package pl.hypeapp.episodie

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.hypeapp.domain.executor.PostExecutionThread
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UIThread @Inject constructor() : PostExecutionThread {
    override fun getScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}