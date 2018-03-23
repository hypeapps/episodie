package pl.hypeapp.domain.model.premiere

import java.text.SimpleDateFormat
import java.util.*

class PremiereDateModel(val id: String?,
                        val imdbId: String?,
                        val name: String?,
                        val genre: String?,
                        val summary: String?,
                        val episodeOrder: Int?,
                        val status: String?,
                        val episodeRuntime: Long?,
                        val fullRuntime: Long?,
                        val premiere: String?,
                        val imageMedium: String?,
                        val imageOriginal: String?,
                        var notificationScheduled: Boolean = false) {

    fun premiereDateFormatted(): Date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(this.premiere)
}

