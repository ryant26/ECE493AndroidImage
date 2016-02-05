package ece493.imagemanipulation.GestureListeners;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 04/02/16.
 */
public class GestureAggregator implements View.OnTouchListener {
    private List<View.OnTouchListener> listeners = new ArrayList<>();

    public void addListener(View.OnTouchListener listener) {
        listeners.add(listener);
    }

    public void removeListener(View.OnTouchListener listener) {
        listeners.remove(listener);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        for (View.OnTouchListener listener: listeners) {
            listener.onTouch(v,event);
        }
        return true;
    }
}