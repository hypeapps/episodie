package pl.hypeapp.episodie.ui.features.search

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_search.*
import pl.hypeapp.domain.model.search.BasicSearchResultModel
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.di.components.ActivityComponent
import pl.hypeapp.episodie.di.components.DaggerActivityComponent
import pl.hypeapp.episodie.di.module.ActivityModule
import pl.hypeapp.episodie.extensions.getNavigationBarSize
import pl.hypeapp.episodie.extensions.getStatusBarHeight
import pl.hypeapp.episodie.extensions.isLandscapeOrientation
import pl.hypeapp.episodie.extensions.isNavigationBarLandscape
import pl.hypeapp.episodie.navigation.Navigator
import pl.hypeapp.episodie.ui.base.BaseViewModelActivity
import pl.hypeapp.episodie.ui.features.navigationdrawer.NavigationDrawer
import pl.hypeapp.episodie.ui.features.search.adapter.OnSearchSuggestionClickListener
import pl.hypeapp.episodie.ui.features.search.adapter.SearchSuggestionsRecyclerAdapter
import pl.hypeapp.episodie.ui.viewmodel.BasicSearchResultViewModel
import pl.hypeapp.episodie.ui.viewmodel.SearchViewModel
import pl.hypeapp.presentation.search.SearchPresenter
import pl.hypeapp.presentation.search.SearchView
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchActivity : BaseViewModelActivity<SearchViewModel>(), SearchView, MaterialSearchView.OnQueryTextListener,
        OnSearchSuggestionClickListener {

    override val viewModelClass: Class<SearchViewModel> = SearchViewModel::class.java

    override fun getLayoutRes(): Int = R.layout.activity_search

    @Inject lateinit var presenter: SearchPresenter

    @Inject
    lateinit var navigationDrawer: NavigationDrawer

    private lateinit var searchSuggestionsRecyclerAdapter: SearchSuggestionsRecyclerAdapter

    private lateinit var transitionView: View

    private val component: ActivityComponent
        get() = DaggerActivityComponent.builder()
                .appComponent((application as App).component)
                .activityModule(ActivityModule(this))
                .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        presenter.onAttachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetachView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            showSearchView()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        dismissSearchView()
        super.onBackPressed()
    }

    override fun loadViewModel() = viewModel.loadModel({
        searchSuggestionsRecyclerAdapter.addSuggestions(viewModel.searchSuggestions)
    })

    override fun initSearchView() = with(search_activity_search_view) {
        setOnQueryTextListener(this@SearchActivity)
        findViewById<View>(R.id.transparent_view).background = null
        setPadding(paddingLeft, resources.getStatusBarHeight(), paddingRight, paddingBottom)
        setSubmitOnClick(true)
        setSuggestionIcon(ContextCompat.getDrawable(this@SearchActivity, R.drawable.all_ic_diamond_checked_dark))
        disposable = RxTextView.textChanges(this.findViewById(R.id.searchTextView))
                .filter { it.length > 2 }
                .debounce(TIMEOUT_DEBOUNCE, TimeUnit.MILLISECONDS)
                .map { it.toString() }
                .distinctUntilChanged()
                .filter { !it.isEmpty() }
                .subscribe { presenter.executeQuery(it) }
    }

    override fun initNavigationDrawer() {
        toolbar_activity_all.apply {
            setSupportActionBar(this)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            setPadding(paddingLeft, resources.getStatusBarHeight(), paddingRight, paddingBottom)
        }
        lifecycle.addObserver(navigationDrawer)
    }

    override fun initRecyclerAdapter() {
        searchSuggestionsRecyclerAdapter = SearchSuggestionsRecyclerAdapter(this)
        search_suggestions_recycler_view.apply {
            setHasFixedSize(false)
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            val itemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation)
            itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.item_recycler_view_decoration)!!)
            addItemDecoration(itemDecoration)
            adapter = searchSuggestionsRecyclerAdapter
        }
    }

    override fun populateRecyclerWithSuggestions(suggestions: List<BasicSearchResultModel>) {
        viewModel.retainModel(suggestions)
        searchSuggestionsRecyclerAdapter.addSuggestions(viewModel.searchSuggestions)
    }

    override fun onItemClick(basicSearchResultViewModel: BasicSearchResultViewModel, view: View) {
        transitionView = view
        presenter.onItemClick(basicSearchResultViewModel.basicSearchResultModel.id!!)
    }

    override fun setNavigationBarOptions() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        if (isLandscapeOrientation() and !isNavigationBarLandscape()) {
            setLandscapeNavBarPaddingEnd(constraint_layout_search)
        }
    }

    override fun setSearchViewSuggestions(suggestions: Array<String>) =
            search_activity_search_view.setSuggestions(suggestions)

    override fun onQueryTextSubmit(query: String?): Boolean {
        presenter.onSearchQuerySubmit()
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun dismissSearchView() = with(search_activity_search_view) {
        if (isSearchOpen) {
            hideKeyboard(this@SearchActivity.currentFocus)
            dismissSuggestions()
            this.setSuggestions(arrayOf(""))
            findViewById<EditText>(R.id.searchTextView).text.clear()
        }
    }

    override fun navigateToTvShowDetails(tvShowModel: TvShowModel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Navigator.startTvShowDetailsWithSharedElement(this, tvShowModel, transitionView)
        } else {
            Navigator.startTvShowDetails(this, tvShowModel)
        }
    }

    override fun showErrorToast() = Toast
            .makeText(this, getString(R.string.all_toast_error_message), Toast.LENGTH_LONG).show()

    private fun showSearchView() = search_activity_search_view.showSearch()

    private fun setLandscapeNavBarPaddingEnd(vararg views: View) = views.forEach {
        with(it) {
            setPadding(paddingStart, paddingTop, (paddingEnd + getNavigationBarSize().x), paddingBottom)
        }
    }

    private companion object {
        val TIMEOUT_DEBOUNCE = 200L
    }

}
