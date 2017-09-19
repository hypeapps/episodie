package pl.hypeapp.episodie.ui.features.tvshowsummary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_tv_show_summary.*
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.di.components.DaggerFragmentComponent
import pl.hypeapp.episodie.di.components.FragmentComponent
import pl.hypeapp.episodie.extensions.setRuntime
import pl.hypeapp.episodie.ui.base.BaseFragment
import pl.hypeapp.episodie.ui.viewmodel.TvShowModelParcelable
import pl.hypeapp.presentation.tvshowsummary.TvShowSummaryPresenter
import pl.hypeapp.presentation.tvshowsummary.TvShowSummaryView
import javax.inject.Inject

class TvShowSummaryFragment : BaseFragment(), TvShowSummaryView {

    override fun getLayoutRes(): Int = R.layout.fragment_tv_show_summary

    @Inject lateinit var presenter: TvShowSummaryPresenter

    private val component: FragmentComponent
        get() = DaggerFragmentComponent.builder()
                .appComponent((activity.application as App).component)
                .build()

    override fun getModel(): TvShowModel {
        val tvShowModelParcelable: TvShowModelParcelable = arguments.getParcelable(ARGUMENT_INFO_ABOUT_TV_SHOW)
        return tvShowModelParcelable.mapToTvShowModel()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        component.inject(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.onAttachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDetachView()
    }

    override fun setSummary(summary: String?) {
        text_view_tv_show_summary.text = summary
    }

    override fun setEpisodeRuntime(runtime: Long?) = text_view_tv_show_summary_runtime.setRuntime(runtime)

    override fun setGenre(genre: String?) {
        text_view_tv_show_summary_genres.text = genre
    }

    override fun setNetwork(network: String?) {
        text_view_tv_show_summary_network.visibility = View.VISIBLE
        image_view_tv_show_summary_ic_network.visibility = View.VISIBLE
        text_view_tv_show_summary_network.text = network
    }

    override fun setOfficialSiteVisible() {
        image_view_tv_show_info_ic_official_website.visibility = View.VISIBLE
        text_view_tv_show_summary_official_website.visibility = View.VISIBLE
    }

    override fun openBrowserIntent(url: String?) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity.startActivity(browserIntent)
    }

    @OnClick(R.id.text_view_tv_show_summary_official_website,
            R.id.image_view_tv_show_info_ic_official_website)
    fun onOfficialSitePressed() {
        presenter.onOfficialSitePressed()
    }

    @OnClick(R.id.text_view_tv_show_summary_imdb,
            R.id.image_view_tv_show_summary_ic_imdb)
    fun onImdbSitePressed() {
        presenter.onImdbSitePressed()
    }

    @OnClick(R.id.text_view_tv_show_summary_tv_maze,
            R.id.image_view_tv_show_summary_ic_tv_maze)
    fun onTvMazeSitePressed() {
        presenter.onTvMazeSitePressed()
    }

    companion object {
        val ARGUMENT_INFO_ABOUT_TV_SHOW = "TV_SHOW_MODEL"
        private val bundle: Bundle = Bundle()

        fun newInstance(tvShowModel: TvShowModelParcelable): TvShowSummaryFragment = with(TvShowSummaryFragment()) {
            bundle.putParcelable(ARGUMENT_INFO_ABOUT_TV_SHOW, tvShowModel)
            this.arguments = bundle
            return this
        }
    }

}
