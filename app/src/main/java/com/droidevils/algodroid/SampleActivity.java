package com.droidevils.algodroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SampleActivity extends AppCompatActivity {

    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        tableLayout = findViewById(R.id.sample_table);

        TableRow tableRow = new TableRow(this);
        tableRow.addView(getTextView("Abhishek"));
        tableRow.addView(getTextView("Bleh Bleh"));
        tableRow.addView(getTextView("Bleh Bleh"));
        tableLayout.addView(tableRow);
    }

    public TextView getTextView(String strValue){
        TextView textView = new TextView(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        params.setMargins(5,5,5,5);
        textView.setLayoutParams(params);
        textView.setText(strValue);
        textView.setTextColor(getColor(R.color.colorAccent));
        return textView;
    }
}