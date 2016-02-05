package ece493.imagemanipulation.GestureListeners;

import android.util.Log;

/**
 * Created by ryan on 03/02/16.
 */
public class WarpListener extends TwoFingerGesture{
    private static final int Y_THRESHOLD = 50;

    @Override
    protected void checkForGestureExecution() {
        int xDifferenceDown =Math.abs(x1d - x2d);
        int xDifferenceUp =Math.abs(x1u - x2u);

        int yDifferenceDown = Math.abs(y1d - y2d);
        int yDifferenceUp = Math.abs(y1u - y2u);

        if (xDifferenceUp > 2 * xDifferenceDown){
            if (yDifferenceDown < Y_THRESHOLD && yDifferenceUp < Y_THRESHOLD){
                //Gestgure rcognized!
                Log.d("GESTURE", "Warp Gesture detected!!");
            }
        }
    }
}
