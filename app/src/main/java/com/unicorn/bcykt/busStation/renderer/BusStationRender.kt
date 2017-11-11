package com.unicorn.bcykt.busStation.renderer

import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.bcykt.busStation.entity.BusStation


class BusStationRender(private val helper: BaseViewHolder, private val busStation: BusStation) {

    fun render() {
        with(busStation.poiItem) {
            title.let { helper.setText(com.unicorn.bcykt.R.id.tvName, it) }
            "距您约${distance}米".let { helper.setText(com.unicorn.bcykt.R.id.tvDistance, it) }
        }
    }

}