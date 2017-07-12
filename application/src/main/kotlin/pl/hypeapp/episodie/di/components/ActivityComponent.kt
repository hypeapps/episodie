package pl.hypeapp.episodie.di.components

import dagger.Component
import pl.hypeapp.episodie.di.scope.PerActivity
import pl.hypeapp.episodie.ui.features.mainfeed.MainFeedActivity

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class))
interface ActivityComponent {

    fun inject(mainFeedActivity: MainFeedActivity)

}
