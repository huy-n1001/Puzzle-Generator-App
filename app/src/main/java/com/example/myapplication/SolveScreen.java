package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class SolveScreen extends AppCompatActivity {

    Button backToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);

        backToMenu = findViewById(R.id.mainMenu);

        backToMenu.setOnClickListener(new View.OnClickListener() {

            // On click, returns to main menu
            @Override
            public void onClick(View view) {

                File dir = new File (Environment.getExternalStorageDirectory() + "/outImages");
                deleteRecursive(dir);
                startActivity(new Intent(SolveScreen.this, MainMenu.class));
            }
        });

        /** retrieving the image from the previous interface */
        File imgFile1 = new File(Environment.getExternalStorageDirectory() + File.separator + "outImages/image.png");
        Bitmap myBitmap1 = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());

        ImageView imageView = findViewById(R.id.image);
        imageView.setImageBitmap(myBitmap1);

        /** get message from PuzzleGenerator activity */
        Intent intent = getIntent();
        String message = intent.getStringExtra(PuzzleGenerator.EXTRA_MESSAGE3);

        /** Capture the layout's TextView and set the string as its text */
        TextView textView = findViewById(R.id.textViewer);
        textView.setText(message);



    }

    // Deletes folder
    public static void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory()){

            for (File child : fileOrDirectory.listFiles()) {

                deleteRecursive(child);

            }

        }

        fileOrDirectory.delete();

    }

}
