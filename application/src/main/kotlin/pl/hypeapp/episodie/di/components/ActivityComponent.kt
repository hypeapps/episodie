package pl.hypeapp.episodie.di.components

import dagger.Component
import pl.hypeapp.episodie.di.scope.PerActivity
import pl.hypeapp.episodie.ui.features.mainfeed.MainFeedActivity
import pl.hypeapp.episodie.ui.features.search.SearchActivity
import pl.hypeapp.episodie.ui.features.seasontracker.SeasonTrackerActivity
import pl.hypeapp.episodie.ui.features.timecalculator.TimeCalculatorActivity
import pl.hypeapp.episodie.ui.features.tvshowdetails.TvShowDetailsActivity

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class))
interface ActivityComponent {

    fun inject(mainFeedActivity: MainFeedActivity)

    fun inject(tvShowDetailsActivity: TvShowDetailsActivity)

    fun inject(timeCalculatorActivity: TimeCalculatorActivity)

    fun inject(seasonTrackerActivity: SeasonTrackerActivity)

    fun inject(searchActivity: SearchActivity)

}
