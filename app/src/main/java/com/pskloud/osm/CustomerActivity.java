package com.pskloud.osm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.pskloud.osm.model.Customer;

public class CustomerActivity extends DefaultActivity {

    private static final String CUSTOMER = "com.pskloud.osm.intent.CUSTOMER";

    public static void show(Context context, final Customer customer){
        context.startActivity(new Intent(context, CustomerActivity.class)
                .putExtra(CUSTOMER, customer));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_customer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_customer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void init() {

    }

    @Override
    public void setUp() {

    }
}
