package ece493.imagemanipulation.Utilities;

import java.util.List;

/**
 * Created by ryan on 06/01/16.
 */
public abstract class ConvolutionFilter {
    abstract Integer convolute(int[] mask, int numPixels);

    protected int getRedChannel(int pixel){
        return shiftAndMask(pixel, 16);
    }

    protected int getGreenChannel(int pixel){
        return shiftAndMask(pixel, 8);
    }

    protected int getBlueChannel(int pixel){
        return shiftAndMask(pixel, 0);
    }

    protected int packChannels(int R, int G, int B){
        return 0xff000000 | (R << 16) | (G << 8) | B;
    }

    private int shiftAndMask(int pixel, int bits){
        return (pixel >> bits) & 0xff;
    }
}
