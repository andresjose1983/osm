package com.pskloud.osm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.pskloud.osm.R;
import com.pskloud.osm.model.Product;
import com.pskloud.osm.util.Functions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres on 09/06/16.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> implements Filterable {

    private List<Product> mProducts;
    private List<Product> mProductsFilter;

    public ProductAdapter(List<Product> mProducts) {
        this.mProducts = mProducts;
        mProductsFilter = new ArrayList<>(mProducts);
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        private TextView mTvCode;
        private TextView mTvDescription;
        private TextView mTvPrice;
        private ImageView mIvPicture;

        public ProductHolder(View itemView) {
            super(itemView);
            mTvCode = (TextView) itemView.findViewById(R.id.tv_code);
            mTvDescription = (TextView) itemView.findViewById(R.id.tv_number);
            mTvPrice = (TextView) itemView.findViewById(R.id.tv_quantity);
            mIvPicture = (ImageView)itemView.findViewById(R.id.iv_product);
        }
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,
                parent, false);

        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {

        Product product = mProducts.get(position);

        holder.mTvCode.setText(product.getCode());
        holder.mTvDescription.setText(product.getDescription());
        holder.mTvPrice.setText(String.valueOf(product.getPrice()));

        Functions.loadImageCircle(holder.mIvPicture, product.getPicture());
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    @Override
    public Filter getFilter() {
        return new ProductFilter(this, mProducts);
    }

    private static class ProductFilter extends Filter{

        private List<Product> filtersProduct = new ArrayList<>();
        final private List<Product> products;
        final private ProductAdapter productAdapter;

        public ProductFilter(ProductAdapter productAdapter, List<Product> products) {
            super();
            this.products = products;
            this.productAdapter = productAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filtersProduct.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filtersProduct.addAll(products);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final Product product : products) {
                    if (product.getCode().toLowerCase().contains(filterPattern) ||
                            product.getDescription().toLowerCase().contains(filterPattern)) {
                        filtersProduct.add(product);
                    }
                }
            }
            results.values = filtersProduct;
            results.count = filtersProduct.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productAdapter.mProducts.clear();
            productAdapter.mProducts.addAll((ArrayList<Product>) results.values);
            productAdapter.notifyDataSetChanged();
        }
    }
}