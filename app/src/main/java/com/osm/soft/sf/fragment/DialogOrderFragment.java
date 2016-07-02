package com.osm.soft.sf.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.osm.soft.sf.CustomersActivity;
import com.osm.soft.sf.R;
import com.osm.soft.sf.adapter.OrderAdapter;
import com.osm.soft.sf.model.Customer;
import com.osm.soft.sf.model.Order;
import com.osm.soft.sf.util.Functions;

import java.util.List;

/**
 * Created by andres on 09/06/16.
 */
public class DialogOrderFragment extends BottomSheetDialogFragment {

    private RecyclerView mRvOrders;
    private OrderAdapter mOrderAdapter;
    private TextView mtvTitle;

    private Customer customer;
    public static String BOTTON_SHEET_NAME = "BSDialog";

    private List<Order> mOrders = Functions.getOrder();

    static public DialogOrderFragment newInstance() {
        return new DialogOrderFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_order, container, false);
        init(view);
        setUp(view);
        return view;
    }

    private void setUp(View view) {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        mRvOrders.setLayoutManager(mLayoutManager);
        mRvOrders.setItemAnimator(new DefaultItemAnimator());

        mOrderAdapter = new OrderAdapter(mOrders);
        mRvOrders.setAdapter(mOrderAdapter);
        mRvOrders.setHasFixedSize(true);

        customer = (Customer) this.getArguments().getSerializable(CustomersActivity.ARG_CUSTOMER);
        if(customer != null)
            mtvTitle.setText(customer.getName());

    }

    private void init(View view) {
        mRvOrders = (RecyclerView) view.findViewById(R.id.rv_orders);
        mtvTitle = (TextView)view.findViewById(R.id.tv_title);
    }

}
