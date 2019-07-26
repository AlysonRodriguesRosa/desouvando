package com.desouvando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Achievements extends AppCompatActivity {

    ListView list;
    int MAX_NUMBER_ACHIEV = 6;

    String[] maintitle = {
            "Desescutou Nivel 1",
            "Desescutou Nivel 2",
            "Desescutou Nivel 3",
            "Desescutou Nivel 4",
            "Desescutou Nivel 5",
            "About the about page",
    };

    String[] subtitle = {
            "Voce substituiu a musica na sua cabeca 1x!",
            "Voce substituiu a musica na sua cabeca 10x! De nada!",
            "Voce substituiu a musica na sua cabeca 100x! Eita!",
            "Voce substituiu a musica na sua cabeca 500x! Melhor procurar um medico!",
            "Voce substituiu a musica na sua cabeca 1000x! Desisto.",
            "Voce leu o Sobre.",
    };

    Integer[] imgid = {
            R.drawable.achiev,
            R.drawable.achiev,
            R.drawable.achiev,
            R.drawable.achiev,
            R.drawable.achiev,
            R.drawable.about,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final Boolean[] completed = new Boolean[MAX_NUMBER_ACHIEV];
        int i;

        SharedPreferences achiev_pref = getSharedPreferences("Achiev", MODE_PRIVATE);

        for(i=0; i<MAX_NUMBER_ACHIEV; i++){
            int next_value = i + 1;
            String achiev_name = "achiev_" + next_value;
            completed[i] = achiev_pref.getBoolean(achiev_name, false);
        }

        MyListAdapter adapter = new MyListAdapter(this, maintitle, subtitle, imgid, completed);
        list = findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                if(completed[position])
                    Toast.makeText(getApplicationContext(),"Achievement Acquired", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Secret", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
