package pl.hypeapp.episodie.di.components

import dagger.Component
import pl.hypeapp.episodie.di.module.ActivityModule
import pl.hypeapp.episodie.di.scope.PerActivity
import pl.hypeapp.episodie.ui.features.mainfeed.MainFeedActivity
import pl.hypeapp.episodie.ui.features.search.SearchActivity
import pl.hypeapp.episodie.ui.features.seasontracker.SeasonTrackerActivity
import pl.hypeapp.episodie.ui.features.timecalculator.TimeCalculatorActivity
import pl.hypeapp.episodie.ui.features.tvshowdetails.TvShowDetailsActivity
import pl.hypeapp.episodie.ui.features.yourlibrary.YourLibraryActivity

@PerActivity
@Component(dependencies = [(AppComponent::class)], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(mainFeedActivity: MainFeedActivity)

    fun inject(tvShowDetailsActivity: TvShowDetailsActivity)

    fun inject(timeCalculatorActivity: TimeCalculatorActivity)

    fun inject(seasonTrackerActivity: SeasonTrackerActivity)

    fun inject(searchActivity: SearchActivity)

    fun inject(yourLibraryActivity: YourLibraryActivity)
}
