package com.pskloud.osm;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

import com.pskloud.osm.service.CustomersService;
import com.pskloud.osm.util.DialogHelper;
import com.pskloud.osm.util.Functions;

public class SettingsActivity extends DefaultActivity {

    private Switch mSvCustomer;
    private Switch mSvConnection;
    private Switch mSvLocality;
    private CoordinatorLayout mClView;

    public static void show(final Context context){
        context.startActivity(new Intent(context, SettingsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_done:
                if(mSvConnection.isChecked()) {
                    if(!Functions.checkInternetConnection(this)){
                        showSnackBar(mClView, R.string.check_internet_connection);
                    }else{
                        if (mSvCustomer.isChecked()) {

                            DialogInterface.OnClickListener onClickListener =(dialogInterface, i) -> {
                                if(!CustomersService.isRunning(this)) {
                                    startService(new Intent(this, CustomersService.class));
                                }else
                                    showSnackBar(mClView, R.string.error_service_running);
                            };
                            DialogHelper.confirm(this, R.string.content_sync, onClickListener);
                        }
                    }
                }else
                    showSnackBar(mClView, R.string.check_connection);
            break;
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void init() {
        mSvCustomer = (Switch)findViewById(R.id.sv_customer);
        mSvConnection = (Switch)findViewById(R.id.sv_connection);
        mSvLocality = (Switch)findViewById(R.id.sv_locality);
        mClView = (CoordinatorLayout) findViewById(R.id.cl_view);
    }

    @Override
    public void setUp() {

        mSvConnection.setOnCheckedChangeListener((compoundButton, b) -> {
            Functions.setStatus(this, b);
        });
        mSvConnection.setChecked(Functions.getStatus(this));

        if (Build.VERSION.SDK_INT >= 23) {
            int internetPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.INTERNET);
            if(internetPermission != PackageManager.PERMISSION_GRANTED){

            }
        }
    }
}