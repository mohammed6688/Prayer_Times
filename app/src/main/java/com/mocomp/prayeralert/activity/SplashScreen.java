package com.mocomp.prayeralert.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.mocomp.prayeralert.R;
import com.mocomp.prayeralert.dal.ServerDAO;
import com.mocomp.prayeralert.interfaces.Dao;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new ServerDAO();

//        int result =serverDAO.getData("https://api.aladhan.com/v1/timingsByCity?city=egypt&country=cairo&method=8");
//
//        Log.e("return",String.valueOf(result));
//        if (result==200){
//            redirect();
//        }else {
////            close();
//        }

//        int SPLASH_DISPLAY_LENGTH = 2000;
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
//                SplashScreen.this.startActivity(mainIntent);
//                SplashScreen.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new AsyncCaller().execute();

    }

    private class AsyncCaller extends AsyncTask<Void, Void, Integer>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(Void... params) {
           ServerDAO.serverDAO.getData("https://api.aladhan.com/v1/timingsByCity?city=egypt&country=cairo&method=8");
            while (ServerDAO.serverDAO.statusCode==0){}
            return ServerDAO.serverDAO.statusCode;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result==200){
                Log.e("out",ServerDAO.serverDAO.data.toString());
                redirect();
            }else {
                SplashScreen.this.finish();
            }

        }

    }
    private void redirect() {
        Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
        SplashScreen.this.startActivity(mainIntent);
        SplashScreen.this.finish();
    }
}
