package com.osm.soft.sf;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.osm.soft.sf.adapter.OrderAdapter;
import com.osm.soft.sf.model.Order;
import com.osm.soft.sf.util.DialogHelper;
import com.osm.soft.sf.util.OrderSqlHelper;

public class OrdersActivity extends DefaultActivity {

    private RecyclerView mRvOrders;
    private OrderAdapter mOrderAdapter;
    private CoordinatorLayout mClOrder;
    private OrderSqlHelper orderSqlHelper;

    public static void show(DefaultActivity defaultActivity){
        defaultActivity.startActivity(new Intent(defaultActivity, OrdersActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mOrderAdapter = new OrderAdapter(this, orderSqlHelper.GET_ALL.execute(), true);
            mRvOrders.setAdapter(mOrderAdapter);
            mRvOrders.setHasFixedSize(true);
        }catch (Exception e){
            if(BuildConfig.DEBUG)
                Log.e(OrdersActivity.class.getCanonicalName(), e.getMessage());
        }
    }

    @Override
    protected void setUp() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRvOrders.setLayoutManager(mLayoutManager);
        mRvOrders.setItemAnimator(new DefaultItemAnimator());
        findViewById(R.id.fab_add).setOnClickListener(view -> CustomersActivity.show(this));
        swipe();
    }

    @Override
    protected void init() {
        mRvOrders = (RecyclerView) findViewById(R.id.rv_orders);
        mClOrder = (CoordinatorLayout) findViewById(R.id.cl_order);

        orderSqlHelper = new OrderSqlHelper(this);
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
}
