package com.unicorn.bcykt.bus


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.busline.BusStationQuery
import com.amap.api.services.busline.BusStationSearch
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.bcykt.R
import com.unicorn.bcykt.app.Constant
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
//        setCameraPosition(aMap.getCameraPosition())//保存地图状态
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

//        dragLayout.setAttachScrollView(frameLayout)
//
//
//        slideUp = SlideUpBuilder(recyclerView)
//                .withListeners(object : SlideUp.Listener.Events {
//                    override fun onSlide(percent: Float) {
//                        dim.alpha = 1 - percent / 100
//                    }
//
//                    override fun onVisibilityChanged(visibility: Int) {
//
//                    }
//                })
//                .withStartGravity(Gravity.BOTTOM)
//                .withLoggingEnabled(true)
//                .withGesturesEnabled(true)
//                .withStartState(SlideUp.State.HIDDEN)
//                .build()
//        dragLayout.setAttachScrollView(recyclerView)
    }

    private fun searchStation(keyword: String) {
        BusStationSearch(context, BusStationQuery(keyword, Constant.cityCode)).apply {
            setOnBusStationSearchListener { result, code ->
                if (code != 1000) {
                    ToastUtils.showShort("错误码:" + code)
                } else {
                    BusStationOverlay(mapView.map,result.busStations).addToMap()
                }
            }
        }.searchBusStationAsyn()
    }


}
