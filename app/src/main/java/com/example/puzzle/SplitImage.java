package com.example.puzzle;

import static android.graphics.Bitmap.createBitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import java.nio.file.Paths;
import java.nio.file.Path;

public class SplitImage {


    private static Bitmap bibi;

    // Path to origin image
    public static String pathDir = "/Puzzle/src/main/resources/one.png";

    //Path to outputted images
    public static String outDir = "/Puzzle/src/main/resources/out";
    //String debugDir2 = "/ImageSplit/target/classes/out/" //use in case incorrect path resolution

    //Function to create puzzle pieces(images)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void makeImages(String pathname) {

        // Row and column count set to 2 for a total of 4
        int rCount = 2;
        int cCount = 2;

        // Load and split image based on width and height
        loadImage(pathname);
        Bitmap img = bibi.createBitmap(bibi.getWidth(), bibi.getHeight(), bibi.getConfig());
         Bitmap[] imgs = SplitImage.getImages(img, rCount, cCount);

        //Create a folder for split images
        createFolder(pathname + outDir);
        System.out.println(outDir + "/img");

        //Write the images to the new directory and name them accordingly
        for (int i = 0; i < imgs.length; i++) {

            //ImageIO.write(imgs[i], "jpg", new File(pathname + outDir + "/img" + i + ".png"));
            File file = new File(pathname, "img" + i + ".jpg");
            if(!file.exists()){

                Log.d("path", file.toString());
                FileOutputStream fos = null;

                try{
                    fos = new FileOutputStream(file);
                    img.compress(Bitmap.CompressFormat.JPEG,100,fos);
                    fos.flush();
                    fos.close();
                }

                catch (IOException e){

                    e.printStackTrace();

                }

            }


        }

    }

    public static void loadImage(String pathname) {

        try {

            // Read path name and take image from directory
            pathname = pathname + pathDir;
            System.out.println(pathname);

            File file = new File(".");

            //for(String fileNames : file.list()) System.out.println(fileNames);
            //bibi = ImageIO.read(new FileInputStream(pathname));

            bibi = BitmapFactory.decodeFile(pathname);

        }
        catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static Bitmap[] getImages(Bitmap img, int rows, int columns) {

        // Create array for split images
        Bitmap[] splittedImages = new Bitmap[rows * columns];
        //Bitmap bi = createBitmap(img.getWidth(), img.getHeight());

        //Graphics g = bi.createGraphics();
        //g.drawImage(img, 0, 0, null);

        // Take in dimensions and divide by desired columns and rows
        int width = img.getWidth();
        int height = img.getHeight();
        int pos = 0;
        int sWidth = width / columns;
        int sHeight = height / rows;

        //Divide the source images into separate puzzle pieces based on the dimensions provided
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {

                Bitmap bimg = createBitmap(img,j * sWidth, i * sHeight, sWidth, sHeight);
                splittedImages[pos] = bimg;
                pos++;

            }

        }

        return splittedImages;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createFolder(String pathname) {

        File directory = new File(pathname);

        // If a directory doesn't already exist, create a new one
        if (!directory.exists()) {

            directory.mkdir();

        }

        // Otherwise delete all files in directory and create new directory
        else {

            Path tempDir = Paths.get(pathname);
            String[]entries = tempDir.toFile().list();

            for(String s: entries){

                File currentFile = new File(tempDir.toFile().getPath(),s);
                currentFile.delete();

            }

            tempDir.toFile().delete();

            //make new directory
            directory.mkdir();
        }
    }
}
