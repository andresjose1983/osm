package com.pskloud.osm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pskloud.osm.adapter.CustomerAdapter;
import com.pskloud.osm.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomersActivity extends DefaultActivity {

    private FloatingActionButton mFabAdd;
    private RecyclerView mRvCustomers;
    private CustomerAdapter customerAdapter;

    public static void show(Context context){
        context.startActivity(new Intent(context, CustomersActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

    }

    @Override
    public void init() {
        mFabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        mRvCustomers = (RecyclerView)findViewById(R.id.rv_customers);
    }

    @Override
    public void setUp() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            customers.add(new Customer("V-16119275","Andres Mendez","1","1","1"));
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRvCustomers.setLayoutManager(mLayoutManager);
        mRvCustomers.setItemAnimator(new DefaultItemAnimator());

        customerAdapter = new CustomerAdapter(customers);
        mRvCustomers.setAdapter(customerAdapter);
        mRvCustomers.setHasFixedSize(true);

    }
}
