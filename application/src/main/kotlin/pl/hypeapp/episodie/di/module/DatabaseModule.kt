package pl.hypeapp.episodie.di.module

import dagger.Module
import dagger.Provides
import pl.hypeapp.dataproviders.service.room.RoomService
import pl.hypeapp.episodie.App
import javax.inject.Singleton

@Module
class DatabaseModule(private val app: App,
                     private val dbName: String) {

    @Provides
    @Singleton
    fun provideRoomService(): RoomService {
        return RoomService.getInstance(app, dbName)
    }

}
