package com.droidevils.algodroid.algorithm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.droidevils.algodroid.R;
import com.droidevils.algodroid.utility.NumberFormat;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegressionActivity extends AppCompatActivity {

    LinearLayout resultLayout;
    TextView regressionEquation, xMean, yMean;
    TextInputLayout inputX, inputY;
    Button computeButton;
    TableLayout resultTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regression);

        resultLayout = findViewById(R.id.result_layout);
        resultTable = findViewById(R.id.regression_result_table);
        inputX = findViewById(R.id.regression_x_input);
        inputY = findViewById(R.id.regression_y_input);
        regressionEquation = findViewById(R.id.regression_value);
        xMean = findViewById(R.id.regression_x_mean);
        yMean = findViewById(R.id.regression_y_mean);
        computeButton = findViewById(R.id.regression_compute);
    }

    public void onRegressionCompute(View view) {
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
            inputXValue[i++] = Double.parseDouble(str);
        i = 0;
        for (String str : inputYArray)
            inputYValue[i++] = Double.parseDouble(str);

        Regression regression = new Regression(inputXValue, inputYValue, n1);
        regression.computeRegression();

        Correlation correlation = new Correlation(inputXValue, inputYValue, n1);
        correlation.correlationCoefficient();

        updateResultIntoTable(resultTable, regression);
        resultLayout.setVisibility(View.VISIBLE);
    }

    public void updateResultIntoTable(TableLayout tableLayout, Regression regression) {

        tableLayout.removeAllViews();

        int num = regression.getN();

        //Table Header
        TableRow headerRow = new TableRow(this);
        headerRow.addView(getHeaderTextView("X"));
        headerRow.addView(getHeaderTextView("Y"));
        headerRow.addView(getHeaderTextView("X-Xm"));
        headerRow.addView(getHeaderTextView("Y-Ym"));
        headerRow.addView(getHeaderTextView("Xm.Ym"));
        headerRow.addView(getHeaderTextView("Xm^2"));
        tableLayout.addView(headerRow);
        //Table Value
        for (int i = 0; i < num; i++) {
            TableRow row = new TableRow(this);
            row.addView(getTextView(String.valueOf(NumberFormat.roundDecimal(regression.getX()[i]))));
            row.addView(getTextView(String.valueOf(NumberFormat.roundDecimal(regression.getY()[i]))));
            row.addView(getTextView(String.valueOf(NumberFormat.roundDecimal(regression.getXm()[i]))));
            row.addView(getTextView(String.valueOf(NumberFormat.roundDecimal(regression.getYm()[i]))));
            row.addView(getTextView(String.valueOf(NumberFormat.roundDecimal(regression.getXm_Ym()[i]))));
            row.addView(getTextView(String.valueOf(NumberFormat.roundDecimal(regression.getXm_2()[i]))));
            tableLayout.addView(row);
        }
        TableRow row = new TableRow(this);
        row.addView(getHeaderTextView(String.valueOf(NumberFormat.roundDecimal(regression.getX_sum()))));
        row.addView(getHeaderTextView(String.valueOf(NumberFormat.roundDecimal(regression.getY_sum()))));
        row.addView(getHeaderTextView(""));
        row.addView(getHeaderTextView(""));
        row.addView(getHeaderTextView(String.valueOf(NumberFormat.roundDecimal(regression.getXm_ym_sum()))));
        row.addView(getHeaderTextView(String.valueOf(NumberFormat.roundDecimal(regression.getXm_2_sum()))));
        tableLayout.addView(row);

        regressionEquation.setText(String.format("Regression Equation: %s", regression.getEquation()));
        xMean.setText(String.format("x̄ = %s, ȳ = %s", NumberFormat.roundDecimal(regression.getX_mean()), NumberFormat.roundDecimal(regression.getY_mean())));
        yMean.setText(String.format("W0 = %s, W1 = %s", NumberFormat.roundDecimal(regression.getW0()), NumberFormat.roundDecimal(regression.getW1())));
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