package com.pskloud.osm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

import com.pskloud.osm.R;
import com.pskloud.osm.model.Order;
import com.pskloud.osm.util.Functions;

import java.util.List;

/**
 * Created by andres on 09/06/16.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {

    private List<Order> mOrders;

    public OrderAdapter(List<Order> mOrders) {
        this.mOrders = mOrders;
    }

    public class OrderHolder extends RecyclerView.ViewHolder {

        private TextView mTvNumber;
        private TextView mTvQuantity;
        private TextView mTvDate;

        public OrderHolder(View itemView) {
            super(itemView);
            mTvNumber = (TextView) itemView.findViewById(R.id.tv_number);
            mTvQuantity = (TextView) itemView.findViewById(R.id.tv_quantity);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
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

        holder.mTvNumber.setText(order.getNumber());
        holder.mTvQuantity.setText(String.valueOf(order.getTotalItem()));
        holder.mTvDate.setText(Functions.format(order.getDate()));

    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

}
