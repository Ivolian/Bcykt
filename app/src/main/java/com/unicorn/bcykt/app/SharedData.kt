package com.unicorn.bcykt.app

import com.amap.api.services.core.LatLonPoint

object SharedData {

    // 上海
    val cityCode = "201"
    // 公交线路
    val busCode = "150700"
    // 搜索半径
    val radius = 1000
    // 当前坐标
    lateinit var latLonPoint: LatLonPoint

}