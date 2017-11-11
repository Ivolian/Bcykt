package com.unicorn.bcykt.busStation.renderer

import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.bcykt.R
import com.unicorn.bcykt.busStation.entity.BusStationLine

class BusStationLineRender(private val helper: BaseViewHolder, private val busStationLine: BusStationLine) {

    fun render() {
        with(busStationLine) {
            lineName.let { helper.setText(R.id.tvLineName, it) }
            "约${time}后到达".let { helper.setText(R.id.tvTime, it) }
        }
    }

}