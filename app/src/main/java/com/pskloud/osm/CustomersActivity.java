package com.pskloud.osm;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import com.pskloud.osm.adapter.CustomerAdapter;
import com.pskloud.osm.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomersActivity extends DefaultActivity implements SearchView.OnQueryTextListener {

    private FloatingActionButton mFabAdd;
    private RecyclerView mRvCustomers;
    private CustomerAdapter customerAdapter;
    private SearchView searchView;
    List<Customer> customers = new ArrayList<>();

    public static void show(Context context) {
        context.startActivity(new Intent(context, CustomersActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_customers, menu);
        // Associate searchable configuration with the SearchView
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public void init() {
        mFabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        mRvCustomers = (RecyclerView) findViewById(R.id.rv_customers);
    }

    @Override
    public void setUp() {
        for (int i = 0; i < 1000; i++) {
            customers.add(new Customer(Integer.toOctalString(i), "Andres Mendez", "1", "1", "1"));
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRvCustomers.setLayoutManager(mLayoutManager);
        mRvCustomers.setItemAnimator(new DefaultItemAnimator());

        customerAdapter = new CustomerAdapter(customers);
        mRvCustomers.setAdapter(customerAdapter);
        mRvCustomers.setHasFixedSize(true);

        mFabAdd.setOnClickListener(view->goToCustomer(this, null));

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        filterCustomer(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterCustomer(newText);
        return false;
    }

    private void filterCustomer(String query){
        customerAdapter.getFilter().filter(query.toLowerCase());
    }

    public static void goToCustomer(final Context context, final Customer customer){
        CustomerActivity.show(context, customer);
    }
}
