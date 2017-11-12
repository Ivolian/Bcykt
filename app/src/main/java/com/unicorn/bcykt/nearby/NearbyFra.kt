package com.unicorn.bcykt.nearby

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
import com.unicorn.bcykt.R
import com.unicorn.bcykt.app.SharedData
import com.unicorn.bcykt.app.SharedData.busCode
import com.unicorn.bcykt.app.SharedData.cityCode
import com.unicorn.bcykt.gaode.AMapServicesUtil
import com.unicorn.bcykt.gaode.overlay.WalkRouteOverlay
import com.unicorn.bcykt.nearby.adapter.NearbyAdapter
import com.unicorn.bcykt.nearby.entity.NearbyLine
import com.unicorn.bcykt.nearby.entity.NearbyStation
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.fra_nearby.*

class NearbyFra : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fra_nearby, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMapView()
        initRecyclerView()
    }

    private fun initMapView() {
        lifecycle.addObserver(mapView)
        mapView.map.apply {
            myLocationStyle = MyLocationStyle().apply {
                // 定位一次，且将视角移动到地图中心点。
                myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
                myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.map_site_light_ic))
                val colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary)
                strokeColor(colorPrimary)
                radiusFillColor(ColorUtils.setAlphaComponent(colorPrimary, 40))
            }
            uiSettings.isZoomControlsEnabled = false
            uiSettings.isMyLocationButtonEnabled = true
            // 开始定位
            isMyLocationEnabled = true
            setOnMyLocationChangeListener { location ->
                LatLonPoint(location.latitude, location.longitude).let {
                    SharedData.latLonPoint = it
                    searchNearbyStation(it)
                }
            }
        }
    }

    private fun searchNearbyStation(latLonPoint: LatLonPoint) {
        PoiSearch(context, PoiSearch.Query("", busCode, cityCode).apply { pageSize = 10 })
                .apply {
                    // 设置周边搜索的中心点以及半径
                    bound = PoiSearch.SearchBound(latLonPoint, SharedData.radius)
                    setOnPoiSearchListener(object : PoiSearch.OnPoiSearchListener {
                        override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
                            // do nothing
                        }

                        override fun onPoiSearched(result: PoiResult, p1: Int) {
                            ArrayList<MultiItemEntity>().apply {
                                result.pois.forEach{  poi ->
                                    NearbyStation(poi).apply {
                                        poi.snippet.split(";").forEachIndexed { index, lineName ->
                                            addSubItem(NearbyLine(lineName, "${index + 1}分钟"))
                                        }
                                    }.let { add(it) }
                                }
                            }.let { busStationAdapter.setNewData(it) }
                        }
                    })
                    searchPOIAsyn()
                }
    }

    private val busStationAdapter = NearbyAdapter()

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
            (busStationAdapter.getItem(pos) as? NearbyStation)?.let { searchWalkRoute(it.poiItem) }
        }
    }

    var walkRouteOverlay: WalkRouteOverlay? = null

    private fun searchWalkRoute(poiItem: PoiItem) {
        RouteSearch(context).apply {
            setRouteSearchListener(object : RouteSearch.OnRouteSearchListener {
                override fun onDriveRouteSearched(result: DriveRouteResult?, p1: Int) {
                }

                override fun onBusRouteSearched(p0: BusRouteResult?, p1: Int) {
                }

                override fun onRideRouteSearched(p0: RideRouteResult?, p1: Int) {
                }

                override fun onWalkRouteSearched(result: WalkRouteResult, p1: Int) {
                    LatLngBounds.builder().apply {
                        include(AMapServicesUtil.convertToLatLng(SharedData.latLonPoint))
                        include(AMapServicesUtil.convertToLatLng(poiItem.latLonPoint))
                    }.build().let { mapView.map.animateCamera(CameraUpdateFactory.newLatLngBounds(it, 150)) }

                    //
                    walkRouteOverlay?.removeFromMap()
                    walkRouteOverlay = WalkRouteOverlay(context, mapView.map, result.paths[0], SharedData.latLonPoint, poiItem.latLonPoint).addToMap()

                }
            })
        }.calculateWalkRouteAsyn(WalkRouteQuery(RouteSearch.FromAndTo(SharedData.latLonPoint, poiItem.latLonPoint)))
    }

}
