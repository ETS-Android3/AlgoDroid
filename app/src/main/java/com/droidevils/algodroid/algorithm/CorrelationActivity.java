package com.droidevils.algodroid.algorithm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.droidevils.algodroid.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CorrelationActivity extends AppCompatActivity {

    LinearLayout resultLayout;
    TextView correlationTextView;
    TextInputLayout inputX, inputY;
    Button computeButton;
    TableLayout resultTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correlation);

        resultLayout = findViewById(R.id.result_layout);
        resultTable = findViewById(R.id.correlation_result_table);
        inputX = findViewById(R.id.correlation_x_input);
        inputY = findViewById(R.id.correlation_y_input);
        correlationTextView = findViewById(R.id.correlation_value);
        computeButton = findViewById(R.id.correlation_compute);
    }

    public void onCorrelationCompute(View view) {
        String inputXString = inputX.getEditText().getText().toString().trim();
        String inputYString = inputY.getEditText().getText().toString().trim();
        int n1, n2;

        //Validate
        boolean validate = true;
        if (!useRegex(inputXString)) {
            inputX.getEditText().setError("Invalid Input");
            validate = false;
        }
        if (!useRegex(inputYString)) {
            inputY.getEditText().setError("Invalid Input");
            validate = false;
        }

        if (!validate) {
            return;
        }

        String[] inputXArray = inputXString.trim().split(",");
        String[] inputYArray = inputYString.trim().split(",");
        n1 = inputXArray.length;
        n2 = inputYArray.length;

        if (n1 != n2) {
            Toast.makeText(getApplicationContext(), "Invalid number of inputs", Toast.LENGTH_SHORT).show();
            return;
        }

        //Pre processing
        double[] inputXValue = new double[n1];
        double[] inputYValue = new double[n2];
        int i = 0;
        for (String str : inputXArray)
            inputXValue[i++] = (double) Double.parseDouble(str);
        i = 0;
        for (String str : inputYArray)
            inputYValue[i++] = (double) Double.parseDouble(str);

        Correlation correlation = new Correlation(inputXValue, inputYValue, n1);
        correlation.correlationCoefficient();

        updateResultIntoTable(resultTable, correlation);
        resultLayout.setVisibility(View.VISIBLE);
    }

    public void updateResultIntoTable(TableLayout tableLayout,  Correlation correlation) {

        tableLayout.removeAllViews();

        int num = correlation.getN();

        //Table Header
        TableRow headerRow = new TableRow(this);
        headerRow.addView(getHeaderTextView("X"));
        headerRow.addView(getHeaderTextView("Y"));
        headerRow.addView(getHeaderTextView("X^2"));
        headerRow.addView(getHeaderTextView("Y^2"));
        headerRow.addView(getHeaderTextView("X*Y"));
        tableLayout.addView(headerRow);
        //Table Value
        for (int i = 0; i < num; i++) {
            TableRow row = new TableRow(this);
            row.addView(getTextView(String.valueOf(correlation.getX()[i])));
            row.addView(getTextView(String.valueOf(correlation.getY()[i])));
            row.addView(getTextView(String.valueOf(correlation.getX_square()[i])));
            row.addView(getTextView(String.valueOf(correlation.getY_square()[i])));
            row.addView(getTextView(String.valueOf(correlation.getXY()[i])));
            tableLayout.addView(row);
        }
        TableRow row = new TableRow(this);
        row.addView(getHeaderTextView(String.valueOf(correlation.getSum_X())));
        row.addView(getHeaderTextView(String.valueOf(correlation.getSum_Y())));
        row.addView(getHeaderTextView(String.valueOf(correlation.getSquareSum_X())));
        row.addView(getHeaderTextView(String.valueOf(correlation.getSquareSum_Y())));
        row.addView(getHeaderTextView(String.valueOf(correlation.getSum_XY())));
        tableLayout.addView(row);

        correlationTextView.setText("Correlation (r): "+String.format("%.4f",correlation.getCorr()));
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