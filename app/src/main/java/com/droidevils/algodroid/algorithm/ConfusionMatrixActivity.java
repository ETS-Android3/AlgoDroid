package com.droidevils.algodroid.algorithm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.droidevils.algodroid.R;
import com.google.android.material.textfield.TextInputLayout;

public class ConfusionMatrixActivity extends AppCompatActivity {

    LinearLayout resultLayout;
    TextInputLayout tpLayout, fpLayout, fnLayout, tnLayout;
    Button computeButton;
    TextView sensitivity, specificity, precision, negativePredictive, accuracy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confusion_matrix);

        //Map UI Components
        tpLayout = findViewById(R.id.tp_layout);
        fpLayout = findViewById(R.id.fp_layout);
        fnLayout = findViewById(R.id.fn_layout);
        tnLayout = findViewById(R.id.tn_layout);
        computeButton = findViewById(R.id.confusion_matrix_compute);
        resultLayout = findViewById(R.id.result_layout);
        sensitivity = findViewById(R.id.sensitivity_value);
        specificity = findViewById(R.id.specificity_value);
        precision = findViewById(R.id.precision_value);
        negativePredictive = findViewById(R.id.negative_predictive_value);
        accuracy = findViewById(R.id.accuracy_value);
    }

    public void onConfusionMatrixCompute(View view){

        String tpValue = tpLayout.getEditText().getText().toString().trim();
        String fpValue = fpLayout.getEditText().getText().toString().trim();
        String fnValue = fnLayout.getEditText().getText().toString().trim();
        String tnValue = tnLayout.getEditText().getText().toString().trim();

        boolean validate = true;
        if (tpValue.isEmpty()){
            tpLayout.getEditText().setError("TP Cannot be empty");
            validate = false;
        }
        if (fpValue.isEmpty()){
            fpLayout.getEditText().setError("FP Cannot be empty");
            validate = false;
        }
        if (fnValue.isEmpty()){
            fnLayout.getEditText().setError("FN Cannot be empty");
            validate = false;
        }
        if (tnValue.isEmpty()){
            tnLayout.getEditText().setError("TN Cannot be empty");
            validate = false;
        }
        if (!validate)
            return;

        ConfusionMatrix confusionMatrix = new ConfusionMatrix(Double.valueOf(tpValue), Double.valueOf(fpValue), Double.valueOf(fnValue), Double.valueOf(tnValue));
        confusionMatrix.computeConfusionMatrix();
        sensitivity.setText(confusionMatrix.formatValue(confusionMatrix.getSensitivity()));
        specificity.setText(confusionMatrix.formatValue(confusionMatrix.getSpecificity()));
        precision.setText(confusionMatrix.formatValue(confusionMatrix.getPrecision()));
        negativePredictive.setText(confusionMatrix.formatValue(confusionMatrix.getNegativePredictive()));
        accuracy.setText(confusionMatrix.formatValue(confusionMatrix.getAccuracy()));

        resultLayout.setVisibility(View.VISIBLE);
    }
}