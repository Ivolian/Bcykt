package com.unicorn.bcykt.busStation.entity

import com.amap.api.services.core.PoiItem
import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity

class BusStation(val poiItem: PoiItem) : MultiItemEntity,AbstractExpandableItem<BusStationLine>() {

    companion object {
        val type = 0
    }

    override fun getLevel(): Int {
return        0
    }

    override fun getItemType() = type

}