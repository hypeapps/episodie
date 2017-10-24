package pl.hypeapp.episodie.ui.features.bingewatching.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import pl.hypeapp.domain.model.AllSeasonsModel
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.loadImage

class SelectSeasonDialog(context: Context, allSeasonsModel: AllSeasonsModel,
                         onDialogItemClickListener: OnDialogItemClickListener) : Dialog(context) {

    var recyclerView: RecyclerView

    private val groupAdapter: GroupAdapter<ViewHolder> = GroupAdapter()

    init {
        setContentView(R.layout.dialog_select_season)
        this.setCancelable(true)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.attributes.windowAnimations = R.style.EpisodieTheme_DialogAnimation
        recyclerView = this.findViewById(R.id.recycler_view_select_season_dialog)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = groupAdapter
        }
        allSeasonsModel.seasons?.forEach {
            groupAdapter.add(SeasonItemHolder(it, onDialogItemClickListener))
        }
        populateHeader(allSeasonsModel)
    }

    private fun populateHeader(allSeasonsModel: AllSeasonsModel) {
        this.findViewById<TextView>(R.id.text_view_select_dialog_title).text = allSeasonsModel.name
        this.findViewById<ImageView>(R.id.image_view_tv_show_details_cover).loadImage(allSeasonsModel.imageMedium)
        this.findViewById<TextView>(R.id.text_view_select_dialog_premiered).text = String
                .format(context.getString(R.string.item_all_format_premiered), allSeasonsModel.premiered)
        this.findViewById<TextView>(R.id.text_view_select_dialog_status).text =
                String.format(context.getString(R.string.tv_show_details_status), allSeasonsModel.status)
    }

}
