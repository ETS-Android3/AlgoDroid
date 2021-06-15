package com.droidevils.algodroid.algorithm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.droidevils.algodroid.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DistanceMatrixActivity extends AppCompatActivity {

    int num;
    LinearLayout resultLayout;
    TableLayout resultTable;
    TextInputLayout pointsLayout, distanceLayout;
    AutoCompleteTextView distanceMethod;
    Button computeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_matrix);

        resultLayout = findViewById(R.id.distance_matrix_result_layout);
        resultTable = findViewById(R.id.distance_matrix_result_table);
        pointsLayout = findViewById(R.id.distance_matrix_points_layout);
        distanceLayout = findViewById(R.id.distance_matrix_method);
        distanceMethod = findViewById(R.id.distance_matrix_method_atv);
        computeButton = findViewById(R.id.distance_matrix_compute);

        ArrayAdapter<String> distanceMethodAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.kmeans_distance_method));
        distanceMethod.setAdapter(distanceMethodAdapter);

    }

    public void OnDistanceMatrixCompute(View view) {

        String points = pointsLayout.getEditText().getText().toString().trim();
        String distance = distanceMethod.getText().toString().trim();

        //Validate
        boolean validate = true;
        String[] array = points.split("\n");
        for (String pair : array) {
            if (!useRegex(pair.trim())) {
                pointsLayout.getEditText().setError("Invalid Input");
                validate = false;
            }
        }

        if (!validate)
            return;

        String[] pointPair = points.split("\n");
        ArrayList<Point> pointsArray = new ArrayList<>();
        for (int i = 0; i < pointPair.length; i++) {
            String[] temp = pointPair[i].split(",");
            double x = Double.parseDouble(temp[0]);
            double y = Double.parseDouble(temp[1]);
            pointsArray.add(new Point(x, y));
        }

        DistanceMatrix distanceMatrix = new DistanceMatrix(pointsArray);
        if (!distance.isEmpty() && distance != "") {
            int index = Arrays.asList((getResources().getStringArray(R.array.kmeans_distance_method))).indexOf(distance);
            distanceMatrix.setDistanceMethod(index);
        }
        distanceMatrix.computeDistanceMatrix();
        updateResultIntoTable(resultTable, distanceMatrix);
        resultLayout.setVisibility(View.VISIBLE);

    }

    public void updateResultIntoTable(TableLayout tableLayout, DistanceMatrix matrix) {

        tableLayout.removeAllViews();

        double[][] distanceMatrixValue = matrix.getDistanceMatrix();
        num = distanceMatrixValue.length;

        //Table Header
        TableRow headerRow = new TableRow(this);
        headerRow.addView(getHeaderTextView("DM"));
        tableLayout.addView(headerRow);
        for (int i = 0; i < num; i++) {
            headerRow.addView(getHeaderTextView("P" + (i + 1)));
        }
        //Table Value
        for (int i = 0; i < num; i++) {
            TableRow row = new TableRow(this);
            row.addView(getHeaderTextView("P" + (i + 1)));
            for (int j = 0; j < num; j++) {
                row.addView(getTextView(String.valueOf(distanceMatrixValue[i][j])));
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