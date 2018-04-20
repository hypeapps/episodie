package pl.hypeapp.dataproviders.datasource

object PagingHelper {

    fun createLoadPageParams(page: Int, size: Int, totalItemCount: Int): LoadPageParams {
        val totalPages: Int = Math.ceil(totalItemCount.toDouble() / size.toDouble()).toInt()
        val startPosition = (page * size)
        var size = size
        val first = when (page + 1) {
            1 -> true
            else -> false
        }
        var last = false
        when (page + 1) {
            totalPages -> {
                size = totalItemCount - startPosition
                last = true
            }
        }
        return LoadPageParams(page = page,
                size = size,
                startPosition = startPosition,
                totalItemCount = totalItemCount,
                totalPages = totalPages,
                first = first,
                last = last)
    }

}

class LoadPageParams(val page: Int,
                     val size: Int,
                     val startPosition: Int,
                     val totalItemCount: Int,
                     val totalPages: Int,
                     var first: Boolean = false,
                     var last: Boolean = false)
