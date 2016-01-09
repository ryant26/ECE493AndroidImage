package ece493.imagemanipulation.FilterSettingsActivites;

import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ece493.imagemanipulation.AppManager;
import ece493.imagemanipulation.R;
import ece493.imagemanipulation.Utilities.ImageHelper;

public abstract class FilterSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText filterSizeEntry;
    private AppManager manager;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_settings);
        manager = (AppManager) getApplication();


        //Set up Widget Bindings
        filterSizeEntry = (EditText) findViewById(R.id.filterSizeEntry);
        TextView title = (TextView) findViewById(R.id.settingsViewTitle);
        title.setText(getSettingsTitle());

        Button applyFilterButton = (Button) findViewById(R.id.applyFilterButton);
        applyFilterButton.setOnClickListener(this);

    }

    @Override
    public final void onClick(View v){
        int filterSize = 0;
        try{
            filterSize = Integer.parseInt(filterSizeEntry.getText().toString());
        } catch (NumberFormatException e){
            showInvalidFilterDialog();
        }
        Bitmap selectedImage = manager.getSelectedBitMap();
        if(filterSize > ImageHelper.getMaxFilterSize(selectedImage)){
            showInvalidFilterDialog();
        } else {
            applyFilter();
        }
    }

    private final void showInvalidFilterDialog(){
        int maxFilterSize = ImageHelper.getMaxFilterSize(manager.getSelectedBitMap());
        new AlertDialog.Builder(this)
                .setTitle("Invalid Filter Size")
                .setMessage("You must enter an odd integer between 3 and " + maxFilterSize)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    //Should return the title for the top of the screen
    protected abstract String getSettingsTitle();

    //Should call the appropiate method for each filter type
    protected abstract void applyFilter();
}
