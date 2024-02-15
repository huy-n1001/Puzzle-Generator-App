package com.example.myapplication;

import static com.example.myapplication.UserInput.EXTRA_MESSAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.BuildConfig;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;


public class PuzzleGenerator extends AppCompatActivity {

    public static final String EXTRA_MESSAGE3 = "com.example.myApplication.MESSAGE";

    //a method to get rid of the "A resource failed to call release" error
    public PuzzleGenerator() {
        if(BuildConfig.DEBUG)
            StrictMode.enableDefaults();
    }
    //Variable for the number of squares in the puzzle
    //For example, if the PuzzleDimension is 2, the puzzle will be 2x2

    int PuzzleDimension = Settings1.getDifficulty() + 2;

    //variable for the number of the puzzle pieces
    int PuzzleNumber = PuzzleDimension * PuzzleDimension;

    //2D array to check if the puzzle pieces are in the correct placement
    String [][] PuzzlePieces = new String[PuzzleDimension][PuzzleDimension];

    //2D array to record the current placement of the puzzle piece
    String [][] CurrentPlacement = new String[PuzzleDimension][PuzzleDimension];

    //Variable for the size of the puzzle background
    int PuzzleSize;

    //array to keep track of the original placement of each puzzle piece
    //this will be used for the case when two puzzles overlap, these
    //arrays will be used to put the puzzle back in the original spot
    float [] OriginalPlacementX = new float[PuzzleNumber];
    float [] OriginalPlacementY = new float[PuzzleNumber];


    //an array for the fully solved condition
    //These arrays are mainly used for double checking the win condition
    ImageView [][] CurrentPosition = new ImageView[PuzzleDimension][PuzzleDimension];
    float [][] CurrentPositionX = new float[PuzzleDimension][PuzzleDimension];
    float [][] CurrentPositionY = new float[PuzzleDimension][PuzzleDimension];


    //Array for keeping the current location of the puzzle pieces
    //This will be used to avoid overlapping of the puzzle pieces
    ArrayList<ImageView> pieceLegend;

