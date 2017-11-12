package com.unicorn.bcykt.busStation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.ColorUtils
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.amap.api.services.route.*
import com.amap.api.services.route.RouteSearch.WalkRouteQuery
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.unicorn.bcykt.gaode.AMapServicesUtil
import com.unicorn.bcykt.R
import com.unicorn.bcykt.app.SharedData
import com.unicorn.bcykt.app.SharedData.busCode
import com.unicorn.bcykt.app.SharedData.cityCode
import com.unicorn.bcykt.busStation.adapter.BusStationAdapter
import com.unicorn.bcykt.busStation.entity.BusStation
import com.unicorn.bcykt.busStation.entity.BusStationLine
import com.unicorn.bcykt.busStation.overlay.WalkRouteOverlay
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.fra_nearby_station.*

class NearbyFra : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fra_nearby_station, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        initRecyclerView()
        initMap()
    }

    private fun initMap() {
        mapView.map.apply {
            myLocationStyle = MyLocationStyle().apply {
                // 定位一次，且将视角移动到地图中心点。
                myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
                myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.map_site_light_ic))
                val color =               ContextCompat.getColor(context,R.color.colorPrimary)

                strokeColor(color)
                radiusFillColor(ColorUtils.setAlphaComponent(color,40))
            }
            uiSettings.isZoomControlsEnabled = false
            uiSettings.isMyLocationButtonEnabled = true
            // 开始定位
            isMyLocationEnabled = true
            setOnMyLocationChangeListener { location ->
                SharedData.latLonPoint = LatLonPoint(location.latitude, location.longitude)
                searchNearbyStation()
            }
        }
    }

    private val busStationAdapter = BusStationAdapter()

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            busStationAdapter.bindToRecyclerView(this)
            addItemDecoration(HorizontalDividerItemDecoration.Builder(context)
                    .colorResId(R.color.material_grey_400)
                    .size(1)
                    .build())
        }
        busStationAdapter.setOnItemClickListener { _, _, pos ->
            val item = busStationAdapter.getItem(pos)
            (item as? BusStation)?.let { s(it.poiItem) }
        }
    }

    private fun searchNearbyStation() {
        PoiSearch(context, PoiSearch.Query("", busCode, cityCode).apply { pageSize = 10 })
                .apply {
                    //设置周边搜索的中心点以及半径
                    bound = PoiSearch.SearchBound(SharedData.latLonPoint, SharedData.radius)
                    setOnPoiSearchListener(object : PoiSearch.OnPoiSearchListener {
                        override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
                            // do nothing
                        }

                        override fun onPoiSearched(result: PoiResult, p1: Int) {
                            val pois = result.pois
                            val multi = ArrayList<MultiItemEntity>()
                            for (poi in pois) {
                                val bus = BusStation(poi)
                                var i = 1
                                poi.snippet.split(";").forEach { lineName ->
                                    bus.addSubItem(BusStationLine(lineName, "${i++}分钟"))

                                }
                                multi.add(bus)
                            }
                            busStationAdapter.setNewData(multi)
                        }
                    })
                    searchPOIAsyn()
                }
    }

    var walkRouteOverlay: WalkRouteOverlay? = null

    private fun s(poiItem: PoiItem) {
        val fromAndTo = RouteSearch.FromAndTo(SharedData.latLonPoint, poiItem.latLonPoint)
        val query = WalkRouteQuery(fromAndTo)



        RouteSearch(context).apply {
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
                    this@NearbyFra.result = result
                    val b = LatLngBounds.builder()

                    b.include(AMapServicesUtil.convertToLatLng(SharedData.latLonPoint))
                    b.include(AMapServicesUtil.convertToLatLng(poiItem.latLonPoint))

                    val bounds = b.build()
                    mapView.map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150))

                    walkRouteOverlay?.removeFromMap()
                    walkRouteOverlay = WalkRouteOverlay(context, mapView.map, result.paths[0], SharedData.latLonPoint, poiItem.latLonPoint).addToMap()

                }
            })
        }.calculateWalkRouteAsyn(query)// 异步路径规划步行模式查询


    }

    var result: WalkRouteResult? = null



// ===================== map =====================

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

}
