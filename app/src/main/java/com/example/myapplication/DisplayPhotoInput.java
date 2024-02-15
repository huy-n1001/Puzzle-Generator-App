package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;

public class DisplayPhotoInput extends AppCompatActivity {
    Button play;
    public static final String EXTRA_MESSAGE2 = "com.example.myApplication.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        play = findViewById(R.id.buttonPlayPuzzle);

        // User clicks 'Generate'
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sending user message to next interface
                Intent intent = new Intent(DisplayPhotoInput.this, PuzzleGenerator.class);
                TextView textView = (TextView) findViewById(R.id.textView);
                String message = textView.getText().toString();
                intent.putExtra(EXTRA_MESSAGE2, message);
                startActivity(intent);
            }
        });


        /** retrieving the image from the previous interface */
        String imageUrl2 = getIntent().getStringExtra("imageURL");
        Uri myUri2 = Uri.parse(imageUrl2);
        ImageView imageView = findViewById(R.id.image);
        imageView.setImageURI(myUri2);

        /** Get the Intent that started this activity and extract the string */
        Intent intent = getIntent();
        String message = intent.getStringExtra(UserInput.EXTRA_MESSAGE);

        try(PrintWriter out = new PrintWriter(Environment.getExternalStorageDirectory() + "/outImages/message.txt")){

            out.println(message);

        }
        catch(IOException e){
            e.printStackTrace();
        }

        /** Capture the layout's TextView and set the string as its text */
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }
}
