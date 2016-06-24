package com.pskloud.osm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Thread.sleep(5000);
            LoginActivity.show(this);
        } catch (InterruptedException e) {
            if(BuildConfig.DEBUG)
                Log.e(SplashActivity.class.getCanonicalName(), e.getMessage());
        }
    }


}
