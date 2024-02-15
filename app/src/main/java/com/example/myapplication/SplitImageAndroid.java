package com.example.myapplication;

import static android.graphics.Bitmap.createBitmap;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;


public class SplitImageAndroid extends AppCompatActivity {

    // Get bitmap array from bitmap image from rows and columns
    public static Bitmap[] getImages(Bitmap img, int rows, int columns) {

        Bitmap[] splittedImages = new Bitmap[rows * columns];

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

    // Create folder and puts bitmap images into it
    public static void createFolder(Bitmap[] imgs, Bitmap img) {

        File tempDir = new File(Environment.getExternalStorageDirectory() + "/outImages");

        // If the folder doesn't exist and it isn't a directory, make it so
        if(!tempDir.exists() && !tempDir.isDirectory()){

            if(tempDir.mkdirs()){

                Log.i("CreateDir","App dir created");

            }

            else {

                Log.w("CreateDir","Unable to create app dir!");

            }

        }

        else {

            Log.i("CreateDir","App dir already exists");
            deleteRecursive(tempDir);

        }

        File file = null;

        try {

            // Write all divided images to folder
            for(int i = 0; i < imgs.length; i++) {

                int j = i+1;
                file = new File(Environment.getExternalStorageDirectory() + File.separator + "outImages/img" + j + ".png");
                file.createNewFile();

                //Convert bitmap to byte array
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                imgs[i].compress(Bitmap.CompressFormat.PNG, 0, bos); // YOU can also save it in JPEG
                byte[] bitmapdata = bos.toByteArray();

                //write the bytes in file
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();

            }

            // Writes original image to folder as well
            File mainFile = new File(Environment.getExternalStorageDirectory() + File.separator + "outImages/Image.png");
            mainFile.createNewFile();

            ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.PNG, 0, bos1); // YOU can also save it in JPEG
            byte[] bitmapdata1 = bos1.toByteArray();

            //write the bytes in file
            FileOutputStream fos1 = new FileOutputStream(mainFile);
            fos1.write(bitmapdata1);
            fos1.flush();
            fos1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory()){

            for (File child : fileOrDirectory.listFiles()) {

                deleteRecursive(child);

            }

        }

    }


}
