package com.unicorn.bcykt.nearby.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

class NearbyLine(val lineName: String, val time: String) : MultiItemEntity {

    companion object {
        val type = 1
    }

    override fun getItemType() = type

}