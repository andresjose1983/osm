package com.osm.soft.sf;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.osm.soft.sf.adapter.CustomerAdapter;
import com.osm.soft.sf.adapter.OrderAdapter;
import com.osm.soft.sf.model.Customer;
import com.osm.soft.sf.model.Order;
import com.osm.soft.sf.util.DialogHelper;
import com.osm.soft.sf.util.OrderSqlHelper;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends DefaultActivity implements SearchView.OnQueryTextListener {

    private RecyclerView mRvOrders;
    private OrderAdapter mOrderAdapter;
    private CoordinatorLayout mClOrder;
    private OrderSqlHelper orderSqlHelper;
    private MenuItem mMenu;
    private SearchView mSearchView;
    private List<Order> mOrders;

    public static void show(DefaultActivity defaultActivity){
        defaultActivity.startActivity(new Intent(defaultActivity, OrdersActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filters, menu);
        mMenu = menu.findItem(R.id.action_search);
        // Associate searchable configuration with the SearchView
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mOrders = orderSqlHelper.GET_ALL.execute();
            reset(mOrders);

            if(mSearchView != null) {
                mSearchView.clearFocus();
                mSearchView.setQuery("", false);

                //Collapse the action view
                mSearchView.onActionViewCollapsed();
                if(mMenu != null)
                    mMenu.collapseActionView();
            }
        }catch (Exception e){
            if(BuildConfig.DEBUG)
                Log.e(OrdersActivity.class.getCanonicalName(), e.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        List<Order> orders = new ArrayList<>();
        switch (item.getItemId()){
            case R.id.action_filter_all:
                reset(mOrders);
                break;
            case R.id.action_filter_synced:
                orders.clear();
                for (Order order : mOrders) {
                    if(order.isSync())
                        orders.add(order);
                }
                reset(orders);
                break;
            case R.id.action_filter_unsynced:
                orders.clear();
                for (Order order : mOrders) {
                    if(!order.isSync())
                        orders.add(order);
                }
                reset(orders);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setUp() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRvOrders.setLayoutManager(mLayoutManager);
        mRvOrders.setItemAnimator(new DefaultItemAnimator());
        findViewById(R.id.fab_add).setOnClickListener(view -> CustomersActivity.show(this));
        swipe();

        ActionBar supportActionBar = getSupportActionBar();
        if(supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void init() {
        mRvOrders = (RecyclerView) findViewById(R.id.rv_orders);
        mClOrder = (CoordinatorLayout) findViewById(R.id.cl_order);

        orderSqlHelper = new OrderSqlHelper(this);
    }

    private void reset(final List<Order> orders){
        mOrderAdapter = new OrderAdapter(this, orders, true);
        mRvOrders.setAdapter(mOrderAdapter);
    }

    private void swipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        Order order = mOrderAdapter.get(viewHolder.getLayoutPosition());
                        if(!order.isSync())
                            DialogHelper.confirm(OrdersActivity.this, R.string.message_delete_order,
                                    (dialogInterface, i) -> {
                                        if(order != null){
                                            if(orderSqlHelper.DELETE.execute(order)) {
                                                mOrderAdapter.remove(viewHolder.getLayoutPosition());
                                                showSnackBar(mClOrder, R.string.order_deleted);
                                            }
                                        }
                                    },
                                    (dialogInterface1, i1) -> mOrderAdapter.notifyDataSetChanged()
                            );
                        else{
                            mOrderAdapter.notifyDataSetChanged();
                            showSnackBar(mClOrder, R.string.order_synced);
                        }
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRvOrders);
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
        mOrderAdapter.getFilter().filter(query.toUpperCase());
    }
}
