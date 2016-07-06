package com.osm.soft.sf.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.osm.soft.sf.R;
import com.osm.soft.sf.model.Product;
import com.osm.soft.sf.model.ProductOrder;
import com.osm.soft.sf.util.Functions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres on 09/06/16.
 */
public class ProductOrderAdapter extends RecyclerView.Adapter<ProductOrderAdapter.ProductHolder>{

    private List<ProductOrder> mProducts;
    private final int VIEW;
    private final int DPI;

    public ProductOrderAdapter(List<ProductOrder> mProducts, final int VIEW, final int DPI) {
        this.mProducts = mProducts;
        this.VIEW = VIEW;
        this.DPI = DPI;
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        private TextView mTvCode;
        private TextView mTvDescription;
        private TextView mTvPrice;
        private TextView mtvQuantity;
        private ImageView mIvColor;

        public ProductHolder(View itemView) {
            super(itemView);
            mTvCode = (TextView) itemView.findViewById(R.id.tv_code);
            mTvDescription = (TextView) itemView.findViewById(R.id.tv_number);
            mTvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            mtvQuantity = (TextView)itemView.findViewById(R.id.tv_quantity);
            mIvColor = (ImageView)itemView.findViewById(R.id.iv_color);
        }
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(VIEW, parent, false);

        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {

        ProductOrder product = mProducts.get(position);

        holder.mTvCode.setText(product.getProduct().getCode());
        holder.mTvDescription.setText(product.getProduct().getName());
        //holder.mTvPrice.setText("Bs. " + Functions.format(product.getPrice()));
        holder.mTvPrice.setVisibility(View.INVISIBLE);
        holder.mtvQuantity.setText(String.valueOf(product.getQuantity()));

        String index = String.valueOf(position);
        Functions.changeColor(holder.mIvColor, String.valueOf(product.getProduct().getName().charAt(0)),
                Integer.valueOf(index.substring(index.length() - 1)), DPI);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }
}
