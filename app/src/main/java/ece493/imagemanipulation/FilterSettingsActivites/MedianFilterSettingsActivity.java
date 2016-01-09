package ece493.imagemanipulation.FilterSettingsActivites;

import ece493.imagemanipulation.R;

/**
 * Created by ryan on 08/01/16.
 */
public class MedianFilterSettingsActivity extends FilterSettingsActivity {
    @Override
    protected String getSettingsTitle() {
        return getResources().getString(R.string.median_filter_Settings);

    }

    @Override
    protected void applyFilter() {

    }
}
