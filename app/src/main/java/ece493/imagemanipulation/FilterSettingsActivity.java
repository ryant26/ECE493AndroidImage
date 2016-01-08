package ece493.imagemanipulation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class FilterSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_Settings);
    }

    //Should return the title for the top of the screen
    protected abstract String getSettingsTitle();

    //Should call the appropiate method for each filter type
    protected abstract void applyFilter();
}
