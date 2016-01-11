package ece493.imagemanipulation.FilterSettingsActivites;

import android.graphics.Bitmap;

import ece493.imagemanipulation.AppManager;
import ece493.imagemanipulation.R;
import ece493.imagemanipulation.Utilities.ImageHelper;

/**
 * Created by ryan on 08/01/16.
 */
public class MeanFilterSettingsActivity extends FilterSettingsActivity{
    @Override
    protected String getSettingsTitle() {
        return getResources().getString(R.string.mean_filter_settings);
    }

    @Override
    protected void applyFilter(Bitmap image) {
        ImageHelper helper = new ImageHelper((AppManager) getApplication());
        helper.meanFilter(image);
    }
}
