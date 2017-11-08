package com.unicorn.bcykt.bus

import com.amap.api.services.busline.BusStationItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.bcykt.R

class BusStationAdapter : BaseQuickAdapter<BusStationItem, BaseViewHolder>(R.layout.item_bus_station) {

    override fun convert(helper: BaseViewHolder, item: BusStationItem) {
        item.busStationName.let { helper.setText(R.id.tvName, it) }
        item.busLineItems
                .map { it.busLineName }
                .map { it.substring(0, it.indexOf('(')) }
                .distinct()
                .joinToString(",")
                .let {
                    helper.setText(R.id.tvLines, it)
                }
    }

}

