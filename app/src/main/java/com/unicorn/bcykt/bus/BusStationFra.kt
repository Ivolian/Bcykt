package com.unicorn.bcykt.bus

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.services.busline.BusStationItem
import com.amap.api.services.busline.BusStationQuery
import com.amap.api.services.busline.BusStationSearch
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.bcykt.R
import com.unicorn.bcykt.app.Constant
import kotlinx.android.synthetic.main.frg_bus_station.*
import me.yokeyword.fragmentation.SupportFragment

class BusStationFra : SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frg_bus_station, container, false)
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        searchStation("四川北路")
    }

    private fun searchStation(keyword: String) {
        BusStationSearch(context, BusStationQuery(keyword, Constant.cityCode)).apply {
            setOnBusStationSearchListener { result, code ->
                if (code != 1000) {
                    ToastUtils.showShort("错误码:" + code)
                } else {
                    renderBusStations(result.busStations)
                }
            }
        }.searchBusStationAsyn()
    }

    private fun renderBusStations(stations: List<BusStationItem>) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        BusStationAdapter().apply {
            bindToRecyclerView(recyclerView)
            setNewData(stations)
        }
    }

}
