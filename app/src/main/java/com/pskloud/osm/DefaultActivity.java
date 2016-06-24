package com.pskloud.osm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.pskloud.osm.util.DialogHelper;

/**
 * Created by Mendez Fernandez on 07/06/2016.
 */
public abstract class DefaultActivity extends AppCompatActivity {

    public static final String INTENT_ACTION_NETWORK = "com.pskloud.osm.intent.INTENT_ACTION_NETWORK";
    public static final String INTENT_ACTION_SERVICES_UNAVAILABLE = "com.pskloud.osm.intent.INTENT_ACTION_SERVICES_UNAVAILABLE";

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(INTENT_ACTION_NETWORK)){
                DialogHelper.ok(DefaultActivity.this, R.string.error_server_unavailable);
            }else if(intent.getAction().equals(INTENT_ACTION_SERVICES_UNAVAILABLE)){
                DialogHelper.ok(DefaultActivity.this, R.string.error_service_unavailable);
            }
        }
    };

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(INTENT_ACTION_NETWORK));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(INTENT_ACTION_SERVICES_UNAVAILABLE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    protected abstract void init();

    protected abstract void setUp();

    protected void showSnackBar(View view, int message) {
        Snackbar snackbar = Snackbar.make(view, getString(message), Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    protected void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
