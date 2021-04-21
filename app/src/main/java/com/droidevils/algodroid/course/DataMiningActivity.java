package com.droidevils.algodroid.course;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.droidevils.algodroid.R;
import com.droidevils.algodroid.algorithm.ConfusionMatrixActivity;
import com.droidevils.algodroid.algorithm.FivePointSummaryActivity;
import com.droidevils.algodroid.algorithm.KMeanClusterActivity;
import com.droidevils.algodroid.algorithm.ZScoreActivity;

public class DataMiningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_mining);
    }

    public void callFPSActivity(View view){
        Intent intent = new Intent(getApplicationContext(), FivePointSummaryActivity.class);
        startActivity(intent);
    }

    public void callZScoreActivity(View view){
        Intent intent = new Intent(getApplicationContext(), ZScoreActivity.class);
        startActivity(intent);
    }

    public void callConfusionMatrixActivity(View view){
        Intent intent = new Intent(getApplicationContext(), ConfusionMatrixActivity.class);
        startActivity(intent);
    }

    public void callKMeansClusterActivity(View view){
        Intent intent = new Intent(getApplicationContext(), KMeanClusterActivity.class);
        startActivity(intent);
    }

}