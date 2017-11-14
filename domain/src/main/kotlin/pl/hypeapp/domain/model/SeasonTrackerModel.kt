package pl.hypeapp.domain.model

data class SeasonTrackerModel(val tvShowId: String,
                              val seasonId: String,
                              val watchedEpisodes: ArrayList<String>)
