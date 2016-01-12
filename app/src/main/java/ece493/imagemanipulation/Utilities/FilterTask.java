package ece493.imagemanipulation.Utilities;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import ece493.imagemanipulation.AppManager;

/**
 * Created by ryan on 11/01/16.
 */
public class FilterTask extends AsyncTask<Bitmap, Void, Bitmap> {
    int filterSize;
    ConvolutionFilter filter;
    AppManager manager;
    boolean finishedFiltering;

    public FilterTask(int filterSize, ConvolutionFilter filter, AppManager manager){
        this.filterSize = filterSize;
        this.filter = filter;
        this.manager = manager;
        finishedFiltering = false;
    }


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
                for (int frameJ=j-(filterSize/2); frameJ <= j + (filterSize/2); frameJ++){
                    for (int frameI=i-(filterSize/2); frameI <= i+(filterSize/2); frameI++){
                        int framePixelPosition = (frameJ*image.getWidth()) + frameI;
                        if (framePixelPosition >= 0 &&
                                framePixelPosition < pixels.length &&
                                frameI >=0 &&
                                frameJ >= 0){
                            framePixels[pixelCounter] = pixels[framePixelPosition];
                            pixelCounter++;
                        }
                    }
                }
                if (isCancelled()) return null;
                newImage[(j * image.getWidth()) + i] = filter.convolute(framePixels, pixelCounter);
            }
        }

        return Bitmap.createBitmap(newImage, image.getWidth(), image.getHeight(), image.getConfig());
    }

    @Override
    protected void onPostExecute(Bitmap result){
        finishedFiltering = true;
        manager.setSelectedBitMap(result);
    }

    public boolean isFinishedFiltering(){
        return finishedFiltering;
    }
}
