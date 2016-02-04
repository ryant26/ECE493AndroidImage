package ece493.imagemanipulation.GestureListeners;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ryan on 03/02/16.
 */
public abstract class CustomListener implements View.OnTouchListener{
    private int x1d, y1d, x1u, y1u;

    @Override
    public final boolean onTouch(View v, MotionEvent e){
        int action = e.getActionMasked();

        switch (action){
            case(MotionEvent.ACTION_DOWN):
                actionDown((int)e.getX(), (int)e.getY());
                break;
            case(MotionEvent.ACTION_POINTER_DOWN):
                actionPointerDown((int) e.getX(), (int) e.getY());
                break;
            case(MotionEvent.ACTION_POINTER_UP):
                actionPointerUp((int) e.getX(), (int) e.getY());
                break;
        }

        return true;
    }

    public abstract void actionDown(int x, int y);
    public abstract void actionPointerDown(int x, int y);
    public abstract void actionPointerUp(int x, int y);
}
