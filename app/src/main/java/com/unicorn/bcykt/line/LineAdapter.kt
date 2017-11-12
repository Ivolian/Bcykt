package com.unicorn.bcykt.line

import com.amap.api.services.busline.BusLineItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.bcykt.R

class LineAdapter : BaseQuickAdapter<BusLineItem, BaseViewHolder>(R.layout.item_bus_line) {

    override fun convert(helper: BaseViewHolder, item: BusLineItem) {
        with(item) {
            busLineName.substring(0, busLineName.indexOf("(")).let { helper.setText(R.id.tvName, it) }
            "开往${terminalStation}方向".let { helper.setText(R.id.tvDirection, it) }
            "票价:${basicPrice}元".let { helper.setText(R.id.tvPrice, it) }
            "首班:05:35".let { helper.setText(R.id.tvFirstBusTime, it) }
            "末班:22:35".let { helper.setText(R.id.tvLastBusTime, it) }
        }
    }

}