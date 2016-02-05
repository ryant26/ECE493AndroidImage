package ece493.imagemanipulation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.IOException;

import ece493.imagemanipulation.FilterSettingsActivites.MeanFilterSettingsActivity;
import ece493.imagemanipulation.FilterSettingsActivites.MedianFilterSettingsActivity;
import ece493.imagemanipulation.GestureListeners.BulgeListener;
import ece493.imagemanipulation.GestureListeners.FishEyeListener;
import ece493.imagemanipulation.GestureListeners.GestureAggregator;
import ece493.imagemanipulation.GestureListeners.SwirlListener;
import ece493.imagemanipulation.Utilities.DialogHelper;
import ece493.imagemanipulation.Utilities.Observer;
import ece493.imagemanipulation.Utilities.PhotoHelper;

public class MainActivity extends AppCompatActivity implements Observer {

    private static final int SELECT_IMAGE = 99;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private AppManager appManager;
    private Menu menu;
    private DialogHelper dialogHelper;
    private PhotoHelper photoHelper;
    private GestureDetectorCompat gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Gesture listeners
        GestureAggregator gestureAggregator = new GestureAggregator();
        gestureAggregator.addListener(new FishEyeListener());
        gestureAggregator.addListener(new SwirlListener());
        gestureAggregator.addListener(new BulgeListener());


        // Connect Widgets
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView iView = (ImageView) findViewById(R.id.ImageView);
        iView.setOnTouchListener(gestureAggregator);

        // Initialize helpers and manager
        dialogHelper = new DialogHelper(this);
        photoHelper = new PhotoHelper(this);
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
            case R.id.take_photo:
                photoHelper.takePhoto();
                return true;
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
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK){
                    recievePhoto();
                }
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

    @Override
    public boolean onTouchEvent(MotionEvent e){
        gestureDetector.onTouchEvent(e);
        return super.onTouchEvent(e);
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

    private void recievePhoto(){
        appManager.setSelectedBitMap(photoHelper.getImage());
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
