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

    public FilterTask(int filterSize, ConvolutionFilter filter, AppManager manager){
        this.filterSize = filterSize;
        this.filter = filter;
        this.manager = manager;
    }


    @Override
    protected Bitmap doInBackground(Bitmap... params) {
        // Set up frequently used variables
        Bitmap image = params[0];
        int width = image.getWidth();
        int height = image.getHeight();

        // Create arrays for old image, new image and kernel
        int [] pixels = new int[width * height];
        int [] framePixels = new int[filterSize * filterSize];
        int [] newImage = new int[pixels.length];

        // Get the pixel array
        image.getPixels(pixels, 0, width, 0, 0, width, height);

        // Create external counter variables for performance
        int pixelCounter;
        int framePixelPosition;

        // Iterate over every pixel in the image
        for (int j=0; j < height; j++){
            for (int i=0; i < width; i++){

                pixelCounter = 0;

                // Iterate over every pixel in the kernel
                for (int frameJ=j-(filterSize/2); frameJ <= j + (filterSize/2); frameJ++){
                    for (int frameI=i-(filterSize/2); frameI <= i+(filterSize/2); frameI++){

                        framePixelPosition = (frameJ*width) + frameI;

                        // Handle edges of image
                        if (framePixelPosition >= 0 &&
                                framePixelPosition < pixels.length &&
                                frameI >=0 &&
                                frameJ >= 0){

                            framePixels[pixelCounter] = pixels[framePixelPosition];
                            pixelCounter++;
                        }
                    }
                }
                // Kill the loop if we are cancelled
                if (isCancelled()) return null;

                //Apply the convolution with the constructed kernel
                newImage[(j * width) + i] = filter.convolute(framePixels, pixelCounter);
            }
        }
        return Bitmap.createBitmap(newImage, image.getWidth(), image.getHeight(), image.getConfig());
    }

    @Override
    protected void onPostExecute(Bitmap result){
        manager.setSelectedBitMap(result);
        manager.setFilterTask(null);
    }

}
