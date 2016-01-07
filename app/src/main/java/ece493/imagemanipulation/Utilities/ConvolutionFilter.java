package ece493.imagemanipulation.Utilities;

import java.util.List;

/**
 * Created by ryan on 06/01/16.
 */
public interface ConvolutionFilter {
    Integer convolute(List<Integer> mask);
}
