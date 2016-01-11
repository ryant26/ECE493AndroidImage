package ece493.imagemanipulation.Utilities;

import android.graphics.Bitmap;

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

    public Bitmap meanFilter(Bitmap original){
        return filter(original, new MeanConvolutionFilter());
    }

    public Bitmap medianFilter(Bitmap original){
        return filter(original, new MeanConvolutionFilter());
    }

    private Bitmap filter(Bitmap original, ConvolutionFilter filter){
        
    }

    private class MeanConvolutionFilter implements ConvolutionFilter{

        @Override
        public Integer convolute(List<Integer> mask) {
            return null;
        }
    }


    private class MedianConvolutionFiler implements  ConvolutionFilter{

        @Override
        public Integer convolute(List<Integer> mask) {
            return null;
        }
    }

}
