package ece493.imagemanipulation.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ryan on 02/02/16.
 */

// Much of the code from this class taken directly from Google Documentation:
// http://developer.android.com/training/camera/photobasics.html
public class PhotoHelper {

    static final int REQUEST_TAKE_PHOTO = 1;
    File image;

    private String mCurrentPhotoPath;
    private Activity context;

    public PhotoHelper(Activity context){
        this.context = context;
    }

    public Bitmap getImage(){
        galleryAddPic();
        return BitmapFactory.decodeFile(getPhotoPath());
    }

    public String getPhotoPath() {
        return image.getPath();
    }

    public void takePhoto(){
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            image = null;
            try {
                image = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("FILE SAVING", "Could not save photo to storage");
                Log.e("FILE SAVING", ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (image != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(image));
                context.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(image);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

}
