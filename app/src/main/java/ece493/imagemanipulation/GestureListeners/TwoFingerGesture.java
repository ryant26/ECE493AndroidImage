package ece493.imagemanipulation.GestureListeners;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by ryan on 04/02/16.
 */
public abstract class TwoFingerGesture extends CustomListener {
    protected int x1d, y1d, x2d, y2d, x1u, y1u, x2u, y2u;

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
        } else {
            resetTouchPositions();
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

    protected boolean twoTouches(){
        if (x1d > 0 && x2d > 0 && x1u > 0 && x2u > 0
                && y1d > 0 && y2d > 0 && y1u > 0 && y2u > 0){
            return true;
        }
        return false;
    }

    protected void resetTouchPositions(){
        x1d = x2d = x1u = x2u = y1d = y2d = y1u = y2u = 0;
    }

    protected abstract void checkForGestureExecution();
}
