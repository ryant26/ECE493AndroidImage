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
            int R=0, G=0, B=0;
            for (int j=0; j < numPixels; j++){
                R += (mask[j] >> 16) & 0xff;
                G += (mask[j] >> 8) & 0xff;
                B += mask[j] & 0xff;
            }
            R = Math.round((float)R/numPixels);
            G = Math.round((float)G/numPixels);
            B = Math.round((float)B/numPixels);

            return 0xff000000 | (R << 16) | (G << 8) | B;

        }
    }


    private class MedianConvolutionFiler implements  ConvolutionFilter{

        @Override
        public Integer convolute(int[] mask, int numPixels) {
            int [] RChannels = new int[numPixels];
            int [] GChannels = new int[numPixels];
            int [] BChannels = new int[numPixels];

            for (int i=0; i < numPixels; i++){
                RChannels[i] = (mask[i] >> 16) & 0xff;
                GChannels[i] = (mask[i] >> 8) & 0xff;
                BChannels[i] = mask[i] & 0xff;
            }

            int R = Math.round(computeMedian(RChannels));
            int G = Math.round(computeMedian(GChannels));
            int B = Math.round(computeMedian(BChannels));

            return 0xff000000 | (R << 16) | (G << 8) | B;
        }
        
        private float computeMedian(int[] unsortedList){
            int numElements = unsortedList.length;
            Arrays.sort(unsortedList, 0, numElements);
            float median = 0;

            if (numElements % 2 == 0){    //Even
                median = ((float)unsortedList[numElements/2] + unsortedList[numElements/2 - 1])/2;
            } else {    //Odd
                median = (float)unsortedList[numElements/2];
            }

            return Math.round(median);
        }
    }

}
