package pl.hypeapp.presentation.timecalculator

import pl.hypeapp.domain.model.search.BasicSearchResultModel
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.gettvshow.GetTvShowUseCase
import pl.hypeapp.domain.usecase.search.BasicSearchUseCase
import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class TimeCalculatorPresenter @Inject constructor(private val searchUseCase: BasicSearchUseCase,
                                                  private val getTvShowUseCase: GetTvShowUseCase)
    : Presenter<TimeCalculatorView>() {

    var isFirstRun = true

    val addedTvShows: ArrayList<String> = arrayListOf()

    var model: List<BasicSearchResultModel> = emptyList()

    override fun onAttachView(view: TimeCalculatorView) {
        super.onAttachView(view)
        this.view?.initAdapter()
        this.view?.initSearchView()
        this.view?.initNavigationDrawer()
    }

    override fun onDetachView() {
        super.onDetachView()
        searchUseCase.dispose()
        getTvShowUseCase.dispose()
    }

    fun executeQuery(query: String) {
        searchUseCase.execute(SearchObserver(), BasicSearchUseCase.Params.createQuery(query))
    }

    fun onSearchQuerySubmit(query: String?) {
        view?.dismissSearchView()
        if (model.isEmpty()) {
            this.view?.showErrorToast()
            return
        }
        val filteredModel: BasicSearchResultModel? = model.firstOrNull { it.name.equals(query) }
        if (filteredModel != null) {
            if (isTvShowAlreadyAdded(filteredModel)) {
                this.view?.showTvShowAlreadyAddedToast()
            } else {
                addTvShow(filteredModel)
            }
        } else {
            showSuggestionsDialog(model.map { it.name }.toTypedArray())
        }
    }

    fun onSwiped(adapterPosition: Int?) {
        adapterPosition?.let { this.view?.deleteRecyclerItemAt(adapterPosition) }
    }

    fun onItemRemove(model: BasicSearchResultModel) {
        this.view?.decrementSelected()
        this.view?.subtractEpisodeOrder(model.episodeOrder!!)
        this.view?.subtractRuntimeWithAnimation(model.runtime!!)
        addedTvShows.remove(model.name)
    }

    fun onItemSelected(id: String?) = id?.let {
        getTvShowUseCase.execute(TvShowObserver(), GetTvShowUseCase.Params.createParams(it, true))
    }

    private fun isTvShowAlreadyAdded(filteredModel: BasicSearchResultModel) =
            addedTvShows.contains(filteredModel.name)

    private fun addTvShow(model: BasicSearchResultModel?) = model?.let {
        if (isFirstRun) {
            isFirstRun = false
            this.view?.startEnterAnimation()
            this.view?.setRecyclerItemWithDelay(it, 300L)
        } else {
            this.view?.setRecyclerItem(it)
        }
        addedTvShows.add(model.name!!)
        it.episodeOrder?.let { this.view?.addEpisodeOrder(it) }
        it.runtime?.let { this.view?.addRuntimeWithAnimation(it) }
        this.view?.incrementSelected()
    }

    private fun showSuggestionsDialog(suggestions: Array<String?>) {
        this.view?.showSuggestionsDialog(suggestions)
    }

    inner class SearchObserver : DefaultSingleObserver<List<BasicSearchResultModel>>() {
        override fun onSuccess(model: List<BasicSearchResultModel>) {
            if (!model.isEmpty())
                this@TimeCalculatorPresenter.view?.setSearchSuggestions(model
                        .map { it.name!! }
                        .take(3)
                        .toTypedArray())
            this@TimeCalculatorPresenter.model = model
        }

        override fun onError(error: Throwable) {
            this@TimeCalculatorPresenter.model = emptyList()
        }
    }

    inner class TvShowObserver : DefaultSingleObserver<TvShowModel>() {
        override fun onSuccess(model: TvShowModel) {
            this@TimeCalculatorPresenter.view?.openTvShowDetailsActivity(model)
        }

        override fun onError(error: Throwable) {
            this@TimeCalculatorPresenter.view?.showErrorToast()
        }
    }

}
