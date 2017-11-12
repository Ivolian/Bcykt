package com.unicorn.bcykt.other

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.util.AttributeSet
import com.amap.api.maps.TextureMapView

internal class MyMapView : TextureMapView, LifecycleObserver {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, i: Int) : super(context, attributeSet, i)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        onCreate(null)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        onPause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        onDestroy()
    }

}