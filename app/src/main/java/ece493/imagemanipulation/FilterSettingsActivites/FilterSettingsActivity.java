package ece493.imagemanipulation.FilterSettingsActivites;

import android.graphics.Bitmap;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ece493.imagemanipulation.AppManager;
import ece493.imagemanipulation.R;
import ece493.imagemanipulation.Utilities.DialogHelper;
import ece493.imagemanipulation.Utilities.ImageHelper;
import ece493.imagemanipulation.Utilities.Observer;

public abstract class FilterSettingsActivity extends AppCompatActivity implements View.OnClickListener, Observer {

    private EditText filterSizeEntry;
    private AppManager manager;
    private DialogHelper dialogHelper;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up new objects and call backs
        manager = (AppManager) getApplication();
        manager.addObserver(this);
        dialogHelper = new DialogHelper(this);


        //Set up Widget Bindings
        filterSizeEntry = (EditText) findViewById(R.id.filterSizeEntry);

        TextView title = (TextView) findViewById(R.id.settingsViewTitle);
        title.setText(getSettingsTitle());

        Button applyFilterButton = (Button) findViewById(R.id.applyFilterButton);
        applyFilterButton.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        if(filterSize > ImageHelper.getMaxFilterSize(selectedImage)
                || filterSize % 2 == 0
                || filterSize < 3){
            showInvalidFilterDialog();
        } else {
            applyFilter(manager.getSelectedBitMap(), filterSize);
        }
    }

    @Override
    public void update(){
        if(manager.filterTaskRunning()){
            dialogHelper.showProgressDialog();
        } else {
            dialogHelper.hideFilterDialog();
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
    protected abstract void applyFilter(Bitmap image, int filterSize);
}
