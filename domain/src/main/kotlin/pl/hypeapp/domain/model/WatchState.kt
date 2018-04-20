package pl.hypeapp.domain.model

object WatchState {

    const val PARTIALLY_WATCHED = "PARTIALLY WATCHED"

    const val WATCHED = "WATCHED"

    const val NOT_WATCHED = "NOT_WATCHED"

    fun toggleWatchState(watchState: String): String = when (watchState) {
        WatchState.NOT_WATCHED -> WatchState.WATCHED
        WatchState.WATCHED -> WatchState.NOT_WATCHED
        else -> WatchState.WATCHED
    }

}
