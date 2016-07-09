package com.osm.soft.sf.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.osm.soft.sf.DefaultActivity;
import com.osm.soft.sf.ProductsActivity;
import com.osm.soft.sf.R;
import com.osm.soft.sf.model.Order;
import com.osm.soft.sf.util.Functions;

import java.util.List;

/**
 * Created by andres on 09/06/16.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder>{

    private List<Order> mOrders;
    private DefaultActivity mDefaultActivity;
    private boolean showCustomerInfo;

    public OrderAdapter(final DefaultActivity defaultActivity, List<Order> mOrders,
                        boolean showCustomerInfo) {
        mDefaultActivity = defaultActivity;
        this.mOrders = mOrders;
        this.showCustomerInfo = showCustomerInfo;
    }

    public class OrderHolder extends RecyclerView.ViewHolder {

        private TextView mTvNumber;
        private TextView mTvDate;
        private TextView mTvName;
        private TextView mTvCode;
        private RecyclerView mRvProducts;
        private ProductOrderAdapter mProductAdapter;
        private ImageView mIvEditOrder;
        private ImageView mIvSync;
        private ImageView mIvColor;

        public OrderHolder(View itemView) {
            super(itemView);
            mTvNumber = (TextView) itemView.findViewById(R.id.tv_number);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mTvCode = (TextView) itemView.findViewById(R.id.tv_code);
            mRvProducts = (RecyclerView) itemView.findViewById(R.id.rv_products);
            mIvEditOrder = (ImageView) itemView.findViewById(R.id.iv_edit_order);
            mIvSync = (ImageView) itemView.findViewById(R.id.iv_sync);
            mIvColor = (ImageView) itemView.findViewById(R.id.iv_color);

            itemView.setOnClickListener(v -> {
                Order order = mOrders.get(getAdapterPosition());
                if (order.isView()) {
                    order.setView(false);
                } else {
                    order.setView(true);
                }
                mOrders.set(getAdapterPosition(), order);
                notifyItemChanged(getAdapterPosition());
            });

            mIvEditOrder.setOnClickListener(view -> ProductsActivity.show(mDefaultActivity,
                    mOrders.get(getAdapterPosition())));
        }
    }


    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,
                parent, false);

        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {

        Order order = mOrders.get(position);

        holder.mTvNumber.setText(String.valueOf(order.getId()));
        holder.mTvDate.setText(Functions.format(order.getDate()));

        holder.mProductAdapter = new ProductOrderAdapter(order.getProducts(), R.layout.item_grid_product,-1);
        holder.mRvProducts.setLayoutManager(new GridLayoutManager(holder.mRvProducts.getContext(), 2));
        holder.mRvProducts.setItemAnimator(new DefaultItemAnimator());
        holder.mRvProducts.setNestedScrollingEnabled(false);
        holder.mRvProducts.setHasFixedSize(false);
        holder.mRvProducts.setAdapter(holder.mProductAdapter);

        String index = String.valueOf(position);

        GradientDrawable bgShape = (GradientDrawable) holder.mIvColor.getBackground();
        bgShape.setColor(Color.parseColor(Functions.getColor(Integer.valueOf(
                index.substring(index.length() - 1)))));

        if(order.isSync())
            holder.mIvSync.setVisibility(View.GONE);
        else
            holder.mIvSync.setVisibility(View.VISIBLE);

        if(showCustomerInfo){
            holder.mTvCode.setText(order.getCustomer().getCode());
            holder.mTvName.setText(order.getCustomer().getName());
            holder.mTvCode.setVisibility(View.VISIBLE);
            holder.mTvName.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public Order get(final int position){
        if(mOrders != null && !mOrders.isEmpty())
            return mOrders.get(position);
        return null;
    }

    public void remove(int position) {
        if (position < 0 || position >= mOrders.size()) {
            return;
        }
        mOrders.remove(position);
        notifyItemRemoved(position);
    }

}
