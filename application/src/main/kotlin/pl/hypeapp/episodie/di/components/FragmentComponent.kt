package pl.hypeapp.episodie.di.components

import dagger.Component
import pl.hypeapp.episodie.di.scope.PerFragment
import pl.hypeapp.episodie.ui.features.mostpopular.MostPopularFragment
import pl.hypeapp.episodie.ui.features.premieres.PremieresFragment
import pl.hypeapp.episodie.ui.features.seasons.SeasonsFragment
import pl.hypeapp.episodie.ui.features.top.TopListFragment
import pl.hypeapp.episodie.ui.features.tvshowsummary.TvShowSummaryFragment

@PerFragment
@Component(dependencies = arrayOf(AppComponent::class))
interface FragmentComponent {

    fun inject(mostPopularFragment: MostPopularFragment)

    fun inject(topListFragment: TopListFragment)

    fun inject(tvShowSummaryFragment: TvShowSummaryFragment)

    fun inject(seasonsFragment: SeasonsFragment)

    fun inject(premieresFragment: PremieresFragment)

}
