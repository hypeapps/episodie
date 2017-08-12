package pl.hypeapp.episodie.ui.features.tvshowdetails.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_about_tv_show.image_view_about_ic_official_website
import kotlinx.android.synthetic.main.fragment_about_tv_show.image_view_info_ic_network
import kotlinx.android.synthetic.main.fragment_about_tv_show.text_view_about_genres
import kotlinx.android.synthetic.main.fragment_about_tv_show.text_view_about_network
import kotlinx.android.synthetic.main.fragment_about_tv_show.text_view_about_official_website
import kotlinx.android.synthetic.main.fragment_about_tv_show.text_view_about_runtime
import kotlinx.android.synthetic.main.fragment_about_tv_show.text_view_about_summary
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.di.components.DaggerFragmentComponent
import pl.hypeapp.episodie.di.components.FragmentComponent
import pl.hypeapp.episodie.extensions.setRuntime
import pl.hypeapp.episodie.extensions.setTextFromHtml
import pl.hypeapp.episodie.ui.base.BaseFragment
import pl.hypeapp.episodie.ui.viewmodel.TvShowModelParcelable
import pl.hypeapp.presentation.aboutvshow.AboutTvShowPresenter
import pl.hypeapp.presentation.aboutvshow.AboutTvShowView
import javax.inject.Inject

class AboutTvShowFragment : BaseFragment(), AboutTvShowView {

    override fun getLayoutRes(): Int = R.layout.fragment_about_tv_show

    @Inject
    lateinit var presenter: AboutTvShowPresenter

    private val component: FragmentComponent
        get() = DaggerFragmentComponent.builder()
                .appComponent((activity.application as App).component)
                .build()

    override fun getModel(): TvShowModel {
        val tvShowModelParcelable: TvShowModelParcelable = arguments.getParcelable(ARGUMENT_INFO_ABOUT_TV_SHOW)
        val tvShowModel: TvShowModel = tvShowModelParcelable.mapToTvShowModel()
        return tvShowModel
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

    override fun setSummary(summary: String?) = text_view_about_summary.setTextFromHtml(summary)

    override fun setEpisodeRuntime(runtime: Long?) = text_view_about_runtime.setRuntime(runtime)

    override fun setGenre(genre: String?) {
        text_view_about_genres.text = genre
    }

    override fun setNetwork(network: String?) {
        text_view_about_network.visibility = View.VISIBLE
        image_view_info_ic_network.visibility = View.VISIBLE
        text_view_about_network.text = network
    }

    override fun setOfficialSiteVisible() {
        image_view_about_ic_official_website.visibility = View.VISIBLE
        text_view_about_official_website.visibility = View.VISIBLE
    }

    override fun openBrowserIntent(url: String?) {
        val browserIntent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity.startActivity(browserIntent)
    }

    @OnClick(R.id.text_view_about_official_website, R.id.image_view_about_ic_official_website)
    fun onOfficialSitePressed() {
        presenter.onOfficialSitePressed()
    }

    @OnClick(R.id.text_view_about_imdb, R.id.image_view_about_ic_imdb)
    fun onImdbSitePressed() {
        presenter.onImdbSitePressed()
    }

    @OnClick(R.id.text_view_about_tv_maze, R.id.image_view_about_ic_tv_maze)
    fun onTvMazeSitePressed() {
        presenter.onTvMazeSitePressed()
    }

    companion object {
        val ARGUMENT_INFO_ABOUT_TV_SHOW = "TV_SHOW_MODEL"
        private val bundle: Bundle = Bundle()

        fun newInstance(tvShowModel: TvShowModel?): AboutTvShowFragment = with(AboutTvShowFragment()) {
            bundle.putParcelable(ARGUMENT_INFO_ABOUT_TV_SHOW, TvShowModelParcelable(tvShowModel))
            this.arguments = bundle
            return this
        }
    }

}
