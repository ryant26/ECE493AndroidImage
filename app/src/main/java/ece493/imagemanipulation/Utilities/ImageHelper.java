package ece493.imagemanipulation.Utilities;

import android.graphics.Bitmap;
import java.util.Arrays;

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

        int R=0, G=0, B=0;

        @Override
        public Integer convolute(int[] mask, int numPixels) {
            R=0; G=0; B=0;
            for (int j=0; j < numPixels; j++){
                R += (mask[j] >> 16) & 0xff;
                G += (mask[j] >> 8) & 0xff;
                B += mask[j] & 0xff;
            }

            //Intentionally using integer division dropping the decimal
            R = R/numPixels;
            G = G/numPixels;
            B = B/numPixels;

            return 0xff000000 | (R << 16) | (G << 8) | B;

        }
    }


    private class MedianConvolutionFiler implements  ConvolutionFilter{

        int [] RChannels = new int[1];
        int [] GChannels = new int[1];
        int [] BChannels = new int[1];

        int R, G, B;

        @Override
        public Integer convolute(int[] mask, int numPixels) {

            if (numPixels > RChannels.length){
                RChannels = new int[numPixels];
                GChannels = new int[numPixels];
                BChannels = new int[numPixels];
            }


            for (int i=0; i < numPixels; i++){
                RChannels[i] = (mask[i] >> 16) & 0xff;
                GChannels[i] = (mask[i] >> 8) & 0xff;
                BChannels[i] = mask[i] & 0xff;
            }

            R = computeMedian(RChannels, numPixels);
            G = computeMedian(GChannels, numPixels);
            B = computeMedian(BChannels, numPixels);

            return 0xff000000 | (R << 16) | (G << 8) | B;
        }
        
        private int computeMedian(int[] unsortedList, int numElements){
            Arrays.sort(unsortedList, 0, numElements);
            int median = 0;

            if (numElements % 2 == 0){    //Even
                median = (unsortedList[numElements/2] + unsortedList[numElements/2 - 1])/2;
            } else {    //Odd
                median = unsortedList[numElements/2];
            }

            return median;
        }
    }

}
