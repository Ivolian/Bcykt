package com.unicorn.bcykt.bus


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.maps.utils.overlay.SmoothMoveMarker
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.amap.api.services.route.*
import com.amap.api.services.route.RouteSearch.WalkRouteQuery
import com.unicorn.bcykt.R
import com.unicorn.bcykt.app.Constant
import com.unicorn.bcykt.app.Constant.busCode
import com.unicorn.bcykt.app.Constant.cityCode
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.fra_nearby_station.*
import me.yokeyword.fragmentation.SupportFragment


class NearbyStationFra : SupportFragment() {

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
//                myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.poi2))
            }
            uiSettings.isMyLocationButtonEnabled = true
            // 开始定位
            isMyLocationEnabled = true
//            var mCameraUpdate = CameraUpdateFactory.zoomTo(17f)
//            moveCamera(mCameraUpdate)
            setOnMyLocationChangeListener { location ->

                Constant.latLonPoint = LatLonPoint(location.latitude, location.longitude)
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
            val poiItem = busStationAdapter.getItem(pos)
            s(poiItem!!)
        }
    }

    private fun searchNearbyStation() {
        PoiSearch(context, PoiSearch.Query("", busCode, cityCode).apply { pageSize = 10 })
                .apply {
                    //设置周边搜索的中心点以及半径

                    bound = PoiSearch.SearchBound(Constant.latLonPoint, Constant.radius)
                    setOnPoiSearchListener(object : PoiSearch.OnPoiSearchListener {
                        override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
                            // do nothing
                        }

                        override fun onPoiSearched(result: PoiResult, p1: Int) {
//                            BusStationOverlay(mapView.map, result.pois).addToMap()
                            busStationAdapter.setNewData(result.pois)
                        }
                    })
                    searchPOIAsyn()
                }
    }

    var walkRouteOverlay: WalkRouteOverlay? = null

    private fun s(poiItem: PoiItem) {
        val fromAndTo = RouteSearch.FromAndTo(Constant.latLonPoint, poiItem.latLonPoint)
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
this@NearbyStationFra.result = result
                    val b = LatLngBounds.builder()

                        b.include(AMapServicesUtil.convertToLatLng(Constant.latLonPoint))
                    b.include(AMapServicesUtil.convertToLatLng(poiItem.latLonPoint))

                    val bounds = b.build()
                    mapView.map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))

                    walkRouteOverlay?.removeFromMap()
                    walkRouteOverlay = WalkRouteOverlay(context, mapView.map, result.paths[0], Constant.latLonPoint, poiItem.latLonPoint).addToMap()

                }
            })
        }.calculateWalkRouteAsyn(query)// 异步路径规划步行模式查询


    }

    var result:WalkRouteResult? = null

    override fun onBackPressedSupport(): Boolean {

        val points  = ArrayList<LatLng>()
        if (result == null){
            return true
        }
        for (step in result!!.paths[0].steps){
            for (lng in step.polyline){
                points.add(AMapServicesUtil.convertToLatLng(lng))
            }
        }
        val smoothMarker = SmoothMoveMarker(mapView.map)
        // 设置滑动的图标
        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.mipmap.map_bus_ic))

        /*
        //当移动Marker的当前位置不在轨迹起点，先从当前位置移动到轨迹上，再开始平滑移动
        // LatLng drivePoint = points.get(0);//设置小车当前位置，可以是任意点，这里直接设置为轨迹起点
        LatLng drivePoint = new LatLng(39.980521,116.351905);//设置小车当前位置，可以是任意点
        Pair<Integer, LatLng> pair = PointsUtil.calShortestDistancePoint(points, drivePoint);
        points.set(pair.first, drivePoint);
        List<LatLng> subList = points.subList(pair.first, points.size());
        // 设置滑动的轨迹左边点
        smoothMarker.setPoints(subList);*/

        smoothMarker.setPoints(points)//设置平滑移动的轨迹list
        smoothMarker.setTotalDuration(10)//设置平滑移动的总时间
        smoothMarker.startSmoothMove()
        return true
    }

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
