package pl.hypeapp.domain.model.search


data class BasicSearchResultModel(val id: String?,
                                  val name: String?,
                                  val runtime: Long?,
                                  val episodeOrder: Int?,
                                  val imageMedium: String?,
                                  val imageOriginal: String?) : Comparable<BasicSearchResultModel> {

    override fun compareTo(other: BasicSearchResultModel): Int = this.id!!.compareTo(other.id!!)

}
