package com.mocomp.prayeralert.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.mocomp.prayeralert.AppController;
import com.mocomp.prayeralert.R;
import com.mocomp.prayeralert.dal.ServerDAO;
import com.mocomp.prayeralert.interfaces.Dao;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    String url="https://api.aladhan.com/v1/timingsByCity?city=egypt&country=cairo&state=cairo&method=8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getMethod();
        new ServerDAO();
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
           ServerDAO.serverDAO.getData(url);
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

    private void getMethod() {
        String method = AppController.getPrefranceData("method");
        if (!method.isEmpty()){
            url="https://api.aladhan.com/v1/timingsByCity?city=egypt&country=cairo&state=cairo&method="+method;
        }
    }

    private void redirect() {
        Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
        SplashScreen.this.startActivity(mainIntent);
        SplashScreen.this.finish();
    }
}
