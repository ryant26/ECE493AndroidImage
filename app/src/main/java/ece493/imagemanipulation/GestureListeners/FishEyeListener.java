package ece493.imagemanipulation.GestureListeners;

import android.util.Log;

import ece493.imagemanipulation.NonlinearTransforms.RenderScriptContext;

/**
 * Created by ryan on 03/02/16.
 */
public class FishEyeListener extends TwoFingerGesture {

    public FishEyeListener(GestureInvokedListener listener) {
        super(listener);
    }

    @Override
    protected boolean gestureIsInvoked() {
        int xThreshold = Math.abs(x1d - x2d);
        int yThreshold = 200;

        int xDifferenceDown =Math.abs(x1d - x2d);
        int xDifferenceUp =Math.abs(x1u - x2u);

        boolean out = false;

        if (xDifferenceDown - (xThreshold * 1.5) < xDifferenceUp
                && xDifferenceDown + xThreshold > xDifferenceUp){
            if(y1u > y1d + yThreshold && y2u > y2d + yThreshold){
                // The gesture is detected
                Log.d("GESTURE", "Fish Eye gesture detected!!");
                out = true;
            }
        }
        resetTouchPositions();
        return out;
    }
}
