package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainMenu extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //NEW CODE FOR Database
        Button datab = findViewById(R.id.button6);
        datab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putInt("Data", 1);
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                startActivity(new Intent(MainMenu.this, dbHandler.class));
            }
        });

        // linking start button to MainActivity
        Button play = findViewById(R.id.button3);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkPermission()) {

                    startActivity(new Intent(MainMenu.this, Import.class));
                }

                else{
                    requestPermission();

                }

            }
        });

        Button settings = findViewById(R.id.button5);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, Settings1.class));
            }
        });

        // Button that sends you to the tutorial
        Button tutorial = findViewById(R.id.tutorialButton);
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, Tutorial.class));
            }
        });

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

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, STORAGE_PERMISSION_CODE);

        }

    }
    // Activity result launcher to ask for permission
    private ActivityResultLauncher<Intent> storageActivityResultLauncher = registerForActivityResult(

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

                    else{

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
            //int cam = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

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
                boolean cam = grantResults[2] == PackageManager.PERMISSION_GRANTED;

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
