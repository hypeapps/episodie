package pl.hypeapp.episodie.di.components

import dagger.Component
import pl.hypeapp.episodie.MainActivity
import pl.hypeapp.episodie.di.scope.PerActivity

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class))
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

}
