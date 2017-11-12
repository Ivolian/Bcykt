package com.unicorn.bcykt.nearby.renderer

import com.chad.library.adapter.base.BaseViewHolder
import com.unicorn.bcykt.R
import com.unicorn.bcykt.nearby.entity.NearbyLine

class NearbyLineRender(private val helper: BaseViewHolder, private val item: NearbyLine) {

    fun render() {
        with(item) {
            lineName.let { helper.setText(R.id.tvLineName, it) }
            "约${time}后到达".let { helper.setText(R.id.tvTime, it) }
        }
    }

}