package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import android.graphics.Color;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
public class Ratings_chart extends AppCompatActivity {


    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;

    //variables to keep track of the stars
    int OneStar, TwoStar, ThreeStar, FourStar, FiveStar = 0;

    boolean key = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings_chart);

        //Reading the files from the database
        ArrayList<Integer> list = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("reviews");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!key) {

                    //reading through the data base entries
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Information info = snapshot.getValue(Information.class);
                        int StarRating = 0;
                        if (info != null) {
                            StarRating = info.getRating();
                        }
                        //Storing the star ratings in the list
                        list.add(StarRating);
                    }

                    //counting up the number of ratings for each star
                    for (int i = 0; i < list.size(); i++) {
                        int currentRating = list.get(i);
                        System.out.println("The rating is = " + currentRating);
                        if (list.get(i) == 1) {
                            OneStar++;
                        } else if (list.get(i) == 2) {
                            TwoStar++;
                        } else if (list.get(i) == 3) {
                            ThreeStar++;
                        } else if (list.get(i) == 4) {
                            FourStar++;
                        } else if (list.get(i) == 5) {
                            FiveStar++;
                        }
                    }
                    //BUILDING THE GRAPH ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

                    // initializing variable for bar chart.
                    barChart = findViewById(R.id.idBarChart);

                    // calling method to get bar entries.
                    getBarEntries();

                    // creating a new bar data set.
                    barDataSet = new BarDataSet(barEntriesArrayList, "");
                    // creating a new bar data and
                    // passing our bar data set.
                    barData = new BarData(barDataSet);

                    // below line is to set data
                    // to our bar chart.
                    barChart.setData(barData);

                    //showcasing the chart
                    barChart.invalidate();

                    // <gradient android:startColor="#2AF598" android:endColor="#009EFD" android:type="linear"/>
                    barDataSet.setGradientColor(Color.rgb(42, 245, 152), Color.rgb(0, 158, 253));

                    // setting text color.
                    barDataSet.setValueTextColor(Color.WHITE);

                    // setting text size
                    barDataSet.setValueTextSize(16f);
                    barChart.getDescription().setEnabled(false);

                    //customizing the bar graph
                    barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                    barChart.setExtraBottomOffset(22);

                    //changing the x axis label
                    ArrayList<String> labelNames = new ArrayList<>();

                    //Customizing the X axis
                    labelNames.add("");
                    labelNames.add("1 Star");
                    labelNames.add("2 Stars");
                    labelNames.add("3 Stars");
                    labelNames.add("4 Stars");
                    labelNames.add("5 stars");

                    //adding data to the x axis
                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));
                    xAxis.setLabelCount(5);
                    barChart.getXAxis().setTextSize(15);
                    barChart.setExtraTopOffset(15);
                    barChart.setExtraLeftOffset(22);
                    barChart.getXAxis().setDrawLabels(true);

                    //Customizing the Y axis
                    barChart.getAxisLeft().setTextSize(15);
                    barChart.animateY(2000);

                    //changing some font colors
                    barChart.getAxisLeft().setTextColor(Color.rgb(255, 255, 255));
                    barChart.getXAxis().setTextColor(Color.rgb(255, 255, 255));
                    barChart.getLegend().setTextColor(Color.rgb(255, 255, 255));
                    barChart.getDescription().setTextColor(Color.rgb(255, 255, 255));

                    barChart.getAxisRight().setTextColor(Color.rgb(255, 255, 255));
                    barChart.getAxisRight().setGridColor(Color.rgb(255, 255, 255));
                    barChart.setExtraRightOffset(22);

                    //removing the grid lines
                    barChart.getAxisRight().setDrawGridLines(false);
                    barChart.getAxisLeft().setDrawGridLines(false);
                    barChart.getXAxis().setDrawGridLines(false);
                    barChart.getAxisRight().setDrawLabels(false);

                    barChart.getLegend().setEnabled(false);
                    barChart.getAxisLeft().setAxisMinimum(0f);

                    key = true;

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });

        System.out.println("size of the list is: " + list.size());

        //Reading the files from the database

    }
    private void getBarEntries() {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();

        final ArrayList<String> xAxisLabel = new ArrayList<>();
        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(new BarEntry(1f, OneStar));
        barEntriesArrayList.add(new BarEntry(2f, TwoStar));
        barEntriesArrayList.add(new BarEntry(3f, ThreeStar));
        barEntriesArrayList.add(new BarEntry(4f, FourStar));
        barEntriesArrayList.add(new BarEntry(5f, FiveStar));
    }
}
