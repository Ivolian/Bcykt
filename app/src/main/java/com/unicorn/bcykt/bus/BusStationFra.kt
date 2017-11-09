package com.unicorn.bcykt.bus


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.busline.BusStationItem
import com.amap.api.services.busline.BusStationQuery
import com.amap.api.services.busline.BusStationSearch
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.bcykt.R
import com.unicorn.bcykt.app.Constant
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.fra_bus_station.*
import me.yokeyword.fragmentation.SupportFragment




class BusStationFra : SupportFragment() {

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fra_bus_station, container, false)
        searchStation("四川北路")
        return view
    }


    fun renderMap() {
        mapView.map.apply {
            myLocationStyle = MyLocationStyle().apply {
                // 定位一次，且将视角移动到地图中心点。
                myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
//                myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.poi2))
            }
            moveCamera(CameraUpdateFactory.zoomTo(14f))
            uiSettings.isMyLocationButtonEnabled = true
            // 开始定位
            isMyLocationEnabled = true
            setOnMyLocationChangeListener { location ->

                var extras = location.extras
                val street = extras.getString("Street")
                searchStation(street)
//                ToastUtils.showShort(extras.toString())
            }
        }
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        renderMap()

//        dragLayout.setAttachScrollView(recyclerView)
    }

    private fun searchStation(keyword: String) {
        BusStationSearch(context, BusStationQuery(keyword, Constant.cityCode)).apply {
            setOnBusStationSearchListener { result, code ->
                if (code != 1000) {
                    ToastUtils.showShort("错误码:" + code)
                } else {
                    if (result.busStations.size!=0) {
                        BusStationOverlay(mapView.map, result.busStations).addToMap()
                        renderBusStations(result.busStations)
                    }
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
        recyclerView.addItemDecoration(
                HorizontalDividerItemDecoration.Builder(context)
                        .colorResId(R.color.material_grey_400)
                        .size(1)
                        .build())

    }



}
