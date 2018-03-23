package pl.hypeapp.episodie.ui.features.seasons.adapter

import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import pl.hypeapp.domain.model.tvshow.SeasonModel

class SeasonRecyclerAdapter(private val onChangeWatchStateListener: OnChangeWatchStateListener)
    : GroupAdapter<ViewHolder>(), OnToggleGroupListener {

    private val expandableGroups: ArrayList<ExpandableGroup> = ArrayList()

    private var items: ArrayList<SeasonModel> = ArrayList()

    fun addItems(items: ArrayList<SeasonModel>) {
        this.items.addAll(items)
        this.items.forEach {
            val seasonItemExpandableItem = SeasonItemHolder(it, this, onChangeWatchStateListener)
            val expandableGroup = ExpandableGroup(seasonItemExpandableItem)
            it.episodes?.forEach {
                expandableGroup.add(EpisodeItemHolder(it, this, onChangeWatchStateListener))
            }
            this@SeasonRecyclerAdapter.add(expandableGroup)
            expandableGroups.add(expandableGroup)
        }
        notifyDataSetChanged()
    }

    fun updateItems(items: ArrayList<SeasonModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    // If clicked on expandable group collapse all expanded group and reveal clicked group.
    override fun onToggleExpandableGroup(group: ExpandableGroup) {
        expandableGroups.forEach {
            if (it == group) {
                group.onToggleExpanded()
            } else {
                if (it.isExpanded) it.onToggleExpanded()
            }
        }
    }

    // If clicked on group child, collapse all expanded group
    override fun onToggleGroup() {
        expandableGroups.forEach {
            if (it.isExpanded) it.onToggleExpanded()
        }
    }

}
