package pl.hypeapp.domain.model.reminder


data class EpisodeReminderModel(val episodeId: String,
                                val tvShowId: String,
                                val seasonId: String,
                                val tvShowName: String,
                                val name: String,
                                val episodeNumber: Int,
                                val timestamp: Long,
                                val jobId: Int) : Comparable<Long> {

    override fun compareTo(other: Long): Int = if (timestamp < other) 1 else 0
}
