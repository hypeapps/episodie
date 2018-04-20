package pl.hypeapp.domain.model

data class Pageable<T> constructor(val content: List<T>,
                                   var last: Boolean = false,
                                   var totalPages: Int = 0,
                                   var totalElements: Int = 0,
                                   var size: Int = 0,
                                   var page: Int = 0,
                                   var first: Boolean = false,
                                   var numberOfElements: Int = 0)
