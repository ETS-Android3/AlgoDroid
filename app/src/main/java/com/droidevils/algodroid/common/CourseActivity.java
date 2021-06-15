package com.droidevils.algodroid.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.droidevils.algodroid.R;
import com.droidevils.algodroid.course.DataMiningActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

public class CourseActivity extends AppCompatActivity {

    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }

    public void callDataMiningActivity(View v){
        Intent intent = new Intent(getApplicationContext(), DataMiningActivity.class);
        startActivity(intent);
    }

}