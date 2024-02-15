package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UserInput extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myApplication.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinput);

        /** retrieving the image from the previous interface */
        String imageUrl = getIntent().getStringExtra("imagePath");
        Uri myUri = Uri.parse(imageUrl); //imageUrl is parsed into Uri object
        ImageView imageView = findViewById(R.id.image);
        imageView.setImageURI(myUri);

        /** assignment the next button to pass the image and an user input */
        Button next = findViewById(R.id.button2);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInput.this, DisplayPhotoInput.class);
                intent.putExtra("imageURL", myUri.toString());

                /** passing user input */
                EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
                String message = editText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });
    }



}

