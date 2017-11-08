package com.unicorn.bcykt.bus;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.busline.BusStationItem;
import com.unicorn.bcykt.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BusStationOverlay {

    private AMap map;
    private ArrayList<Marker> markers = new ArrayList();
    private List<BusStationItem> busStationItemList;
    private BitmapDescriptor busBitmap;


    public BusStationOverlay(AMap map, List<BusStationItem> busStationItemList) {
        this.map = map;
        this.busStationItemList = busStationItemList;
    }

    public void addToMap() {


        if (this.busStationItemList.size() >= 1) {
            Marker marker;
            for (int i = 0; i < this.busStationItemList.size() - 1; ++i) {
                marker = this.map.addMarker(this.recycle(i));
                this.markers.add(marker);
            }

//            Marker var5 = this.map.addMarker(this.recycle(0));
//            this.markers.add(var5);
//            marker = this.map.addMarker(this.recycle(this.busStationItemList.size() - 1));
//            this.markers.add(marker);
        }
    }

    public void removeFromMap() {


        Iterator var1 = this.markers.iterator();
        while (var1.hasNext()) {
            Marker var2 = (Marker) var1.next();
            var2.remove();
        }
        this.recycle();
    }

    private void recycle() {

        if (this.busBitmap != null) {
            this.busBitmap.recycle();
            this.busBitmap = null;
        }

    }


    private MarkerOptions recycle(int index) {
        MarkerOptions markerOptions = (new MarkerOptions()).position(new LatLng((this.busStationItemList.get(index)).getLatLonPoint().getLatitude(), (this.busStationItemList.get(index)).getLatLonPoint().getLongitude()));
        markerOptions.icon(this.getBusBitmapDescriptor());
        return markerOptions;
    }


    protected BitmapDescriptor getBusBitmapDescriptor() {
        this.busBitmap =
                BitmapDescriptorFactory.fromResource(R.mipmap.ic_carpool_bus);
        return this.busBitmap;
    }


    public int getBusStationIndex(Marker var1) {
        for (int var2 = 0; var2 < this.markers.size(); ++var2) {
            if (((Marker) this.markers.get(var2)).equals(var1)) {
                return var2;
            }
        }

        return -1;
    }

    public BusStationItem getBusStationItem(int pos) {
        return pos >= 0 && pos < this.busStationItemList.size() ? (BusStationItem) this.busStationItemList.get(pos) : null;
    }


}

