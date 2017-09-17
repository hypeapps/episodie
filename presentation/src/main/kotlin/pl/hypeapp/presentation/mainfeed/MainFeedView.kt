package pl.hypeapp.presentation.mainfeed

import pl.hypeapp.presentation.base.View

interface MainFeedView : View {

    fun navigateToPage(page: Int)

    fun navigateToSearch()

    fun addFabButtonLandscapePadding()

}