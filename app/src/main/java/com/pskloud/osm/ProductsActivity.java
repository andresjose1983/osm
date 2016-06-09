package com.pskloud.osm;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import com.pskloud.osm.adapter.ProductAdapter;
import com.pskloud.osm.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends DefaultActivity implements SearchView.OnQueryTextListener {

    private SearchView mSearchView;
    private RecyclerView mRvProducts;
    private ProductAdapter mProductAdapter;
    private List<Product> mProducts = new ArrayList<>();

    public static void show(Context context) {
        context.startActivity(new Intent(context, ProductsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_products);
    }

    @Override
    public void setUp() {
        mProducts.add(new Product("70240216", "1K TEMPSISTOR 1% P/CS-800S", 4400, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("8A-14X ", "8A-14X CABLE DIM-DIM 8 CONTACTOS", 3400, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("Z-ML024-036", "ACCESORIOS DE LUZ MYSTIC LED", 4500, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("TAC-MP3M", "ADAPTADOR ENTRADA USB-MP3 PARA CONSOLAS", 21500, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("PN-USB-BLUE", "ADAPTADOR USB BLUETOOTH PARA CORNETAS", 1850, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("222012", "AGARRADERA DE GOMA NEGRA TIRADOR", 1050, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("44-2035", "AGARRADERA NEGRA PARA CORNETA 44-2035", 1650, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRvProducts.setLayoutManager(mLayoutManager);
        mRvProducts.setItemAnimator(new DefaultItemAnimator());

        mProductAdapter = new ProductAdapter(mProducts);
        mRvProducts.setAdapter(mProductAdapter);
        mRvProducts.setHasFixedSize(true);
    }

    @Override
    public void init() {
        mRvProducts = (RecyclerView)findViewById(R.id.rvProducts);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        // Associate searchable configuration with the SearchView
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return super.onCreateOptionsMenu(menu);
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
        mProductAdapter.getFilter().filter(query.toLowerCase());
    }

}
