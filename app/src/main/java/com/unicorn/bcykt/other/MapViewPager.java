package com.unicorn.bcykt.other;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


public class MapViewPager extends android.support.v4.view.ViewPager {

    public MapViewPager(Context context) {
        super(context);
    }

    public MapViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        return Math.abs(dx) <= 100 || super.canScroll(v, checkV, dx, x, y);
    }

}
