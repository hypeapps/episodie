package pl.hypeapp.episodie.ui.base.adapter

interface ViewType {

    fun getViewType(): Int

    companion object {
        val ITEM = 1
        val LOADING = 2
        val ERROR = 3
    }

    interface SeasonTrackerViewType : ViewType {
        companion object {
            val HEADER = 1
            val TIME_REMAINING_ITEM = 2
            val EPISODE_ITEM = 3
        }
    }

}
