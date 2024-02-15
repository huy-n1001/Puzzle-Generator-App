package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class Tutorial extends AppCompatActivity {

    private TextView tutorialText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        // Displays tutorial text
        tutorialText = (TextView) findViewById(R.id.tutorialText);
        String htmlText = "<p> To begin, press play and then press import to upload a photo of an important person, a special place, or something meaningful to the patient. </p> <p> ↓ </p> <p> In the event that the photo is consistently rotated from what was intended, take the photo in landscape mode (Hold the phone sideways) </p> <p> ↓ </p> <p> On the next screen, add an appropriate description to the picture and ensure it is something the patient will be able to comprehend. </p> <p> ↓ </p> <p> On the next screen, press 'GENERATE' and hand the phone over to the PWD and allow them to finish the puzzle. Should they need assistance, you should help them. </p> <p> ↓ </p> <p> If the difficulty is too hard or too easy, it can be altered in the settings. </p>";
        tutorialText.setText(Html.fromHtml(htmlText));

    }
}
