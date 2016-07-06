package com.osm.soft.sf.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.osm.soft.sf.BuildConfig;
import com.osm.soft.sf.CustomersActivity;
import com.osm.soft.sf.DefaultActivity;
import com.osm.soft.sf.OsmApplication;
import com.osm.soft.sf.ProductsActivity;
import com.osm.soft.sf.R;
import com.osm.soft.sf.adapter.OrderAdapter;
import com.osm.soft.sf.model.Customer;
import com.osm.soft.sf.util.Functions;
import com.osm.soft.sf.util.OrderSqlHelper;

/**
 * Created by andres on 09/06/16.
 */
public class DialogOrderFragment extends BottomSheetDialogFragment {

    private RecyclerView mRvOrders;
    private OrderAdapter mOrderAdapter;
    private TextView mtvTitle;

    private Customer customer;
    public static String BOTTON_SHEET_NAME = "BSDialog";
    private OrderSqlHelper orderSqlHelper;

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

    @Override
    public void onResume() {
        super.onResume();
        if(BuildConfig.DEBUG)
            Log.d(DialogOrderFragment.class.getCanonicalName(), customer.getCode());

        try {
            mOrderAdapter = new OrderAdapter(orderSqlHelper.GET.execute(customer));
            mRvOrders.setAdapter(mOrderAdapter);
            mRvOrders.setHasFixedSize(true);
        }catch (Exception e){
            if(BuildConfig.DEBUG) {
                Log.d(DialogOrderFragment.class.getCanonicalName(), e.getMessage());

            }
        }
    }

    private void setUp(View view) {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        mRvOrders.setLayoutManager(mLayoutManager);
        mRvOrders.setItemAnimator(new DefaultItemAnimator());

        customer = (Customer) this.getArguments().getSerializable(CustomersActivity.ARG_CUSTOMER);
        if(customer != null)
            mtvTitle.setText(customer.getName());

        View viewAddOrder = view.findViewById(R.id.iv_add_order);
        viewAddOrder.setOnClickListener(view1 -> ProductsActivity.show((DefaultActivity) getActivity(), customer));

        Functions.setViewSelected(viewAddOrder);


    }

    private void init(View view) {
        mRvOrders = (RecyclerView) view.findViewById(R.id.rv_orders);
        mtvTitle = (TextView)view.findViewById(R.id.tv_title);

        orderSqlHelper = new OrderSqlHelper(OsmApplication.getInstance());
    }

}
