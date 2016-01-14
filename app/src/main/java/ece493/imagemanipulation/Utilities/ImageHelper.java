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

    private class MeanConvolutionFilter extends ConvolutionFilter{

        int R=0, G=0, B=0;

        @Override
        public Integer convolute(int[] mask, int numPixels) {
            R=0; G=0; B=0;
            for (int j=0; j < numPixels; j++){
                R += getRedChannel(mask[j]);
                G += getGreenChannel(mask[j]);
                B += getBlueChannel(mask[j]);
            }

            //Intentionally using integer division dropping the decimal
            R = R/numPixels;
            G = G/numPixels;
            B = B/numPixels;

            return packChannels(R, G, B);

        }
    }


    private class MedianConvolutionFiler extends  ConvolutionFilter{

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
                RChannels[i] = getRedChannel(mask[i]);
                GChannels[i] = getGreenChannel(mask[i]);
                BChannels[i] = getBlueChannel(mask[i]);
            }

            R = computeMedian(RChannels, numPixels);
            G = computeMedian(GChannels, numPixels);
            B = computeMedian(BChannels, numPixels);

            return packChannels(R, G, B);
        }
        
        private int computeMedian(int[] unsortedList, int numElements){
            Arrays.sort(unsortedList, 0, numElements);
            int median;

            if (numElements % 2 == 0){    //Even
                median = (unsortedList[numElements/2] + unsortedList[numElements/2 - 1])/2;
            } else {    //Odd
                median = unsortedList[numElements/2];
            }

            return median;
        }
    }

}
