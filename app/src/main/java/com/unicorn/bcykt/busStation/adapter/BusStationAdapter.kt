package com.unicorn.bcykt.busStation.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.unicorn.bcykt.R
import com.unicorn.bcykt.busStation.entity.BusStation
import com.unicorn.bcykt.busStation.entity.BusStationLine
import com.unicorn.bcykt.busStation.renderer.BusStationRender

class BusStationAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(null) {

    init {
        addItemType(BusStation.type, R.layout.item_bus_station)
        addItemType(BusStationLine.type, R.layout.item_bus_station_line)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
        when (item.itemType) {
            BusStation.type -> {
                item as BusStation
                helper.itemView.setOnClickListener {
                    val pos = helper.adapterPosition
                    if (item.isExpanded) {
                        collapse(pos)
                    } else {
                        expand(pos)
//                        expandAll();
                    }
                }
                BusStationRender(helper, item ).render()
            }
            BusStationLine.type -> {
                item as BusStationLine
            }
        }
    }

}

