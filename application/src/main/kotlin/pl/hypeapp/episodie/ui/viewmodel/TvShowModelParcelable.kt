package pl.hypeapp.episodie.ui.viewmodel

import android.os.Parcel
import android.os.Parcelable
import pl.hypeapp.domain.model.tvshow.TvShowModel

class TvShowModelParcelable constructor(val id: String?,
                                        val imdbId: String?,
                                        val name: String?,
                                        val network: String?,
                                        val status: String?,
                                        val summary: String?,
                                        val episodeRuntime: Long?,
                                        val genre: String?,
                                        val officialSite: String?,
                                        val fullRuntime: Long?,
                                        val premiered: String?,
                                        val imageMedium: String?,
                                        val imageOriginal: String?,
                                        val episodeOrder: Int?,
                                        val watchState: String) : Parcelable {

    constructor(tvShowModel: TvShowModel) : this(
            id = tvShowModel.id,
            imdbId = tvShowModel.imdbId,
            name = tvShowModel.name,
            network = tvShowModel.network,
            status = tvShowModel.status,
            summary = tvShowModel.summary,
            episodeRuntime = tvShowModel.episodeRuntime,
            genre = tvShowModel.genre,
            officialSite = tvShowModel.officialSite,
            fullRuntime = tvShowModel.fullRuntime,
            premiered = tvShowModel.premiered,
            imageMedium = tvShowModel.imageMedium,
            imageOriginal = tvShowModel.imageOriginal,
            episodeOrder = tvShowModel.episodeOrder,
            watchState = tvShowModel.watchState)

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString())

    fun mapToTvShowModel(): TvShowModel {
        return TvShowModel(
                this.id,
                this.imdbId,
                this.name,
                this.network,
                this.status,
                this.summary,
                this.episodeRuntime,
                this.genre,
                this.officialSite,
                this.fullRuntime,
                this.premiered,
                this.imageMedium,
                this.imageOriginal,
                this.episodeOrder,
                this.watchState)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(imdbId)
        parcel.writeString(name)
        parcel.writeString(network)
        parcel.writeString(status)
        parcel.writeString(summary)
        parcel.writeValue(episodeRuntime)
        parcel.writeString(genre)
        parcel.writeString(officialSite)
        parcel.writeValue(fullRuntime)
        parcel.writeString(premiered)
        parcel.writeString(imageMedium)
        parcel.writeString(imageOriginal)
        parcel.writeInt(episodeOrder!!)
        parcel.writeString(watchState)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TvShowModelParcelable> {
        override fun createFromParcel(parcel: Parcel): TvShowModelParcelable {
            return TvShowModelParcelable(parcel)
        }

        override fun newArray(size: Int): Array<TvShowModelParcelable?> {
            return arrayOfNulls(size)
        }
    }
}
