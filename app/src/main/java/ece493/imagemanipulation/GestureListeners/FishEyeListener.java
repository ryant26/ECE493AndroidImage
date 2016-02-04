package ece493.imagemanipulation.GestureListeners;

import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by ryan on 03/02/16.
 */
public class FishEyeListener  extends CustomListener{
    private int x1d, y1d, x2d, y2d, x1u, y1u, x2u, y2u;


    @Override
    public void actionDown(MotionEvent e) {
        x1d = getTouchX(e);
        y1d = getTouchY(e);
    }

    @Override
    public void actionPointerDown(MotionEvent e) {
        if (e.getPointerCount() == 2){
            x2d = getTouchX(e);
            y2d = getTouchY(e);
        }

    }

    @Override
    public void actionPointerUp(MotionEvent e) {
        x1u = getTouchX(e);
        y1u = getTouchY(e);
    }

    @Override
    public void actionUp(MotionEvent e) {
        x2u = getTouchX(e);
        y2u = getTouchY(e);
        checkForGestureExecution();
    }

    @Override
    protected void checkForGestureExecution() {
        int xThreshold = Math.abs(x1d - x2d);
        int yThreshold = 200;

        int xDifferenceDown =Math.abs(x1d - x2d);
        int xDifferenceUp =Math.abs(x1u - x2u);

        if (twoTouches()){
            if (xDifferenceDown - (xThreshold * 1.5) < xDifferenceUp
                    && xDifferenceDown + xThreshold > xDifferenceUp){
                if(y1u > y1d + yThreshold && y2u > y2d + yThreshold){
                    // The gesture is detected
                    Log.d("GESTURE", "Fish Eye gesture detected!!");
                }
            }
        }
        resetTouchPositions();
    }

    private boolean twoTouches(){
        if (x1d > 0 && x2d > 0 && x1u > 0 && x2u > 0
                && y1d > 0 && y2d > 0 && y1u > 0 && y2u > 0){
            return true;
        }
        return false;
    }

    private void resetTouchPositions(){
        x1d = x2d = x1u = x2u = y1d = y2d = y1u = y2u = 0;
    }


}
