package ece493.imagemanipulation.FilterSettingsActivites;

import ece493.imagemanipulation.R;

/**
 * Created by ryan on 08/01/16.
 */
public class MeanFilterSettingsActivity extends FilterSettingsActivity{
    @Override
    protected String getSettingsTitle() {
        return getResources().getString(R.string.mean_filter_settings);
    }

    @Override
    protected void applyFilter() {

    }
}
