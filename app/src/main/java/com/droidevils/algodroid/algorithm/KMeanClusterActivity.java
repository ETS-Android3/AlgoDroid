package com.droidevils.algodroid.algorithm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.droidevils.algodroid.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KMeanClusterActivity extends AppCompatActivity {

    int np, nc;
    LinearLayout inputLayout, resultLayout;
    TableLayout resultTable;
    TextInputLayout numPointsLayout, numClusterLayout, pointsLayout, clusterLayout, distanceLayout;
    AutoCompleteTextView distanceMethod;
    Button continueButton, computeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kmean_cluster);

        inputLayout = findViewById(R.id.kmeans_input_layout);
        resultLayout = findViewById(R.id.kmeans_result_layout);
        resultTable = findViewById(R.id.kmeans_result_table);
        numPointsLayout = findViewById(R.id.num_points_layout);
        numClusterLayout = findViewById(R.id.num_clusters_layout);
        pointsLayout = findViewById(R.id.kmeans_point_layout);
        clusterLayout = findViewById(R.id.kmeans_cluster_layout);
        distanceLayout = findViewById(R.id.kmeans_distance_method);
        distanceMethod = findViewById(R.id.kmeans_distance_method_atv);
        continueButton = findViewById(R.id.kmeans_continue);
        computeButton = findViewById(R.id.kmeans_compute);

        ArrayAdapter<String> distanceMethodAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.kmeans_distance_method));
        distanceMethod.setAdapter(distanceMethodAdapter);
    }

    public void onKMeansContinue(View view) {
        String numPoints = numPointsLayout.getEditText().getText().toString().trim();
        String numClusters = numClusterLayout.getEditText().getText().toString().trim();

        boolean validate = true;

        if (numPoints.isEmpty()) {
            numPointsLayout.getEditText().setError("Field cannot be empty");
            validate = false;
        }
        if (numClusters.isEmpty()) {
            numClusterLayout.getEditText().setError("Field cannot be empty");
            validate = false;
        }
        if (!validate)
            return;
        np = Integer.valueOf(numPoints);
        nc = Integer.valueOf(numClusters);
        pointsLayout.getEditText().setLines(np);
        pointsLayout.getEditText().setMaxLines(np);
        clusterLayout.getEditText().setLines(nc);
        clusterLayout.getEditText().setMaxLines(nc);

        inputLayout.setVisibility(View.VISIBLE);
    }

    public void onKMeansCompute(View view) {
        String points = pointsLayout.getEditText().getText().toString().trim();
        String clusters = clusterLayout.getEditText().getText().toString().trim();
        String distance = distanceMethod.getText().toString().trim();

        //Validate
        boolean validate = true;
        String[] array = points.split("\n");
        if (array.length == np) {
            for (String pair : array) {
                if (!useRegex(pair.trim())) {
                    pointsLayout.getEditText().setError("Invalid Input");
                    validate = false;
                }
            }
        } else {
            pointsLayout.getEditText().setError("Enter " + np + " points");
            validate = false;
        }
        array = clusters.split("\n");
        if (array.length == nc) {
            for (String pair : clusters.split("\n")) {
                if (!useRegex(pair.trim())) {
                    clusterLayout.getEditText().setError("Invalid Input");
                    validate = false;
                }
            }
        } else {
            clusterLayout.getEditText().setError("Enter " + nc + " clusters");
            validate = false;
        }

        if (!validate)
            return;

        KMeans kMeans = new KMeans();
        if (!distance.isEmpty() && distance != "") {
            int index = Arrays.asList((getResources().getStringArray(R.array.kmeans_distance_method))).indexOf(distance);
            kMeans.setDistanceMethod(index);
        }
        kMeans.getInput(np, nc, points, clusters);
        kMeans.computeCluster();
        updateResultIntoTable(resultTable, kMeans);

        resultLayout.setVisibility(View.VISIBLE);
    }

    public void updateResultIntoTable(TableLayout tableLayout, KMeans kMeans) {

        tableLayout.removeAllViews();

        //Table Header
        TableRow headerRow = new TableRow(this);
        headerRow.addView(getHeaderTextView("Cluster"));
        headerRow.addView(getHeaderTextView("( x , y )"));
        tableLayout.addView(headerRow);
        for (int i = 0; i < nc; i++) {
            headerRow.addView(getHeaderTextView("Ed(C" + (i + 1) + ")"));
            headerRow.addView(getHeaderTextView("C" + (i + 1) + "( x , y )"));
        }
        //Table Value
        for (int i = 0; i < np; i++) {
            TableRow row = new TableRow(this);
            row.addView(getTextView("C" + (kMeans.cluster[i] + 1)));
            row.addView(getTextView("( " + kMeans.points[i].x + " , " + kMeans.points[i].y + " )"));
            for (int j = 0; j < nc; j++) {
                row.addView(getTextView(String.valueOf(kMeans.distance[i][j])));
                row.addView(getTextView("( " + kMeans.centroids[i][j].x + " , " + kMeans.centroids[i][j].y + " )"));
            }
            tableLayout.addView(row);
        }
    }

    public TextView getHeaderTextView(String strValue) {
        TextView textView = new TextView(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        params.setMargins(5, 5, 5, 5);
        textView.setLayoutParams(params);
        textView.setText(strValue);
        textView.setBackgroundColor(getColor(R.color.lightWhite));
        textView.setTypeface(ResourcesCompat.getFont(this, R.font.muli_bold));
        textView.setTextColor(getColor(R.color.colorAccent));
        return textView;
    }

    public TextView getTextView(String strValue) {
        TextView textView = new TextView(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        params.setMargins(5, 5, 5, 5);
        textView.setLayoutParams(params);
        textView.setText(strValue);
        textView.setTypeface(ResourcesCompat.getFont(this, R.font.muli));
        textView.setTextColor(getColor(R.color.colorAccent));
        return textView;
    }

    public boolean useRegex(String input) {
        // Compile regular expression
        Pattern pattern = Pattern.compile("\\s*[-+]?\\d+(.\\d+)?\\s*,\\s*[-+]?\\d+(.\\d+)?\\s*", Pattern.MULTILINE);
        // Match regex against input
        Matcher matcher = pattern.matcher(input);
        // Use results...
        return matcher.matches();
    }

}