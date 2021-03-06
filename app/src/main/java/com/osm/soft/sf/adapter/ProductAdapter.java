package com.osm.soft.sf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.osm.soft.sf.R;
import com.osm.soft.sf.model.Order;
import com.osm.soft.sf.model.Product;
import com.osm.soft.sf.model.ProductOrder;
import com.osm.soft.sf.util.Functions;
import com.osm.soft.sf.util.OrderSqlHelper;
import com.osm.soft.sf.util.ProductOrderSqlHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres on 09/06/16.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> implements Filterable {

    private List<Product> mProducts;
    private List<Product> mProductsFilter;
    private Order mOrder;
    private final int VIEW;
    private final int DPI;
    private ProductOrderSqlHelper mProductOrderSqlHelper;
    private OrderSqlHelper mOrderSqlHelper;

    public ProductAdapter(List<Product> mProducts, final int VIEW, final int DPI) {
        this.mProducts = mProducts;
        this.VIEW = VIEW;
        this.DPI = DPI;
        mProductsFilter = new ArrayList<>(mProducts);
    }

    public ProductAdapter(final Context context, List<Product> mProducts, Order order, final int VIEW, final int DPI) {
        this.mProducts = mProducts;
        this.VIEW = VIEW;
        this.DPI = DPI;
        mProductsFilter = new ArrayList<>(mProducts);
        mOrder = order;
        mProductOrderSqlHelper = new ProductOrderSqlHelper(context);
        mOrderSqlHelper = new OrderSqlHelper(context);
    }

    public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(mOrder != null){
                final EditText input = new EditText(view.getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setText(String.valueOf(0));
                Functions.showWithCustomView(view.getContext(), input, (dialogInterface, i) -> {
                    if(input.getText().length() > 0){

                        if(mOrder.getId() == 0)
                            mOrder = mOrderSqlHelper.ADD.execute(mOrder);

                        Log.i("Hola", getAdapterPosition() + " " + mProducts.get(getAdapterPosition()).getName());

                        mProductOrderSqlHelper.ADD.execute(new ProductOrder.Builder().product(
                                mProducts.get(getAdapterPosition()))
                                .quantity(Integer.valueOf(input.getText().toString()))
                                .order(mOrder).build());
                    }
                }, (dialogInterface, i) -> dialogInterface.dismiss());
            }
        }
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(VIEW, parent, false);

        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {

        Product product = mProducts.get(position);

        holder.mTvCode.setText(product.getCode());
        holder.mTvDescription.setText(product.getName());
        //holder.mTvPrice.setText("Bs. " + Functions.format(product.getPrice()));
        holder.mTvPrice.setVisibility(View.INVISIBLE);
        holder.mtvQuantity.setText(String.valueOf(product.getStock()));

        String index = String.valueOf(position);
        Functions.changeColor(holder.mIvColor, String.valueOf(product.getName().charAt(0)),
                Integer.valueOf(index.substring(index.length() - 1)), DPI);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    @Override
    public Filter getFilter() {
        return new ProductFilter(this, mProductsFilter);
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
                final String filterPattern = constraint.toString().toUpperCase().trim();

                for (final Product product : products) {
                    if (product.getCode().toUpperCase().contains(filterPattern) ||
                            product.getName().toUpperCase().contains(filterPattern)) {
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
