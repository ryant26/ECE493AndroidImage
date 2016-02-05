package ece493.imagemanipulation.GestureListeners;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 04/02/16.
 */
public class GestureAggregator extends CustomListener {
    private List<CustomListener> listeners = new ArrayList<>();

    public void addListener(CustomListener listener) {
        listeners.add(listener);
    }

    public void removeListener(CustomListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void actionDown(final MotionEvent e) {
        for(CustomListener listener : listeners){
            listener.actionDown(e);
        }
    }

    @Override
    public void actionPointerDown(MotionEvent e) {
        for(CustomListener listener : listeners){
            listener.actionPointerDown(e);
        }
    }

    @Override
    public void actionPointerUp(MotionEvent e) {
        for(CustomListener listener : listeners){
            listener.actionPointerUp(e);
        }
    }

    @Override
    public void actionUp(MotionEvent e) {
        for(CustomListener listener : listeners){
            listener.actionUp(e);
        }
    }

}