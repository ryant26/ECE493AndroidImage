package ece493.imagemanipulation.GestureListeners;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ryan on 03/02/16.
 */
public abstract class CustomListener implements View.OnTouchListener{

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

    public abstract void actionDown(MotionEvent e);
    public abstract void actionPointerDown(MotionEvent e);
    public abstract void actionPointerUp(MotionEvent e);
    public abstract void actionUp(MotionEvent e);
}
