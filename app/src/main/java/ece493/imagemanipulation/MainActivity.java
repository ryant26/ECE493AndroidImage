package ece493.imagemanipulation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectImageIntent = new Intent(Intent.ACTION_PICK);
                selectImageIntent.setType("image/*");
                startActivityForResult(selectImageIntent, SELECT_IMAGE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent returnedImageIntent){
        super.onActivityResult(requestCode, resultCode, returnedImageIntent);
        switch (requestCode){
            case SELECT_IMAGE:
                if (resultCode == RESULT_OK){
                    Uri selectedImageUri = returnedImageIntent.getData();
                    try{
                        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri, "r");
                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                        Bitmap selectedImage = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                        setImage(selectedImage);
                    } catch (IOException e){
                        //TODO Show an error to the user
                        Log.e("ImageSelect", "Could not open File!!");
                    }
                }
        }
    }

    private void setImage(Bitmap Image){
        ImageView imageView = (ImageView) findViewById(R.id.ImageView);
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(Image);
    }


}
