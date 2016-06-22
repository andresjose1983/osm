package com.pskloud.osm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pskloud.osm.util.NotificationHelper;

import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;

public class MainActivity extends DefaultActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private NavigationView mNvMain;
    private StackedBarChart mStackedBarChart;

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
        NotificationHelper.close(NotificationHelper.NOTIFICATION_DOWNLOADED_CUSTOMER);
        NotificationHelper.close(NotificationHelper.NOTIFICATION_DOWNLOADED_LOCALITY);
        NotificationHelper.close(NotificationHelper.NOTIFICATION_DOWNLOADED_TAX_TYPES);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

        StackedBarModel s1 = new StackedBarModel(getString(R.string.nav_customers));

        int colorSync = ContextCompat.getColor(this, R.color.colorAccent);
        int colorUnSync = ContextCompat.getColor(this, R.color.colorPrimary);

        s1.addBar(new BarModel(/*98f*/0f, colorSync));
        s1.addBar(new BarModel(/*2f*/0f, colorUnSync));

        StackedBarModel s2 = new StackedBarModel(getString(R.string.nav_orders));
        s2.addBar(new BarModel(60f, colorSync));
        s2.addBar(new BarModel(40f, colorUnSync));

        mStackedBarChart.addBar(s1);
        mStackedBarChart.addBar(s2);

        mStackedBarChart.startAnimation();
    }

    @Override
    public void init() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        mNvMain = (NavigationView)findViewById(R.id.nav_view);
        mStackedBarChart = (StackedBarChart) findViewById(R.id.sbc_view);
    }

}
