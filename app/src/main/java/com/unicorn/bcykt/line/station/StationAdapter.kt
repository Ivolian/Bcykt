package com.unicorn.bcykt.line.station

import com.alorma.timeline.TimelineView
import com.alorma.timeline.TimelineView.*
import com.amap.api.services.busline.BusStationItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.bcykt.R

class StationAdapter : BaseQuickAdapter<BusStationItem, BaseViewHolder>(R.layout.item_station) {

    override fun convert(helper: BaseViewHolder, item: BusStationItem) {
        with(item) {
            busStationName.let { helper.setText(R.id.tvName, it) }
            val timeline = helper.getView<TimelineView>(R.id.timeline1)
            when{
                helper.adapterPosition == 0 -> timeline.timelineType = TYPE_START
                helper.adapterPosition == data.size-1 -> timeline.timelineType = TYPE_END
                else -> timeline.timelineType = TYPE_MIDDLE
            }
        }
    }

}