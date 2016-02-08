package ece493.imagemanipulation.GestureListeners;

import android.util.Log;

import ece493.imagemanipulation.NonlinearTransforms.RenderScriptContext;

/**
 * Created by ryan on 03/02/16.
 */
public class SwirlListener extends TwoFingerGesture{
    private static final int X_THRESHOLD = 100;
    private static final int Y_THRESHOLD = 100;

    public SwirlListener(GestureInvokedListener listener) {
        super(listener);
    }


    @Override
    protected boolean gestureIsInvoked() {
        int xDifferenceDown =Math.abs(x1d - x2d);
        int xDifferenceUp =Math.abs(x1u - x2u);

        int yDifferenceDown = Math.abs(y1d - y2d);
        int yDifferenceUp = Math.abs(y1u - y2u);
        boolean out = false;

        if (yDifferenceUp > Y_THRESHOLD){
            if (Math.abs(xDifferenceDown - xDifferenceUp) < X_THRESHOLD){
                //Gestgure rcognized!
                Log.d("GESTURE", "Swirl Gesture detected!!");
                out = true;
            }
        }
        resetTouchPositions();
        return out;
    }
}
