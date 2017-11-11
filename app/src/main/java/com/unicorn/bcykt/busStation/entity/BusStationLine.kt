package com.unicorn.bcykt.busStation.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

class BusStationLine(val lineName: String, val time: String) : MultiItemEntity {

    companion object {
        val type = 1
    }

    override fun getItemType() = type

}