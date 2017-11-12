package com.unicorn.bcykt.nearby.entity

import com.amap.api.services.core.PoiItem
import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity

class NearbyStation(val poiItem: PoiItem) : MultiItemEntity, AbstractExpandableItem<NearbyLine>() {

    companion object {
        val type = 0
    }

    override fun getLevel() = -1

    override fun getItemType() = type

}