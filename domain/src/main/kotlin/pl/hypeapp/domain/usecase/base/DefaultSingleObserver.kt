package pl.hypeapp.domain.usecase.base

import io.reactivex.observers.DisposableSingleObserver
import java.util.logging.Level
import java.util.logging.Logger

open class DefaultSingleObserver<T> : DisposableSingleObserver<T>() {

    private val logger: Logger = Logger.getLogger("DefaultSingleObserver")

    override fun onSuccess(model: T) {}

    override fun onError(error: Throwable) {
        logger.log(Level.WARNING, error.message + " | \n" + error.stackTrace)
    }

}
