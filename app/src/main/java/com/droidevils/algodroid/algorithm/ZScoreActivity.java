package com.droidevils.algodroid.algorithm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;

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
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZScoreActivity extends AppCompatActivity {

    LinearLayout resutlLayout;
    TextInputLayout inputValue;
    TextView sortedArray, length, sum, mean, variance, stdDeviation;
    TableLayout zScoreResult;
    Button zScoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_score);

        inputValue = findViewById(R.id.zscore_input);
        sortedArray = findViewById(R.id.zscore_sorted_array);
        length = findViewById(R.id.zscore_lenght_value);
        sum = findViewById(R.id.zscore_sum_value);
        mean = findViewById(R.id.zscore_mean_value);
        variance = findViewById(R.id.zscore_variance_value);
        stdDeviation = findViewById(R.id.zscore_std_dev_value);
        zScoreResult = findViewById(R.id.zscore_result_table);
        resutlLayout = findViewById(R.id.result_layout);
    }

    public void onZScoreCompute(View view) {

        String input = inputValue.getEditText().getText().toString().trim();

        //Valididate
        if (!useRegex(input)) {
            inputValue.getEditText().setError("Invalid Input");
            return;
        }

        ArrayList<Double> inputArray = new ArrayList<Double>();
        for (String str : input.split(","))
            inputArray.add(Double.valueOf(str));

        ZScore zScore = new ZScore(inputArray);
        zScore.computeZScore();

        updateResultIntoTable(zScore);
        resutlLayout.setVisibility(View.VISIBLE);
    }

    private void updateResultIntoTable(ZScore zScore) {
        Collections.sort(zScore.getData());
        sortedArray.setText(String.valueOf(zScore.getData().toString()));
        length.setText(String.valueOf(zScore.getLength()));
        sum.setText(String.valueOf(zScore.getSum()));
        mean.setText(String.valueOf(zScore.getMean()));
        variance.setText(String.valueOf(zScore.getVariance()));
        stdDeviation.setText(String.valueOf(zScore.getSd()));

        zScoreResult.removeAllViews();
        TableRow headerRow = new TableRow(this);
        headerRow.addView(getHeaderTextView("Input"));
        headerRow.addView(getHeaderTextView("Z-Score"));
        zScoreResult.addView(headerRow);
        ArrayList<Double> data = zScore.getData();
        ArrayList<Double> zScoreData = zScore.getZscore();
        for (int i = 0; i < zScore.getLength(); i++) {
            TableRow row = new TableRow(this);
            row.addView(getTextView(String.valueOf(data.get(i))));
            row.addView(getTextView(String.valueOf(zScoreData.get(i))));
            zScoreResult.addView(row);
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
        Pattern pattern = Pattern.compile("^\\s*[-+]?\\d+(.\\d+)?(\\s*,\\s*[-+]?\\d+(.\\d+)?)*\\s*$");
        // Match regex against input
        Matcher matcher = pattern.matcher(input);
        // Use results...
        return matcher.matches();
    }

}