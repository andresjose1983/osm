package com.osm.soft.sf.adapter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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

    public OrderAdapter(final DefaultActivity defaultActivity, List<Order> mOrders) {
        mDefaultActivity = defaultActivity;
        this.mOrders = mOrders;
    }

    public class OrderHolder extends RecyclerView.ViewHolder  {

        private TextView mTvNumber;
        private TextView mTvDate;
        private RecyclerView mRvProducts;
        private RecyclerView.LayoutManager mLayoutManager;
        private ProductOrderAdapter mProductAdapter;
        private View mvProducts;
        private ImageView mIvEditOrder;

        public OrderHolder(View itemView) {
            super(itemView);
            mTvNumber = (TextView) itemView.findViewById(R.id.tv_number);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
            mRvProducts = (RecyclerView) itemView.findViewById(R.id.rv_products);
            mvProducts = itemView.findViewById(R.id.v_products);
            mIvEditOrder = (ImageView) itemView.findViewById(R.id.iv_edit_order);

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

            mLayoutManager = new LinearLayoutManager(mRvProducts.getContext());
            mRvProducts.setLayoutManager(mLayoutManager);
            mRvProducts.setItemAnimator(new DefaultItemAnimator());
            mRvProducts.setHasFixedSize(true);

        }

        private void expand() {
            //set Visible
            mvProducts.setVisibility(View.VISIBLE);

            final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            mvProducts.measure(widthSpec, heightSpec);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                ValueAnimator mAnimator = slideAnimator(0, mvProducts.getMeasuredHeight());
                mAnimator.start();
            }
        }

        private ValueAnimator slideAnimator(int start, int end) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                ValueAnimator animator = ValueAnimator.ofInt(start, end);

                animator.addUpdateListener(valueAnimator -> {
                    //Update Height
                    int value = 0;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                        value = (Integer) valueAnimator.getAnimatedValue();
                    }
                    ViewGroup.LayoutParams layoutParams = mvProducts.getLayoutParams();
                    layoutParams.height = value;
                    mvProducts.setLayoutParams(layoutParams);
                });
                return animator;
            }
            return null;
        }

        private void collapse() {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {

                ValueAnimator mAnimator = slideAnimator(mvProducts.getHeight(), 0);
                if (mAnimator != null) {
                    mAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            //Height=0, but it set visibility to GONE
                            mvProducts.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });

                }

                mAnimator.start();
            } else {
                mvProducts.setVisibility(View.GONE);
            }

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

        holder.mProductAdapter = new ProductOrderAdapter(order.getProducts(), R.layout.item_product,-1);
        holder.mRvProducts.setAdapter(holder.mProductAdapter);

        if(order.isView())
            holder.expand();
        else
            holder.collapse();
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
