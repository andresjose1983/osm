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
import com.pskloud.osm.fragment.DialogOrderFragment;
import com.pskloud.osm.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomersActivity extends DefaultActivity implements SearchView.OnQueryTextListener {

    private FloatingActionButton mFabAdd;
    private RecyclerView mRvCustomers;
    private CustomerAdapter mCustomerAdapter;
    private SearchView mSearchView;
    private DialogOrderFragment bsdFragment;
    public static final String ARG_CUSTOMER = "CUSTOMER";

    List<Customer> mCustomers = new ArrayList<>();

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
        getMenuInflater().inflate(R.menu.customer_menu, menu);
        // Associate searchable configuration with the SearchView
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
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
            mCustomers.add(new Customer(Integer.toOctalString(i), "Andres Mendez", "1", "1", "1"));
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRvCustomers.setLayoutManager(mLayoutManager);
        mRvCustomers.setItemAnimator(new DefaultItemAnimator());

        mCustomerAdapter = new CustomerAdapter(this, mCustomers);
        mRvCustomers.setAdapter(mCustomerAdapter);
        mRvCustomers.setHasFixedSize(true);

        mFabAdd.setOnClickListener(view->goToCustomer(this, null));

        bsdFragment = DialogOrderFragment.newInstance();

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
        mCustomerAdapter.getFilter().filter(query.toLowerCase());
    }

    public static void goToCustomer(final Context context, final Customer customer){
        CustomerActivity.show(context, customer);
    }

    public void showOrder(final Customer customer){
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_CUSTOMER, customer);
        bsdFragment.setArguments(bundle);
        bsdFragment.show(getSupportFragmentManager(), DialogOrderFragment.BOTTON_SHEET_NAME);
    }
}
