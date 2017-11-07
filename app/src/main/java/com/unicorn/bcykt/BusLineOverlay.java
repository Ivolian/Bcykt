package com.unicorn.bcykt;

import android.graphics.Color;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.busline.BusStationItem;
import com.amap.api.services.core.LatLonPoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BusLineOverlay {
    private BusLineItem busLineItem;
    private AMap map;
    private ArrayList<Marker> markers = new ArrayList();
    private Polyline polyline;
    private List<BusStationItem> busStationItemList;
    private BitmapDescriptor startBitmap;
    private BitmapDescriptor endBitmap;
    private BitmapDescriptor busBitmap;


    public BusLineOverlay(AMap map, BusLineItem busLineItem) {
        this.busLineItem = busLineItem;
        this.map = map;
        this.busStationItemList = this.busLineItem.getBusStations();
    }

    public void addToMap() {
        List<LatLonPoint> latLonPoints = this.busLineItem.getDirectionsCoordinates();
        List<LatLng> latLngs = new ArrayList<>();
        for (LatLonPoint latLonPoint:latLonPoints){
            latLngs.add(new LatLng(latLonPoint.getLatitude(),latLonPoint.getLongitude()));
        }

//        ArrayList var2 = busLineItem.a(latLonPoints);
        this.polyline = this.map.addPolyline((new PolylineOptions()).addAll(latLngs).color(this.getBusColor()).width(this.getBuslineWidth()));
        if (this.busStationItemList.size() >= 1) {
            Marker var4;
            for (int i = 1; i < this.busStationItemList.size() - 1; ++i) {
                var4 = this.map.addMarker(this.recycle(i));
                this.markers.add(var4);
            }

            Marker var5 = this.map.addMarker(this.recycle(0));
            this.markers.add(var5);
            var4 = this.map.addMarker(this.recycle(this.busStationItemList.size() - 1));
            this.markers.add(var4);
        }
    }

    public void removeFromMap() {
        if (this.polyline != null) {
            this.polyline.remove();
        }

        Iterator var1 = this.markers.iterator();

        while (var1.hasNext()) {
            Marker var2 = (Marker) var1.next();
            var2.remove();
        }

        this.recycle();
    }

    private void recycle() {
        if (this.startBitmap != null) {
            this.startBitmap.recycle();
            this.startBitmap = null;
        }

        if (this.endBitmap != null) {
            this.endBitmap.recycle();
            this.endBitmap = null;
        }

        if (this.busBitmap != null) {
            this.busBitmap.recycle();
            this.busBitmap = null;
        }

    }

    public void zoomToSpan() {
        if (this.map != null) {
            List var1 = this.busLineItem.getDirectionsCoordinates();
            if (var1 != null && var1.size() > 0) {
                LatLngBounds var2 = this.recycle(var1);
                this.map.moveCamera(CameraUpdateFactory.newLatLngBounds(var2, 5));
            }

        }
    }

    private LatLngBounds recycle(List<LatLonPoint> var1) {
        LatLngBounds.Builder var2 = LatLngBounds.builder();

        for (int var3 = 0; var3 < var1.size(); ++var3) {
            var2.include(new LatLng(((LatLonPoint) var1.get(var3)).getLatitude(), ((LatLonPoint) var1.get(var3)).getLongitude()));
        }

        return var2.build();
    }

    private MarkerOptions recycle(int var1) {
        MarkerOptions var2 = (new MarkerOptions()).position(new LatLng(((BusStationItem) this.busStationItemList.get(var1)).getLatLonPoint().getLatitude(), ((BusStationItem) this.busStationItemList.get(var1)).getLatLonPoint().getLongitude())).title(this.getTitle(var1)).snippet(this.getSnippet(var1));
        if (var1 == 0) {
            var2.icon(this.getStartBitmapDescriptor());
        } else if (var1 == this.busStationItemList.size() - 1) {
            var2.icon(this.getEndBitmapDescriptor());
        } else {
            var2.anchor(0.5F, 0.5F);
            var2.icon(this.getBusBitmapDescriptor());
        }

        return var2;
    }

    protected BitmapDescriptor getStartBitmapDescriptor() {
        this.startBitmap =
                BitmapDescriptorFactory.fromResource(R.mipmap.ic_carpool_bus);
        return this.startBitmap;
    }

    protected BitmapDescriptor getEndBitmapDescriptor() {
        this.endBitmap
                = BitmapDescriptorFactory.fromResource(R.mipmap.ic_carpool_bus);

        return this.endBitmap;
    }

    protected BitmapDescriptor getBusBitmapDescriptor() {
        this.busBitmap =
                BitmapDescriptorFactory.fromResource(R.mipmap.ic_carpool_bus);
        return this.busBitmap;
    }

    protected String getTitle(int pos) {
        return busStationItemList.get(pos).getBusStationName();
    }

    protected String getSnippet(int var1) {
        return "";
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

    protected int getBusColor() {
        return Color.parseColor("#537edc");
    }

    protected float getBuslineWidth() {
        return 18.0F;
    }
}

