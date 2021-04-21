package com.droidevils.algodroid.algorithm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.droidevils.algodroid.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FivePointSummaryActivity extends AppCompatActivity {

    LinearLayout resultLayout;
    TextInputLayout input;
    Button computeButton;
    TextView sortedArray, min, q1, median, q3, max, arrayLength, range, iqr, lowerFence, upperFence, outliers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_point_summary);

        //Mapping UI Components
        resultLayout = findViewById(R.id.result_layout);
        input = findViewById(R.id.fps_input);
        computeButton = findViewById(R.id.fps_compute);
        sortedArray = findViewById(R.id.fps_sorted_array);
        min = findViewById(R.id.fps_minimum_value);
        q1 = findViewById(R.id.fps_first_quartile_value);
        median = findViewById(R.id.fps_median_value);
        q3 = findViewById(R.id.fps_third_quartile_value);
        max = findViewById(R.id.fps_maximum_value);
        arrayLength = findViewById(R.id.fps_array_length_value);
        range = findViewById(R.id.fps_range_value);
        iqr = findViewById(R.id.fps_iqr_value);
        lowerFence = findViewById(R.id.fps_lower_fence_value);
        upperFence = findViewById(R.id.fps_upper_fence_value);
        outliers = findViewById(R.id.fps_outlier_value);

    }

    public void onFPSCompute(View view) {
        String inputValue = input.getEditText().getText().toString().trim();

        //Validate
        if (!useRegex(inputValue)) {
            input.getEditText().setError("Invalid Input");
            return;
        }

        ArrayList<Double> inputArray = new ArrayList<Double>();
        for (String str : inputValue.trim().split(","))
            inputArray.add(Double.valueOf(str));

        FivePointSummary fps = new FivePointSummary(inputArray);
        fps.fiveNumberSummary();
        fps.findOutliers();

        updateResultIntoTable(fps);
        resultLayout.setVisibility(View.VISIBLE);
    }

    public boolean useRegex(String input) {
        // Compile regular expression
        Pattern pattern = Pattern.compile("^\\s*[-+]?\\d+(.\\d+)?(\\s*,\\s*[-+]?\\d+(.\\d+)?)*\\s*$");
        // Match regex against input
        Matcher matcher = pattern.matcher(input);
        // Use results...
        return matcher.matches();
    }

    public void updateResultIntoTable(FivePointSummary fps) {
        sortedArray.setText(String.valueOf(fps.getData().toString()));
        min.setText(String.valueOf(fps.getMin()));
        q1.setText(String.valueOf(fps.getFirstQuartile()));
        median.setText(String.valueOf(fps.getMedian()));
        q3.setText(String.valueOf(fps.getThirdQuartile()));
        max.setText(String.valueOf(fps.getMax()));
        arrayLength.setText(String.valueOf(fps.getData().size()));
        range.setText(String.valueOf(fps.getRange()));
        iqr.setText(String.valueOf(fps.getIQR()));
        lowerFence.setText(String.valueOf(fps.getLowerFence()));
        upperFence.setText(String.valueOf(fps.getUpperFence()));
        outliers.setText(String.valueOf(fps.getOutlierList().toString()));
    }

}