package ece493.imagemanipulation.Utilities;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by ryan on 06/01/16.
 */
public class ImageHelper {

    public static int getMaxFilterSize(Bitmap image){
        return image.getHeight() > image.getWidth() ? image.getWidth() : image.getHeight();
    }

    public Bitmap meanFilter(Bitmap original){
        return null;
    }

    public Bitmap medianFilter(Bitmap original){
         return null;
    }

    private Bitmap filter(Bitmap original, ConvolutionFilter filter){
        return null;
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
