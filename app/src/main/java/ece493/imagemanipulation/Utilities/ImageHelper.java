package ece493.imagemanipulation.Utilities;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import ece493.imagemanipulation.AppManager;

/**
 * Created by ryan on 06/01/16.
 */
public class ImageHelper {

    private AppManager manager;

    public ImageHelper(AppManager manager){
        this.manager = manager;
    }

    public static int getMaxFilterSize(Bitmap image){
        return image.getHeight() > image.getWidth() ? image.getWidth() : image.getHeight();
    }

    public void meanFilter(Bitmap original, int filterSize){
        filter(original, new MeanConvolutionFilter(), filterSize);
    }

    public void medianFilter(Bitmap original, int filterSize){
        filter(original, new MedianConvolutionFiler(), filterSize);
    }

    private void filter(Bitmap original, final ConvolutionFilter filter, final int filterSize){
        manager.setFilterTask(new FilterTask(filterSize, filter, manager));
    }

    private class MeanConvolutionFilter implements ConvolutionFilter{

        @Override
        public Integer convolute(int[] mask, int numPixels) {
            int sum = 0;
            for (int j=0; j < numPixels; j++){
                sum += mask[j];
            }
            return Math.round((float)sum/numPixels);
        }
    }


    private class MedianConvolutionFiler implements  ConvolutionFilter{

        @Override
        public Integer convolute(int[] mask, int numPixels) {
            Arrays.sort(mask, 0, numPixels);
            float median = 0;

            if (numPixels % 2 == 0){    //Even
                median = ((float)mask[numPixels/2] / mask[numPixels/2 - 1])/2;
            } else {    //Odd
                median = (float)mask[numPixels/2];
            }

            return Math.round(median);
        }
    }

}
