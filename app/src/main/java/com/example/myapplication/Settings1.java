package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;


public class Settings1 extends AppCompatActivity {

    SeekBar simpleSeekBar;
    public static int progressChangedValue = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Context context = getApplicationContext();

        // Seekbar that can be used to change difficulty
        simpleSeekBar=(SeekBar)findViewById(R.id.difficultySeek);

        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            // When progress changed, changes progress value
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // Placeholder function for starting touch
            }

            // Upon stopping, print out the new value
            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println(progressChangedValue);
                Toast display = Toast.makeText(context, "Puzzle size set to: " + (progressChangedValue+2) + " x " + (progressChangedValue+2), Toast.LENGTH_SHORT);
                display.show();
            }
        });

    }



    // Function that returns the current difficulty
    public static final int getDifficulty(){

        return progressChangedValue;

    }



}
