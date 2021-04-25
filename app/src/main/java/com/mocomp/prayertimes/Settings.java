package com.mocomp.prayertimes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

import com.mocomp.prayertimes.setting.settingadapter;
import com.mocomp.prayertimes.setting.settings;

import java.util.ArrayList;

import static com.mocomp.prayertimes.Theme.PREFS_NAME;

public class Settings extends AppCompatActivity {

    private Switch myswitch;
    Spinner methodSpinner;

    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //to adjust the style when the app started
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        //"theme" is the same key 0 is the default value
        int theme = settings.getInt("theme", 0);

        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");


        myswitch = findViewById(R.id.switche);
        methodSpinner = findViewById(R.id.spinner1);

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
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    //"theme" is the key sTheme is the value you're saving
                    editor.putInt("theme", R.style.AppTheme);
                    editor.commit();
                    //Application.setPreferencesBoolean("night",false);
                }
                restartapp();
            }
        });


        String[] items = new String[]{"Shia Ithna-Ansari", "University of Islamic Sciences, Karachi", "Islamic Society of North America",
        "Muslim World League","Umm Al-Qura University, Makkah","Egyptian General Authority of Survey","Institute of Geophysics, University of Tehran",
                "Gulf Region","Kuwait","Qatar","Majlis Ugama Islam Singapura, Singapore","Union Organization islamic de France",
                "Diyanet İşleri Başkanlığı, Turkey","Spiritual Administration of Muslims of Russia"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        methodSpinner.setAdapter(adapter);

        String method =AppController.getPrefranceData("method");
        if (!method.isEmpty())
            methodSpinner.setSelection(Integer.parseInt(method));
        methodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AppController.setPreferences("method",String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*final ArrayList<settings> setting = new ArrayList <settings>();

        setting.add(new settings("Night mode","Theme",R.drawable.ic_brightness_2_black_24dp));
        //setting.add(new settings("Contact us","Questions ?",R.drawable.ic_group_black_24dp));
        //setting.add(new settings("About Hazem","",R.drawable.ic_person_black_24dp));
        //setting.add(new settings("Feedback","send us feedback",R.drawable.ic_feedback_black_24dp));
        //setting.add(new settings("App info","",R.drawable.ic_info_outline_black_24dp));

        settingadapter adapter = new settingadapter(this, setting);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                settings word = setting.get(position);
                if (word.getprimarytext() == "Night mode"){
                    Intent nightmode = new Intent(Settings.this , Theme.class);
                    startActivity(nightmode);
                }
            }
        });*/
    }
    public void restartapp() {
        Intent i = new Intent(Settings.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
