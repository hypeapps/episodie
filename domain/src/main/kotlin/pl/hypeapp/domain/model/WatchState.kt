package pl.hypeapp.domain.model

object WatchState {

    val PARTIALLY_WATCHED = 2

    val WATCHED = 1

    val NOT_WATCHED = 0

    fun manageWatchState(watchState: Int): Int = when (watchState) {
        WatchState.NOT_WATCHED -> WatchState.WATCHED
        WatchState.WATCHED -> WatchState.NOT_WATCHED
        else -> WatchState.WATCHED
    }

}
