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
        AsyncTask filterTask = new AsyncTask<Bitmap, Void, Bitmap>(){

            @Override
            protected Bitmap doInBackground(Bitmap... params) {
                Bitmap image = params[0];

                int [] pixels = new int[image.getWidth() * image.getHeight()];
                int [] framePixels = new int[filterSize * filterSize];
                int [] newImage = new int[pixels.length];

                image.getPixels(pixels, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
                int pixelCounter;

                for (int j=0; j < image.getHeight(); j++){
                    for (int i=0; i < image.getWidth(); i++){
                        pixelCounter = 0;
                        for (int frameJ=j-(filterSize/2); frameJ < j + (filterSize/2); frameJ++){
                            for (int frameI=i-(filterSize/2); frameI < i+(filterSize/2); frameI++){
                                int framePixelPosition = (frameJ*image.getWidth()) + frameI;
                                if (framePixelPosition >= 0 && framePixelPosition < pixels.length){
                                    framePixels[pixelCounter] = pixels[framePixelPosition];
                                    pixelCounter++;
                                }
                            }
                        }
                        if (isCancelled()) return null;
                        newImage[(i*j) + i] = filter.convolute(framePixels, pixelCounter);
                    }
                }

                return Bitmap.createBitmap(newImage, image.getWidth(), image.getHeight(), image.getConfig());
            }

            @Override
            protected void onPostExecute(Bitmap result){
                manager.setSelectedBitMap(result);
            }
        };


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