    //variables for moving the images
    float xDown = 0, yDown = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_generator);

        //for retrieving the image and the message from the previous activity
        Intent intent = getIntent();
        String message = intent.getStringExtra(DisplayPhotoInput.EXTRA_MESSAGE2);
        TextView textView = findViewById(R.id.textBox);
        textView.setText(message);

        System.out.println(PuzzleDimension);

        //Getting the dimensions of the whole layout
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //getting the width of the puzzle background
        PuzzleSize = displayMetrics.widthPixels;

        //Puzzle piece dimensions
        //Subtracting it by 20% of the screen to make the scaling better
        int PieceDimension = (PuzzleSize/PuzzleDimension) - ((PuzzleSize/PuzzleDimension)/5);
        int GridDimension = PieceDimension * PuzzleDimension;

        //For accessing our current layout
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layout);
        relativeLayout.setBackgroundColor(Color.argb(250,51,51,51));

        int PieceOffset = (PuzzleSize - GridDimension)/2;

        RelativeLayout.LayoutParams GridParams;

        //Loading the grid for the background
        ImageView BackgroundGrid = new ImageView(this);
        if(PuzzleDimension == 2){

            //two by two grid
            BackgroundGrid.setImageResource(getResources().getIdentifier("twotwo", "drawable", getPackageName()));
            GridParams = new RelativeLayout.LayoutParams(GridDimension, GridDimension);

            //making the grid centered
            GridParams.addRule(RelativeLayout.ALIGN_TOP);
            GridParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            relativeLayout.addView(BackgroundGrid, GridParams);

            System.out.println("the y coordinate is: "  + BackgroundGrid.getY());
        }else if(PuzzleDimension == 3){

            //three by three grid
            BackgroundGrid.setImageResource(getResources().getIdentifier("all", "drawable", getPackageName()));
            GridParams = new RelativeLayout.LayoutParams(GridDimension, GridDimension);

            //making the grid centered
            GridParams.addRule(RelativeLayout.ALIGN_TOP);
            GridParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            relativeLayout.addView(BackgroundGrid, GridParams);

            System.out.println("the y coordinate is: "  + BackgroundGrid.getY());
        }else if(PuzzleDimension == 4) {

            //four by four grid
            BackgroundGrid.setImageResource(getResources().getIdentifier("fourfour", "drawable", getPackageName()));
            GridParams = new RelativeLayout.LayoutParams(GridDimension, GridDimension);

            //making the grid centered
            GridParams.addRule(RelativeLayout.ALIGN_TOP);
            GridParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            relativeLayout.addView(BackgroundGrid, GridParams);
        }

            System.out.println("the y coordinate is: "  + BackgroundGrid.getY());

        //variables for changing the original placement of the puzzle pieces
        float x = PuzzleSize/20;
        float y = -PuzzleSize/20;

        //this variable is for stacking the puzzle pieces in their original placement
        int order = 0;

        //two variables that are used as indices when adding the image names to the array
        int j = 0;
        int k = 0;

        //JEFFS CODE
        pieceLegend = new ArrayList<>();

        //reading through the image files
        for (int i = 1; i < PuzzleNumber + 1; i++) {

            //Variables used for naming the imageviews
            String numbering = String.valueOf(i);
            String ImageName = "img" + numbering ;

            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inMutable = true;

            File imgFile = new File(Environment.getExternalStorageDirectory() + File.separator + "outImages/img" + i + ".png");
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), opt);

            //variables for resizing img
            float ImageWidth = myBitmap.getWidth();
            float ImageHeight = myBitmap.getHeight();

            //creating a new imageview for the puzzle piece
            ImageView CurrentImage = new ImageView(this);
            CurrentImage.setImageBitmap(myBitmap);

            CurrentImage.setMinimumWidth(PieceDimension);
            CurrentImage.setMinimumHeight(PieceDimension);
            //Changing the size of the images
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(PieceDimension, PieceDimension);

            //Setting up the positioning for the image
            CurrentImage.setX(x);
            CurrentImage.setY(y);

            //variable for the original y coordinate
            float OriginalPos = PuzzleSize - (displayMetrics.heightPixels/10);

            //storing the original placements for the puzzle pieces
            OriginalPlacementX[i-1] = x;
            OriginalPlacementY[i-1] = OriginalPos;

            //Setting up the mouse moving abilities on the individual puzzle pieces
            CurrentImage.setOnTouchListener(new View.OnTouchListener(){

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event){

                    switch (event.getActionMasked()){

                        //the imageview is clicked
                        case MotionEvent.ACTION_DOWN:
                            xDown = event.getX();
                            yDown = event.getY();

                            CurrentImage.bringToFront();
                            break;

                        //the user drags the image
                        case MotionEvent.ACTION_MOVE:
                            float movedX, movedY;
                            movedX = event.getX();
                            movedY = event.getY();

                            //calculating how much they have moved the photo
                            float distanceX = movedX - xDown;
                            float distanceY = movedY - yDown;


                            //moving the images to the new position
                            CurrentImage.setX(CurrentImage.getX()+distanceX);
                            CurrentImage.setY(CurrentImage.getY()+distanceY);
                            break;

                        //The user lets go of the mouse
                        case MotionEvent.ACTION_UP:
                            PuzzleLocker(CurrentImage, ImageName, PieceDimension, PieceOffset);

                            //if the puzzle is off screen
                            float ScreenWidth = displayMetrics.widthPixels;
                            float ScreenHeight = displayMetrics.heightPixels;
                            //checking if the imageview is offscreen
                            OffScreen(ScreenWidth, ScreenHeight, CurrentImage);

                            System.out.println("X = " + CurrentImage.getX());
                            System.out.println("y = " + CurrentImage.getY());

                    }
                    return true;
                }

            });

            //Adding the locations of the puzzle pieces to keep track of them
            pieceLegend.add(CurrentImage);

            //This is the original placement of the puzzles
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            if (ImageHeight > ImageWidth) {
                CurrentImage.setScaleX(ImageHeight/ImageWidth);
            } else{
                CurrentImage.setScaleY(ImageWidth/ImageHeight);
            }
            //adding the puzzle piece image to the layout
            relativeLayout.addView(CurrentImage, layoutParams);


            x += PuzzleSize/PuzzleDimension;
            order++;

            //The purpose of the loop is to stack the puzzle pieces for the original placement
            //Example: if we have a 2x2 puzzle, the original placement for the puzzles will have 2 slots and
            // all the pieces will be stacked on top of each other in those two slots
            if (order == PuzzleDimension){
                //initializing with PuzzleSize/20 rather than 0 just to make the placement look better
                //on the UI
                x = PuzzleSize/20;
                y = -PuzzleSize/20;
                order = 0;
            }

            //Adding the puzzle names to PuzzlePieces
            //This will be used for the comparison to find the correct placement-
            //-of the puzzles
            PuzzlePieces[j][k] =ImageName;
            j++;
            if(j == PuzzleDimension  && k < PuzzleDimension - 1){
                j = 0;
                k++;
            }

        }
    }

    //a method to place the image back into its original position in case it the image goes off screen
    private void OffScreen(float ScreenWidth, float ScreenHeight, ImageView CurrentImage) {

        //checking if the coordinates are off the screen
        if(CurrentImage.getX() > 0.8 * ScreenWidth || CurrentImage.getX() < (-0.2) * ScreenWidth ||
                CurrentImage.getY() > 0.8 * ScreenHeight || CurrentImage.getY() < (-0.15) * ScreenHeight){
            CurrentImage.setX(OriginalPlacementX[1]);
            CurrentImage.setY(OriginalPlacementY[1]);
        }
    }

    //method for locking in the puzzle pieces into a certain coordinate
    //based on the location that they are left in by mouse drag
    @SuppressLint("ClickableViewAccessibility")
    private void PuzzleLocker(ImageView CurrentImage, String ImageName, int PieceDimension, int PieceOffset) {
        float Xlocation = CurrentImage.getX();
        float Ylocatiopn = CurrentImage.getY();

        //for loop for locking the puzzle pieces in a specific place when they are within a coordinate
        for (int x = 0; x < PuzzleDimension; x++) {
            for(int y= 0; y < PuzzleDimension; y++){

                //these coordinates will be used to lock the images
                //the images will be locked into these coordinates based on where they are
                float XCoordinate = (x * (PieceDimension)) + PieceOffset;
                float YCoordinate = (y * (PieceDimension)) ;

                //offset variable for finding the +- boundaries for the puzzle piece to lock into
                int Offset = (PieceDimension)/2;

                if((Xlocation > XCoordinate - Offset) && (Xlocation < XCoordinate + Offset)
                        && (Ylocatiopn > YCoordinate - Offset) && (Ylocatiopn < YCoordinate + Offset)){
                    //Locking the puzzle piece into a specific coordinate based on where
                    //the user puts it
                    CurrentImage.setX(XCoordinate);
                    CurrentImage.setY(YCoordinate);

                    //Getting the current coordinate of the puzzle pieces to
                    //double check the win condition
                    CurrentPosition[x][y] = CurrentImage;
                    CurrentPositionX[x][y] = XCoordinate;
                    CurrentPositionY[x][y] = YCoordinate;

                    CurrentPlacement[x][y] = ImageName;

                    //checking if the puzzle piece is in the correct spot
                    if (Objects.equals(PuzzlePieces[x][y], CurrentPlacement[x][y])){
                        System.out.println("this piece is correct");
                    }

                    //checking whether the puzzle is fully solved
                    if(Fully_solved()){

                        // sending user message to SolveScreen activity
                        Intent intent = new Intent(PuzzleGenerator.this, SolveScreen.class);
                        TextView textView = (TextView) findViewById(R.id.textBox);
                        String message = textView.getText().toString();
                        intent.putExtra(EXTRA_MESSAGE, message);
                        startActivity(intent);

                        //Clearing out the arrays 5 seconds after the puzzle is solved
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                //resetting the array back to empty
                                for(int i = 0; i < PuzzleDimension; i++){
                                    for (int j = 0; j < PuzzleDimension; j++) {
                                        //clearing out the array
                                        CurrentPlacement[i][j] = "";
                                    }
                                }

                                //resetting the array back to empty
                                for(int i = 0; i < PuzzleDimension; i++){
                                    for (int j = 0; j < PuzzleDimension; j++) {
                                        //clearing out the array
                                        CurrentPosition[i][j].setX(0);
                                        CurrentPosition[i][j].setY(0);
                                    }
                                }
                            }
                        }, 5000);   //5 seconds


                    }

                    //Making sure the puzzles do not overlap
                    CheckOverlap(CurrentImage);
                }
            }
        }
    }

    //A method to ensure that no two pieces of puzzle completely overlap
    private void CheckOverlap(ImageView CurrentImage) {
        for(int i = 0; i < PuzzleNumber; i++) {
            if (CurrentImage != pieceLegend.get(i)
                    && CurrentImage.getX() == pieceLegend.get(i).getX()
                    && CurrentImage.getY() == pieceLegend.get(i).getY()) {

                //moving the currently existing puzzle piece (the one that is occupying the space)
                //back to its original spot
                pieceLegend.get(i).setX(OriginalPlacementX[i]);
                pieceLegend.get(i).setY(OriginalPlacementY[i]);
            }
        }
    }

    //a method that checks whether the puzzle is fully solved
    private boolean Fully_solved() {
        for (int i = 0; i < PuzzleDimension; i++) {
            for (int j = 0; j < PuzzleDimension; j++) {
                if (!Objects.equals(PuzzlePieces[i][j], CurrentPlacement[i][j])) {
                    return false;
                }
            }
        }
        //checking the current coordinate of the puzzle pieces
        for (int i = 0; i < PuzzleDimension; i++) {
            for (int j = 0; j < PuzzleDimension; j++) {
                float XCoord = CurrentPosition[i][j].getX();
                float YCoord = CurrentPosition[i][j].getY();
                if(CurrentPositionX[i][j] != XCoord || CurrentPositionY[i][j] != YCoord){
                    return false;
                }
            }
        }
        return true;
    }
}
