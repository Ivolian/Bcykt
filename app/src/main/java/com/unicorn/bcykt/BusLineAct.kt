package com.unicorn.bcykt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.busline.BusLineQuery
import com.amap.api.services.busline.BusLineSearch
import kotlinx.android.synthetic.main.act_bus_line.*


class BusLineAct : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_bus_line)
        mapView.onCreate(savedInstanceState)
        renderMap()
        search("167")
    }

//
    private val map by lazy { mapView.map }

     fun renderMap() {
        map.apply {
            myLocationStyle = MyLocationStyle().apply {
                // 定位一次，且将视角移动到地图中心点。
                myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
//                myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.poi2))
            }
            moveCamera(CameraUpdateFactory.zoomTo(14f))
            uiSettings.isMyLocationButtonEnabled = true
            // 开始定位
            isMyLocationEnabled = true
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

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

    fun search(key:String) {


        val busStationSearch = BusLineSearch(this, BusLineQuery(key, BusLineQuery.SearchType.BY_LINE_NAME, "021"))
        busStationSearch.setOnBusLineSearchListener { result ,code ->



        }

        busStationSearch.searchBusLineAsyn()
    }


}
