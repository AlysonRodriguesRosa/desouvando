package com.desouvando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        boolean check;
        SharedPreferences achiev_pref = getSharedPreferences("Achiev", MODE_PRIVATE);
        SharedPreferences.Editor editor = achiev_pref.edit();

        // achiev_6 is about the about page
        check = achiev_pref.getBoolean("achiev_6", false);

        if(!check){
            Toast.makeText(getApplicationContext(), "Achievement Unlocked! About the about page!",
                    Toast.LENGTH_SHORT).show();
            editor.putBoolean("achiev_6", true);
            editor.apply();
        }

    }
}
