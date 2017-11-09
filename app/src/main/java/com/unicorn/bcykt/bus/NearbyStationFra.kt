package com.unicorn.bcykt.bus


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
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
            moveCamera(CameraUpdateFactory.zoomTo(16f))
            uiSettings.isMyLocationButtonEnabled = true
            // 开始定位
            isMyLocationEnabled = true
            setOnMyLocationChangeListener { location ->
                Constant.latLonPoint = LatLonPoint(location.latitude, location.longitude)
                searchNearbyStation()
            }
        }
    }

    private val busStationAdapter = BusStationAdapter()

    private fun initRecyclerView(){
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
            val latLngPoint = poiItem!!.latLonPoint
            val cameraUpdate = CameraUpdateFactory.changeLatLng(LatLng(latLngPoint.latitude, latLngPoint.longitude))
            mapView.map.moveCamera(cameraUpdate)
        }
    }

    private fun searchNearbyStation() {
        PoiSearch(context, PoiSearch.Query("", busCode, cityCode))
                .apply {
                    //设置周边搜索的中心点以及半径
                    bound = PoiSearch.SearchBound(Constant.latLonPoint, Constant.radius)
                    setOnPoiSearchListener(object : PoiSearch.OnPoiSearchListener {
                        override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
                            // do nothing
                        }

                        override fun onPoiSearched(result: PoiResult, p1: Int) {
                            BusStationOverlay(mapView.map, result.pois).addToMap()
                           busStationAdapter.setNewData(result.pois)
                        }
                    })
                    searchPOIAsyn()
                }
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
