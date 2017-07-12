package pl.hypeapp.episodie.di.components

import dagger.Component
import pl.hypeapp.episodie.di.scope.PerFragment
import pl.hypeapp.episodie.ui.features.mostpopular.MostPopularFragment

@PerFragment
@Component(dependencies = arrayOf(AppComponent::class))
interface FragmentComponent {

    fun inject(mostPopularFragment: MostPopularFragment)

}
