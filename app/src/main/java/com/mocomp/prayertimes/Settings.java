package com.mocomp.prayertimes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

import com.mocomp.prayertimes.setting.settingadapter;
import com.mocomp.prayertimes.setting.settings;

import java.util.ArrayList;
import java.util.Locale;

import static com.mocomp.prayertimes.Theme.PREFS_NAME;

public class Settings extends AppCompatActivity {

    private Switch myswitch,timeFormat;
    Spinner methodSpinner;
    LinearLayout languageLay;
    String[] colors;
    public static final String PREFS_NAME = "MyPrefsFile";
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //to adjust the style when the app started
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        //"theme" is the same key 0 is the default value
        int theme = settings.getInt("theme", 0);

        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if(AppController.getPrefranceData("lang").equals("ar")){
            Locale locale2 = new Locale("ar");
            Locale.setDefault(locale2);

            Configuration config2 = new Configuration();
            config2.locale = locale2;

            getBaseContext().getResources().updateConfiguration(
                    config2,getBaseContext().getResources().getDisplayMetrics());
        }else if (AppController.getPrefranceData("lang").equals("en")){
            Locale locale2 = new Locale("en");
            Locale.setDefault(locale2);

            Configuration config2 = new Configuration();
            config2.locale = locale2;

            getBaseContext().getResources().updateConfiguration(
                    config2,getBaseContext().getResources().getDisplayMetrics());
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.setting));


        languageLay = findViewById(R.id.languageLay);
        myswitch = findViewById(R.id.switche);
        timeFormat = findViewById(R.id.timeFormat);
        methodSpinner = findViewById(R.id.spinner1);

        if (theme ==  R.style.NightTheme){
            myswitch.setChecked(true);
        }
        if (AppController.getPrefranceDataBoolean("12")){
            timeFormat.setChecked(true);
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

        timeFormat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppController.setPreferencesBoolean("12",true);
                }
                else {
                    AppController.setPreferencesBoolean("12",false);
                }
                restartapp();
            }
        });


        String[] itemsAr = new String[]{"الشيعة إثنا الأنصاري", "جامعة العلوم الإسلامية بكراتشي", "الجمعية الإسلامية لأمريكا الشمالية",
        "رابطة العالم الإسلامي","جامعة أم القرى بمكة المكرمة","الهيئة المصرية العامة للمساحة","معهد الجيوفيزياء بجامعة طهران",
                "منطقة الخليج","الكويت","دولة قطر","مجلس أوجاما إسلام سينجابورا ، سنغافورة","منظمة الاتحاد الإسلامية الفرنسية",
                "ديانت İşleri Başkanlığı ، تركيا","الإدارة الروحية لمسلمي روسيا"};

        String[] items = new String[]{"Shia Ithna-Ansari", "University of Islamic Sciences, Karachi", "Islamic Society of North America",
                "Muslim World League","Umm Al-Qura University, Makkah","Egyptian General Authority of Survey","Institute of Geophysics, University of Tehran",
                "Gulf Region","Kuwait","Qatar","Majlis Ugama Islam Singapura, Singapore","Union Organization islamic de France",
                "Diyanet İşleri Başkanlığı, Turkey","Spiritual Administration of Muslims of Russia"};

        if(AppController.getPrefranceData("lang").equals("ar")){
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsAr);
        }else {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        }

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

       languageLay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(AppController.getPrefranceData("lang").equals("ar")){
                   colors = new String[]{"العربية", "الانجليزية"};
               }else if (AppController.getPrefranceData("lang").equals("en")){
                   colors = new String[]{"Arabic", "English"};
               }else {
                   colors = new String[]{"Arabic", "English"};
               }

               AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
               builder.setTitle(getResources().getString(R.string.choose_lang));
               builder.setItems(colors, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       // the user clicked on colors[which]
                       if (which==0){
                           AppController.setPreferences("lang","ar");
                           Intent i = getBaseContext().getPackageManager().
                                   getLaunchIntentForPackage(getBaseContext().getPackageName());
                           i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           startActivity(i);
                       }else {
                           AppController.setPreferences("lang","en");
                           Intent i = getBaseContext().getPackageManager().
                                   getLaunchIntentForPackage(getBaseContext().getPackageName());
                           i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           startActivity(i);
                       }
                       finish();
                   }
               });
               builder.show();

           }
       });
    }
    public void restartapp() {
        Intent i = new Intent(Settings.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
