package pl.hypeapp.presentation.search

import pl.hypeapp.domain.model.BasicSearchResultModel
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.gettvshow.GetTvShowUseCase
import pl.hypeapp.domain.usecase.search.BasicSearchUseCase
import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class SearchPresenter @Inject constructor(private val searchUseCase: BasicSearchUseCase,
                                          private val getTvShowUseCase: GetTvShowUseCase) : Presenter<SearchView>() {

    var model: List<BasicSearchResultModel> = emptyList()

    override fun onAttachView(view: SearchView) {
        super.onAttachView(view)
        this.view?.setNavigationBarOptions()
        this.view?.initNavigationDrawer()
        this.view?.initSearchView()
        this.view?.initRecyclerAdapter()
        this.view?.loadViewModel()
    }

    override fun onDetachView() {
        super.onDetachView()
        searchUseCase.dispose()
    }

    fun executeQuery(query: String) {
        searchUseCase.execute(SearchObserver(), BasicSearchUseCase.Params.createQuery(query))
    }

    fun onSearchQuerySubmit() {
        view?.dismissSearchView()
        if (model.isEmpty()) {
            this.view?.showErrorToast()
            return
        }
        this.view?.populateRecyclerWithSuggestions(model)
    }

    fun onItemClick(tvShowId: String) {
        getTvShowUseCase.execute(GetTvShowObserver(), GetTvShowUseCase.Params.createParams(tvShowId, false))
    }

    inner class GetTvShowObserver : DefaultSingleObserver<TvShowModel>() {
        override fun onSuccess(model: TvShowModel) {
            this@SearchPresenter.view?.navigateToTvShowDetails(model)
        }

        override fun onError(error: Throwable) {
            this@SearchPresenter.view?.showErrorToast()
        }
    }

    inner class SearchObserver : DefaultSingleObserver<List<BasicSearchResultModel>>() {
        override fun onSuccess(model: List<BasicSearchResultModel>) {
            if (!model.isEmpty())
                this@SearchPresenter.view?.setSearchViewSuggestions(model
                        .map { it.name!! }
                        .take(4)
                        .toTypedArray())
            this@SearchPresenter.model = model
        }

        override fun onError(error: Throwable) {
            this@SearchPresenter.model = emptyList()
        }
    }
}
