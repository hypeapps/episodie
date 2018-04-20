package pl.hypeapp.domain.usecase.episodereminder

import io.reactivex.Completable
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.reminder.EpisodeReminderModel
import pl.hypeapp.domain.model.tvshow.EpisodeModel
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel
import pl.hypeapp.domain.repository.AllSeasonsRepository
import pl.hypeapp.domain.repository.EpisodeReminderRepository
import pl.hypeapp.domain.usecase.base.AbsRxCompletableUseCase
import java.util.*
import javax.inject.Inject

class SyncEpisodeRemindersUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                      postExecutionThread: PostExecutionThread,
                                                      private val repository: EpisodeReminderRepository,
                                                      private val allEpisodesUseCase: AllSeasonsRepository)
    : AbsRxCompletableUseCase<SyncEpisodeRemindersUseCase.Params>(threadExecutor, postExecutionThread) {


    override fun createCompletable(params: Params): Completable {
        if (params.reminders != null) {
            return repository.insertReminders(params.reminders)
        } else {
            params.tvShowId?.let {
                return allEpisodesUseCase.getAllSeason(params.tvShowId, true)
                        .flatMapCompletable { t: TvShowExtendedModel ->
                            val episodeModels: List<EpisodeModel>? = t.seasons
                                    ?.filter { it.seasonId == params.seasonId }
                                    ?.flatMap { it.episodes!! }
                            if (episodeModels != null && episodeModels.isNotEmpty()) {
                                val reminders: Params = Params.Companion.createParams(episodeModels, t.name ?: "")
                                repository.insertReminders(reminders.reminders!!)
                            } else {
                                Completable.error(Throwable("Unable to get reminders"))
                            }
                        }
            }
        }
        return Completable.error(Throwable("Null params"))
    }

    class Params private constructor(val reminders: List<EpisodeReminderModel>?,
                                     val tvShowId: String?,
                                     val seasonId: String?) {
        companion object {
            fun createParams(episodeModels: List<EpisodeModel>, title: String): Params {
                val episodeReminders: List<EpisodeReminderModel> =
                        episodeModels.filter { isEpisodeBeforePremiereDate(it) }
                                .map {
                                    EpisodeReminderModel(
                                            episodeId = it.episodeId!!,
                                            tvShowId = it.tvShowId!!,
                                            seasonId = it.seasonId!!,
                                            tvShowName = title,
                                            name = it.name ?: "",
                                            episodeNumber = it.episodeNumber ?: 0,
                                            timestamp = setExactTime(it.premiereDate ?: Date()),
                                            jobId = it.episodeNumber ?: 0)
                                }.toList()
                return Params(episodeReminders, null, null)
            }

            fun createParams(tvShowId: String?, seasonId: String?): Params {
                return Params(null, tvShowId, seasonId)
            }

            private fun setExactTime(premiereDate: Date): Long {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = premiereDate.time
                calendar.set(Calendar.HOUR_OF_DAY, 11)
                return calendar.timeInMillis - Calendar.getInstance().timeInMillis
            }

            private fun isEpisodeBeforePremiereDate(episodeModel: EpisodeModel) =
                    (episodeModel.premiereDate != null && Calendar.getInstance().time.before(episodeModel.premiereDate))

        }
    }
}



