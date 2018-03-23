package pl.hypeapp.domain.model

object WatchState {

    val PARTIALLY_WATCHED = "PARTIALLY WATCHED"

    val WATCHED = "WATCHED"

    val NOT_WATCHED = "NOT_WATCHED"

    fun toggleWatchState(watchState: String): String = when (watchState) {
        WatchState.NOT_WATCHED -> WatchState.WATCHED
        WatchState.WATCHED -> WatchState.NOT_WATCHED
        else -> WatchState.WATCHED
    }

}
