package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class Import extends AppCompatActivity {

    int CAMERA_REQUEST = 1888;
    boolean key;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        /** assigning the gallery button to import gallery function */
        Button gallery = findViewById(R.id.button);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                key = true;
                startActivityForResult(intent, 3);
            }
        });


        //button for the camera
        Button Camera = findViewById(R.id.Cambutton);
        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //saving the taken photo in a file for a better quality
                String FileName = "CamPhoto";
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                try {
                    File imageFile = File.createTempFile(FileName, ".jpg", storageDirectory);

                    currentPhotoPath = imageFile.getAbsolutePath();

                    Uri imageUri = FileProvider.getUriForFile(Import.this, "com.example.myapplication.fileprovider", imageFile);

                    //making an intent to save the taken photo
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    key = false;
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        /** implemented an arrow in the title bar */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // upon clicking the back button, sends back to home interface
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int diff = Settings1.getDifficulty() + 2;
        System.out.println(diff);
        /** converting image to uri object and passing to the next interface */
        if(resultCode == RESULT_OK && data != null && key) {
            Uri selectedImage = data.getData();
            Intent intent = new Intent(Import.this, UserInput.class);
            intent.putExtra("imagePath", selectedImage.toString());

            //Takes in uri and turns it into bitmap
            Bitmap img = null;
            try {
                img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            //Splits image up into pieces
            Bitmap[] imgs = SplitImageAndroid.getImages(img, diff, diff);

            System.out.println("Width and height of original image: " + img.getWidth() + " x " + img.getHeight());
            System.out.println("Width and height of split images: " + imgs[0].getWidth() + " x " + imgs[0].getHeight());

            System.out.println(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));

            //applying the SplitImage to the photo
            SplitImageAndroid.createFolder(imgs, img);
            startActivity(intent);

        }

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && !key){

            Bitmap img = BitmapFactory.decodeFile(currentPhotoPath);

            Uri passUri = getImageUri(this, img);
            Intent intent = new Intent(Import.this, UserInput.class);
            intent.putExtra("imagePath", passUri.toString());

            //Splits image up into pieces
            Bitmap[] imgs = SplitImageAndroid.getImages(img, diff, diff);

            System.out.println("Width and height of original image: " + img.getWidth() + " x " + img.getHeight());
            System.out.println("Width and height of split images: " + imgs[0].getWidth() + " x " + imgs[0].getHeight());

            System.out.println(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));

            SplitImageAndroid.createFolder(imgs, img);
            startActivity(intent);

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage){

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);

    }

    private static final int STORAGE_PERMISSION_CODE = 100;
    private static final String TAG = "PERMISSION_TAG";

    // Requests permission to read/write files
    private void requestPermission(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){

            try{
                Log.d(TAG, "requestPermission: try");

                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                storageActivityResultLauncher.launch(intent);

            }
            catch(Exception e){

                Log.e(TAG, "requestPermission: catch", e);
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                storageActivityResultLauncher.launch(intent);

            }

        }

        else{

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

        }

    }
    // Activity result launcher to ask for permission
    private final ActivityResultLauncher<Intent> storageActivityResultLauncher = registerForActivityResult(

        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>(){

            @Override
            public void onActivityResult(ActivityResult result){

                Log.d(TAG, "onActivityResult: ");

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){

                    if(Environment.isExternalStorageManager()){

                        Log.d(TAG, "onActivityResult: Manage External Storage Permission is granted");

                    }
                    else{

                        Log.d(TAG, "onActivityResult: Manage External Storage Permission is denied");
                    }
                }
            }

        }

    );

    // Checks if permission is given
    public boolean checkPermission(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){

            return Environment.isExternalStorageManager();

        }

        else{

            int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
        }

    }

    // Looks at the result and responds
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE){

            if(grantResults.length > 0){

                boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if(write && read){

                    Log.d(TAG, "onRequestPermissionsResult: External Storage permissions granted");
                    // Put function here

                }

                else{

                    Log.d(TAG, "onRequestPermissionsResult: External Storage permissions denied");

                }

            }


        }


    }


}

