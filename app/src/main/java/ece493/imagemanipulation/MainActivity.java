package ece493.imagemanipulation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.IOException;

import ece493.imagemanipulation.FilterSettingsActivites.MeanFilterSettingsActivity;
import ece493.imagemanipulation.FilterSettingsActivites.MedianFilterSettingsActivity;
import ece493.imagemanipulation.Utilities.DialogHelper;
import ece493.imagemanipulation.Utilities.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private static final int SELECT_IMAGE = 99;
    private AppManager appManager;
    private Menu menu;
    private DialogHelper dialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect Widgets
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize helpers and manager
        dialogHelper = new DialogHelper(this);
        appManager = ((AppManager) getApplicationContext());
        appManager.addObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem meanFilter = menu.findItem(R.id.apply_mean_filter);
        MenuItem medianFilter = menu.findItem(R.id.apply_median_filter);

        // Only enable apply filter activities if an image is selected
        if (appManager.getSelectedBitMap() != null){
            meanFilter.setEnabled(true);
            medianFilter.setEnabled(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.import_image:
                selectImage();
                return true;
            case R.id.apply_mean_filter:
                startSettingsActivity(MeanFilterSettingsActivity.class);
                return true;
            case R.id.apply_median_filter:
                startSettingsActivity(MedianFilterSettingsActivity.class);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();
        update();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent returnedImageIntent){
        super.onActivityResult(requestCode, resultCode, returnedImageIntent);
        switch (requestCode){
            case SELECT_IMAGE:
                if (resultCode == RESULT_OK){
                   receiveImage(returnedImageIntent);
                }
                break;
        }
    }

    @Override
    public void update() {
        setImage(appManager.getSelectedBitMap());
        if(appManager.filterTaskRunning()){
            dialogHelper.showProgressDialog();
        } else {
            dialogHelper.hideFilterDialog();
        }
    }

    private void setImage(Bitmap image) {
        ImageView imageView = (ImageView) findViewById(R.id.ImageView);
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(image);
    }

    private void selectImage(){
        Intent selectImageIntent = new Intent(Intent.ACTION_PICK);
        selectImageIntent.setType("image/*");
        startActivityForResult(selectImageIntent, SELECT_IMAGE);
    }

    private void startSettingsActivity(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    private void receiveImage(Intent returnedImageIntent){
        Uri selectedImageUri = returnedImageIntent.getData();
        try{
            ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

            if(appManager.filterTaskRunning()){
                appManager.cancelFilterTask();
            }

            appManager.setSelectedBitMap(BitmapFactory.decodeFileDescriptor(fileDescriptor));
        } catch (IOException e){
            Log.e("ImageSelect", "Could not open File!!");
            showFileProblemDialog();
        }
    }

    private final void showFileProblemDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Could not open file")
                .setMessage("We could not open that image file. Please try again.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
