package com.unicorn.bcykt

import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.fra_bus_station.*

/**
 * Created by thinkpad on 2017/11/8.
 */

abstract class MapFra : Fragment() {


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
