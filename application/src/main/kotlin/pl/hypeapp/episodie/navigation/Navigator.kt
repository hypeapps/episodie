package pl.hypeapp.episodie.navigation

import android.content.Context
import android.content.Intent
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.episodie.ui.features.tvshowdetails.TvShowDetailsActivity

object Navigator {

    fun startTvShowDetails(context: Context, tvShowModel: TvShowModel) {
        val intent: Intent = Intent(context, TvShowDetailsActivity::class.java)
        intent.putExtra(EXTRA_INTENT_TV_SHOW_MODEL, tvShowModel)
        context.startActivity(intent)
    }
}
