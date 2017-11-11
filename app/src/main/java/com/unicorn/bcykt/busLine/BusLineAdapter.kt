package com.unicorn.bcykt.busLine

import com.amap.api.services.busline.BusLineItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.bcykt.R
import org.joda.time.DateTime

class BusLineAdapter : BaseQuickAdapter<BusLineItem, BaseViewHolder>(R.layout.item_bus_line) {

    override fun convert(helper: BaseViewHolder, item: BusLineItem) {
        with(item) {
            busLineName.substring(0, busLineName.indexOf("(")).let { helper.setText(R.id.tvName, it) }
            "从${originatingStation}开往${terminalStation}".let { helper.setText(R.id.tvDirection, it) }
            "${basicPrice}元".let { helper.setText(R.id.tvPrice, it) }
            DateTime(firstBusTime).toString("hh:mm:ss").let { helper.setText(R.id.tvStartTime, it) }
            DateTime(lastBusTime).toString("hh:mm:ss").let { helper.setText(R.id.tvEndTime, it) }
        }
    }

}