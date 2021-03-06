package ece493.imagemanipulation.GestureListeners;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import ece493.imagemanipulation.NonlinearTransforms.RenderScriptContext;

/**
 * Created by ryan on 03/02/16.
 */
public class BulgeListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener{

    @SuppressWarnings("deprecation")
    GestureDetector detector = new GestureDetector(this);
    GestureInvokedListener listener;

    public BulgeListener(GestureInvokedListener listener){
        this.listener = listener;
    }

    @Override
    public void onLongPress(MotionEvent e){
        // Buldgge gesture detected!
        Log.d("GESTURE", "Bulge gesture detected");
        listener.onGestureInvoked();
   }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }
}
