package ece493.imagemanipulation.GestureListeners;

import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ryan on 04/02/16.
 */
public abstract class TwoFingerGesture implements View.OnTouchListener {
    protected int x1d, y1d, x2d, y2d, x1u, y1u, x2u, y2u;
    private boolean twoTouches = false;

    @Override
    public final boolean onTouch(View v, MotionEvent e){
        int action = e.getActionMasked();

        switch (action){
            case(MotionEvent.ACTION_DOWN):
                actionDown(e);
                break;
            case(MotionEvent.ACTION_POINTER_DOWN):
                actionPointerDown(e);
                break;
            case(MotionEvent.ACTION_POINTER_UP):
                actionPointerUp(e);
                break;
            case(MotionEvent.ACTION_UP):
                actionUp(e);
                break;
        }

        return true;
    }

    protected int getTouchX(MotionEvent e){
        return (int) MotionEventCompat.getX(e, e.getActionIndex());
    }

    protected int getTouchY(MotionEvent e){
        return (int) MotionEventCompat.getY(e, e.getActionIndex());
    }

    public void actionDown(MotionEvent e) {
        x1d = getTouchX(e);
        y1d = getTouchY(e);
    }

    public void actionPointerDown(MotionEvent e) {
        if (e.getPointerCount() == 2){
            twoTouches = true;
            x2d = getTouchX(e);
            y2d = getTouchY(e);
        } else {
            resetTouchPositions();
        }

    }

    public void actionPointerUp(MotionEvent e) {
        x1u = getTouchX(e);
        y1u = getTouchY(e);
    }

    public void actionUp(MotionEvent e) {
        x2u = getTouchX(e);
        y2u = getTouchY(e);
        if (twoTouches()){
            checkForGestureExecution();
        } else {
            resetTouchPositions();
        }

    }

    protected boolean twoTouches(){
       return twoTouches;
    }

    protected void resetTouchPositions(){
        x1d = x2d = x1u = x2u = y1d = y2d = y1u = y2u = 0;
        twoTouches = false;
    }

    protected abstract void checkForGestureExecution();
}
