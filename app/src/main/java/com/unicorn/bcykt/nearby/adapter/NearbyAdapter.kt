package com.unicorn.bcykt.nearby.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.unicorn.bcykt.R
import com.unicorn.bcykt.nearby.entity.NearbyLine
import com.unicorn.bcykt.nearby.entity.NearbyStation
import com.unicorn.bcykt.nearby.renderer.NearbyLineRender
import com.unicorn.bcykt.nearby.renderer.NearbyStationRender

class NearbyAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(null) {

    init {
        addItemType(NearbyStation.type, R.layout.item_nearby_station)
        addItemType(NearbyLine.type, R.layout.item_nearby_line)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
        when (item.itemType) {
            NearbyStation.type -> NearbyStationRender(this,helper, item as NearbyStation).render()
            NearbyLine.type -> NearbyLineRender(helper, item as NearbyLine).render()
        }
    }

}

