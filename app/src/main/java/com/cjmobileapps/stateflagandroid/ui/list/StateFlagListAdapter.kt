package com.cjmobileapps.stateflagandroid.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cjmobileapps.stateflagandroid.R
import com.cjmobileapps.stateflagandroid.data.model.StateFlagData
import com.cjmobileapps.stateflagandroid.databinding.ItemStateFlagBinding
import com.squareup.picasso.Picasso

class StateFlagListAdapter(
    var stateFlagDataList: List<StateFlagData> = emptyList(),
    val onStateFlagClicked: (stateFlagData: StateFlagData) -> Unit = {}
) : RecyclerView.Adapter<StateFlagListAdapter.StateFlagListHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StateFlagListHolder {
        val itemBinding =
            ItemStateFlagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StateFlagListHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: StateFlagListHolder, position: Int) {
        holder.bind(stateFlagDataList[position])
    }

    override fun getItemCount() = stateFlagDataList.size

    inner class StateFlagListHolder(private val itemBinding: ItemStateFlagBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(stateFlagData: StateFlagData) {
            itemBinding.stateFlagItemStateName.text = stateFlagData.title
            itemBinding.stateFlagItemUrl.text = stateFlagData.url

            Picasso.get()
                .load(stateFlagData.thumbnail)
                .placeholder(R.color.stateFlag_black)
                .error(R.color.stateFlag_black)
                .into(itemBinding.stateFlagItemImage)

            itemBinding.root.setOnClickListener {
                onStateFlagClicked.invoke(stateFlagData)
            }
        }
    }
}
