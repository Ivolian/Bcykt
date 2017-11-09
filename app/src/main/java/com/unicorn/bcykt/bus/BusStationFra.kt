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
import com.unicorn.bcykt.app.Constant.cityCode
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.fra_bus_station.*
import me.yokeyword.fragmentation.SupportFragment






class BusStationFra : SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fra_bus_station, container, false)
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
                Constant.latitude = location.latitude
                Constant.longitude = location.longitude

                var extras = location.extras
                val street = extras.getString("PoiName")
//                val streetNum = extras.getString("StreetNum")
                searchStation("虹口区长山路11号" )
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

       val poiSearch = PoiSearch(context, PoiSearch.Query("", "150700", cityCode))
        poiSearch.setOnPoiSearchListener(object :PoiSearch.OnPoiSearchListener{
            override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
                ""
            }

            override fun onPoiSearched(result: PoiResult, p1: Int) {
                        BusStationOverlay(mapView.map, result.pois).addToMap()
                    renderBusStations(result.pois)
                ""
            }
        })
        poiSearch.bound = PoiSearch.SearchBound(LatLonPoint(Constant.latitude,Constant.longitude), 1000)//设置周边搜索的中心点以及半径
        poiSearch.searchPOIAsyn();


    }

    private fun renderBusStations(stations: List<PoiItem>) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        var adapter = BusStationAdapter().apply {
            bindToRecyclerView(recyclerView)
            setNewData(stations)

        }
        recyclerView.addItemDecoration(
                HorizontalDividerItemDecoration.Builder(context)
                        .colorResId(R.color.material_grey_300)
                        .size(1)
                        .build())

        adapter.setOnItemClickListener  { _, _, pos ->
            val poiItem =  adapter.getItem(pos)
            val latLngPoint = poiItem!!.latLonPoint
            val mCameraUpdate = CameraUpdateFactory.changeLatLng(LatLng(latLngPoint.latitude, latLngPoint.longitude))

            mapView.map.moveCamera(mCameraUpdate)
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
