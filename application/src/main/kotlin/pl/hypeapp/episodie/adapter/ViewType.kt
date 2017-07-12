package pl.hypeapp.episodie.adapter

interface ViewType {

    fun getViewType(): Int

    companion object {

        val ITEM = 1

        val LOADING = 2

        val ERROR = 3

    }

}
