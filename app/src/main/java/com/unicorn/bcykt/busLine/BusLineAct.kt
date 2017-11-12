package com.unicorn.bcykt.busLine

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.maps.utils.overlay.SmoothMoveMarker
import com.amap.api.services.busline.BusLineItem
import com.amap.api.services.busline.BusStationItem
import com.amap.api.services.route.*
import com.unicorn.bcykt.gaode.AMapServicesUtil
import com.unicorn.bcykt.gaode.BusLineOverlay
import com.unicorn.bcykt.R
import kotlinx.android.synthetic.main.act_bus_line.*


class BusLineAct : AppCompatActivity() {

    lateinit var busLineItem:BusLineItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_bus_line)
        mapView.onCreate(savedInstanceState)
        renderMap()
    initRecyclerView()
         busLineItem = intent.getParcelableExtra("keg")
        BusLineOverlay(map, busLineItem).addToMap()
    busStationAdapter.setNewData(busLineItem.busStations)
    }

    private val busStationAdapter = BusStationAdapter()

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            busStationAdapter.bindToRecyclerView(this)
        }

        busStationAdapter.setOnItemClickListener { _, _, position ->
            if (position == busStationAdapter.data.size-1){
                busStaion = busStationAdapter.getItem(position-1)!!
                busStaion2 = busStationAdapter.getItem(position)!!
            }else {
                busStaion = busStationAdapter.getItem(position)!!
                busStaion2 = busStationAdapter.getItem(position + 1)!!
            }
            val b = LatLngBounds.builder()

            b.include(AMapServicesUtil.convertToLatLng(busStaion.latLonPoint))
            b.include(AMapServicesUtil.convertToLatLng(busStaion2.latLonPoint))

            val bounds = b.build()
            mapView.map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150))

        }
    }

    lateinit var busStaion:BusStationItem
    lateinit var busStaion2:BusStationItem

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

    private fun s() {
        val fromAndTo = RouteSearch.FromAndTo(busStaion.latLonPoint, busStaion2.latLonPoint)
        val query = RouteSearch.WalkRouteQuery(fromAndTo)
        RouteSearch(this).apply {
            setRouteSearchListener(object : RouteSearch.OnRouteSearchListener {
                override fun onDriveRouteSearched(result: DriveRouteResult?, p1: Int) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onBusRouteSearched(p0: BusRouteResult?, p1: Int) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onRideRouteSearched(p0: RideRouteResult?, p1: Int) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onWalkRouteSearched(result: WalkRouteResult, p1: Int) {

                    show(result)
                }
            })
        }.calculateWalkRouteAsyn(query)// 异步路径规划步行模式查询


    }


    private fun  show(result:WalkRouteResult){
        val points = ArrayList<LatLng>()
        for (step in result.paths[0].steps) step.polyline.mapTo(points) { AMapServicesUtil.convertToLatLng(it) }
        val smoothMarker = SmoothMoveMarker(mapView.map)
        // 设置滑动的图标
        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.mipmap.map_bus_ic))
        smoothMarker.setPoints(points)//设置平滑移动的轨迹list
        smoothMarker.setTotalDuration(10)//设置平滑移动的总时间
        smoothMarker.startSmoothMove()
    }

    override fun onBackPressed() {
s()
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

}
