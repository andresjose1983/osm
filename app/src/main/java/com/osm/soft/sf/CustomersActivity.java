package com.osm.soft.sf;

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
import android.view.MenuItem;

import com.osm.soft.sf.adapter.CustomerAdapter;
import com.osm.soft.sf.fragment.DialogOrderFragment;
import com.osm.soft.sf.model.Customer;
import com.osm.soft.sf.util.CustomerSqlHelper;

import java.util.ArrayList;
import java.util.List;

public class CustomersActivity extends DefaultActivity implements SearchView.OnQueryTextListener {

    private FloatingActionButton mFabAdd;
    private RecyclerView mRvCustomers;
    private CustomerAdapter mCustomerAdapter;
    private MenuItem mMenu;
    private SearchView mSearchView;
    private DialogOrderFragment bsdFragment;
    private CustomerSqlHelper customerSqlHelper;
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
        getMenuInflater().inflate(R.menu.menu_customers, menu);
        mMenu = menu.findItem(R.id.action_search);
        // Associate searchable configuration with the SearchView
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        List<Customer> customers = new ArrayList<>();
        switch (item.getItemId()){
            case R.id.action_filter_all:
                reset(mCustomers);
                break;
            case R.id.action_filter_synced:
                customers.clear();
                for (Customer customer : mCustomers) {
                    if(customer.isSync())
                        customers.add(customer);
                }
                reset(customers);
                break;
            case R.id.action_filter_unsynced:
                customers.clear();
                for (Customer customer : mCustomers) {
                    if(!customer.isSync())
                        customers.add(customer);
                }
                reset(customers);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void init() {
        mFabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        mRvCustomers = (RecyclerView) findViewById(R.id.rv_customers);

        customerSqlHelper = new CustomerSqlHelper(this);
        bsdFragment = DialogOrderFragment.newInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCustomers = customerSqlHelper.GET.execute();
        reset(mCustomers);

        if(mSearchView != null) {
            mSearchView.clearFocus();
            mSearchView.setQuery("", false);

            //Collapse the action view
            mSearchView.onActionViewCollapsed();
            if(mMenu != null)
                mMenu.collapseActionView();
        }
    }

    private void reset(final List<Customer> customers){
        mCustomerAdapter = new CustomerAdapter(this, customers);
        mRvCustomers.setAdapter(mCustomerAdapter);
    }

    @Override
    public void setUp() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRvCustomers.setLayoutManager(mLayoutManager);
        mRvCustomers.setItemAnimator(new DefaultItemAnimator());
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
        mCustomerAdapter.getFilter().filter(query.toUpperCase());
    }

    public static void goToCustomer(final Context context, final Customer customer){
        CustomerActivity.show(context, customer);
    }

    public void showOrder(final Customer customer){
        hideKeyboard();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CUSTOMER, customer);
        bsdFragment.setArguments(bundle);
        bsdFragment.show(getSupportFragmentManager(), DialogOrderFragment.BOTTON_SHEET_NAME);
    }
}
