package pl.hypeapp.episodie.ui.features.search

import android.os.Bundle
import butterknife.OnClick
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.ui.base.BaseActivity

class SearchActivity : BaseActivity() {

    override fun getLayoutRes(): Int = R.layout.activity_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //    var i = 0
//
    @OnClick(R.id.btn)
    fun onBtn() {
//        alerter.show(" ", " ")
    }

}
