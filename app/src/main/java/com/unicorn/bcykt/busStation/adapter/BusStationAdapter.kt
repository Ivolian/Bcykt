package com.unicorn.bcykt.busStation.adapter

import com.amap.api.services.core.PoiItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.bcykt.R

class BusStationAdapter : BaseQuickAdapter<PoiItem, BaseViewHolder>(R.layout.item_bus_station) {

    override fun convert(helper: BaseViewHolder, item: PoiItem) {
        with(item){
            title.let { helper.setText(R.id.tvName, it) }
            distance.let { helper.setText(R.id.tvDistance,"${it}米") }
            snippet.replace(";",",").let { helper.setText(R.id.tvLines, it) }
        }
    }

}

