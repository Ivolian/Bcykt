package com.unicorn.bcykt.other;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


public class NoTouchPager extends android.support.v4.view.ViewPager {

    public NoTouchPager(Context context) {
        super(context);
    }

    public NoTouchPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public boolean onTouchEvent(MotionEvent arg0) {
//        return false;
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent arg0) {
//        return false;
//    }
//

    @Override
    public boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (Math.abs(dx) > 50) {
            return super.canScroll(v, checkV, dx, x, y);
        } else {
            return true;
        }
    }

}
