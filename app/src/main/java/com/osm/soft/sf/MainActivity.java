package com.osm.soft.sf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.osm.soft.sf.util.CustomerSqlHelper;
import com.osm.soft.sf.util.NotificationHelper;
import com.osm.soft.sf.util.OrderSqlHelper;

import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;

import java.text.DecimalFormat;

public class MainActivity extends DefaultActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private NavigationView mNvMain;
    private StackedBarChart mStackedBarChart;
    private StackedBarModel mSGraphCustomer;
    private StackedBarModel mSsGraphOrder;
    private CustomerSqlHelper mCustomerSqlHelper;
    private OrderSqlHelper mOrderSqlHelper;

    public static void show(LoginActivity activity){
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationHelper.close(NotificationHelper.NOTIFICATION_DOWNLOADED_CUSTOMER,
                NotificationHelper.NOTIFICATION_DOWNLOADED_LOCALITY,
                NotificationHelper.NOTIFICATION_DOWNLOADED_TAX_TYPE,
                NotificationHelper.NOTIFICATION_DOWNLOADED_PRODUCT);
        new Handler().post(() -> {
            try{
                mStackedBarChart.clearChart();
                paint(mCustomerSqlHelper.GET_GRAPH.execute(), mSGraphCustomer);
                paint(mOrderSqlHelper.GET_GRAPH.execute(), mSsGraphOrder);
            }catch(Exception e){
                if(BuildConfig.DEBUG)
                    Log.e(MainActivity.class.getCanonicalName(), e.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.nav_customers:
                CustomersActivity.show(this);
                break;
            case R.id.nav_products:
                ProductsActivity.show(this);
                break;
            case R.id.nav_settings:
                SettingsActivity.show(this);
                break;
            case R.id.nav_order:
                OrdersActivity.show(this);
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setUp() {
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNvMain.setNavigationItemSelectedListener(this);

        mSGraphCustomer = new StackedBarModel(getString(R.string.nav_customers));
        mSsGraphOrder = new StackedBarModel(getString(R.string.nav_orders));
        mCustomerSqlHelper = new CustomerSqlHelper(this);
        mOrderSqlHelper = new OrderSqlHelper(this);
    }

    @Override
    public void init() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        mNvMain = (NavigationView)findViewById(R.id.nav_view);
        mStackedBarChart = (StackedBarChart) findViewById(R.id.sbc_view);
    }


    private void paint(Integer[] graphs, StackedBarModel sGraph){
        if(graphs != null){
            float updated = 0.0f;
            float notUpdated = 0.0f;
            DecimalFormat decimalFormat = new DecimalFormat("##.00");
            if(graphs[0] != null){
                if(graphs[1] != null){
                    updated = Float.valueOf(graphs[1] * 100) / graphs[0];
                }
                if(graphs[2] != null){
                    notUpdated = Float.valueOf(graphs[2] * 100) / graphs[0];
                }

                int colorSync = ContextCompat.getColor(this, R.color.colorPrimary);
                int colorUnSync = ContextCompat.getColor(this, R.color.red);

                sGraph.getBars().clear();
                sGraph.addBar(new BarModel(Float.valueOf(decimalFormat.format(updated)), colorSync));
                sGraph.addBar(new BarModel(Float.valueOf(decimalFormat.format(notUpdated)), colorUnSync));

                mStackedBarChart.addBar(sGraph);
                mStackedBarChart.startAnimation();
            }
        }
    }
}
