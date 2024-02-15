package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dbHandler extends AppCompatActivity {

    //database variables
    FirebaseDatabase db;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        //initialize reviewObject
        reviewObject review = new reviewObject();

        //attaching references
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        EditText msgEdit = findViewById(R.id.msgEdit);
        Button submit = findViewById(R.id.button4);
        Button ratings = findViewById(R.id.ratingButton);

        //database connectivity
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("reviews");

        // User clicks submit - data is gathered and user goes back to main menu
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = msgEdit.getText().toString();
                float numStars = ratingBar.getRating();
                submitData(review, (int) numStars, msg);
                startActivity(new Intent(dbHandler.this, MainMenu.class));
            }
        });
        //navigate to ratings acitivity
        ratings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(dbHandler.this, Ratings_chart.class));
            }
        });

    }

    //Submits data to database
    private void submitData(reviewObject rev, int rating, String msg) {
        rev.setRating(rating);
        rev.setMessage(msg);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.push().setValue(rev);
                Toast.makeText(dbHandler.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
            }
            //on submit failure
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(dbHandler.this, "There was an error " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
