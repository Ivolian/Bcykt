package com.unicorn.bcykt.nearby.renderer

import android.widget.ImageView
import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.bcykt.R
import com.unicorn.bcykt.nearby.adapter.NearbyAdapter
import com.unicorn.bcykt.nearby.entity.NearbyStation


class NearbyStationRender(private val nearbyAdapter: NearbyAdapter,
                          private val helper: BaseViewHolder, private val item: NearbyStation) {

    fun render() {
        helper.getView<ImageView>(R.id.ivArrow).setOnClickListener {
            val pos = helper.adapterPosition
            if (item.isExpanded) {
                nearbyAdapter.collapse(pos)
            } else {
                nearbyAdapter.expand(pos)
            }
        }
        with(item.poiItem) {
            title.let { helper.setText(com.unicorn.bcykt.R.id.tvName, it) }
            "距您约${distance}米".let { helper.setText(com.unicorn.bcykt.R.id.tvDistance, it) }
        }
    }

}