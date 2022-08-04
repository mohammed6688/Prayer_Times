package com.mocomp.prayeralert.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.mocomp.prayeralert.R;

public class Theme extends AppCompatActivity {

    private Switch myswitch;
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //to adjust the style when the app started
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        //"theme" is the same key 0 is the default value
        int theme = settings.getInt("theme", 0);

        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        myswitch = findViewById(R.id.switche);

        if (theme ==  R.style.NightTheme){
            myswitch.setChecked(true);
        }
        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    //"theme" is the key sTheme is the value you're saving
                    editor.putInt("theme", R.style.NightTheme);
                    editor.commit();
                    //Application.setPreferencesBoolean("night",true);
                    restartapp();
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    //"theme" is the key sTheme is the value you're saving
                    editor.putInt("theme", R.style.AppTheme);
                    editor.commit();
                    //Application.setPreferencesBoolean("night",false);
                    restartapp();
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Theme");
    }

    public void restartapp() {
        Intent i = new Intent(Theme.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
