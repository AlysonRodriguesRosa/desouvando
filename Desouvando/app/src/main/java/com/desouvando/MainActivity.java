package com.desouvando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mp;
    private SeekBar seekBar;
    private Handler runHandler = new Handler();

    private Button play_buttton;

    private double startTime = 0;
    private double finalTime = 0;

    private boolean is_playing = false;
    private boolean is_paused = false;

    public int NUMBER_SONGS = 2;
    private int[] songs = {R.raw.senta,
                           R.raw.manivela};
    // Overrides

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setClickable(false);
        play_buttton = findViewById(R.id.play_button);

        SharedPreferences achiev_pref = getSharedPreferences("Achiev", MODE_PRIVATE);
        String cont = "Voce ouviu: " + achiev_pref.getInt("number_of_plays", 0);
        Toast.makeText(getApplicationContext(), cont, Toast.LENGTH_SHORT).show();

        mp = MediaPlayer.create(this, R.raw.senta);
    }

    @Override
    protected void onResume() {
        super.onResume();

        play_buttton.setBackgroundResource(R.drawable.play);
        is_playing = false;
        is_paused = false;
    }

    @Override
    protected void onStart(){
        super.onStart();

        play_buttton.setBackgroundResource(R.drawable.play);
        is_playing = false;
        is_paused = false;
    }

    @Override
    protected void onStop(){
        super.onStop();

        mp.stop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        mp.stop();
    }

    // Public Functions

    public void call_sobre_activity(View view){
        if (is_playing) {
            mp.stop();
        }
        startActivity(new Intent(MainActivity.this, SobreActivity.class));
    }
    public void call_achiev_activity(View view){
        if (is_playing) {
            mp.stop();
        }
        startActivity(new Intent(MainActivity.this, Achievements.class));
    }

    public int get_random(){
        Random generator = new Random();
        return generator.nextInt(NUMBER_SONGS);
    }

    public void play(View view){
        if(is_paused) {
            mp.start();
            is_paused = false;
            play_buttton.setBackgroundResource(R.drawable.next);
        } else {
            if (is_playing){
                mp.stop();
            }

            int rand = get_random();

            mp = MediaPlayer.create(this, songs[rand]);
            finalTime = mp.getDuration();
            mp.start();
            seekBar.setMax((int) finalTime);
            seekBar.setProgress((int) startTime);
            runHandler.postDelayed(TrackSongTime, 100);

            play_buttton.setBackgroundResource(R.drawable.next);

            is_playing = true;
        }
    }

    public void pause(View view){
        if(is_playing) {
            mp.pause();
            play_buttton.setBackgroundResource(R.drawable.play);

            is_paused = true;
        }
    }

    public void UpdateAchievementList_SongPlayed(){
        int cont;
        String score_text;

        SharedPreferences achiev_pref = getSharedPreferences("Achiev", MODE_PRIVATE);
        SharedPreferences.Editor editor = achiev_pref.edit();

        cont = achiev_pref.getInt("number_of_plays", 0);
        cont += 1;
        editor.putInt("number_of_plays", cont);
        editor.apply();

        // TODO: remove later
        score_text = "Voce desouviu " + cont + " musicas!";
        Toast.makeText(getApplicationContext(), score_text,
                Toast.LENGTH_SHORT).show();

        apply_achievement();
    }

    public void apply_achievement(){
        int cont;

        SharedPreferences achiev_pref = getSharedPreferences("Achiev", MODE_PRIVATE);
        SharedPreferences.Editor editor = achiev_pref.edit();

        cont = achiev_pref.getInt("number_of_plays", 0);

        switch(cont){
            case 1:
                Toast.makeText(getApplicationContext(), "Achievement Unlocked! Nivel 1",
                        Toast.LENGTH_SHORT).show();
                editor.putBoolean("achiev_1", true);
                editor.apply();
                break;
            case 10:
                Toast.makeText(getApplicationContext(), "Achievement Unlocked! Nivel 2",
                        Toast.LENGTH_SHORT).show();
                editor.putBoolean("achiev_2", true);
                editor.apply();
                break;
            case 100:
                Toast.makeText(getApplicationContext(), "Achievement Unlocked! Nivel 3",
                        Toast.LENGTH_SHORT).show();
                editor.putBoolean("achiev_3", true);
                editor.apply();
                break;
            case 500:
                Toast.makeText(getApplicationContext(), "Achievement Unlocked! Nivel 4",
                        Toast.LENGTH_SHORT).show();
                editor.putBoolean("achiev_4", true);
                editor.apply();
                break;
            case 1000:
                Toast.makeText(getApplicationContext(), "Achievement Unlocked! Nivel 5",
                        Toast.LENGTH_SHORT).show();
                editor.putBoolean("achiev_5", true);
                editor.apply();
                break;
        }
    }

    private Runnable TrackSongTime = new Runnable() {
        @Override
        public void run() {
            startTime = mp.getCurrentPosition();
            seekBar.setProgress((int) startTime);

            if (startTime >= mp.getDuration()) {
                is_playing = false;
                play_buttton.setBackgroundResource(R.drawable.play);
                // music fully listened, add 1 count to achievement score
                UpdateAchievementList_SongPlayed();
            } else {
                runHandler.postDelayed(this, 100);
            }
        }
    };
}
