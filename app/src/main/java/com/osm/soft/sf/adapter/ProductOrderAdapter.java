package com.osm.soft.sf.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.osm.soft.sf.R;
import com.osm.soft.sf.model.Order;
import com.osm.soft.sf.model.Product;
import com.osm.soft.sf.model.ProductOrder;
import com.osm.soft.sf.util.DialogHelper;
import com.osm.soft.sf.util.Functions;
import com.osm.soft.sf.util.ProductOrderSqlHelper;

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

    public class ProductHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        private TextView mTvCode;
        private TextView mTvDescription;
        private TextView mTvPrice;
        private TextView mtvQuantity;
        private ImageView mIvColor;
        private ProductOrderSqlHelper mProductOrderSqlHelper;

        public ProductHolder(View itemView) {
            super(itemView);
            mTvCode = (TextView) itemView.findViewById(R.id.tv_code);
            mTvDescription = (TextView) itemView.findViewById(R.id.tv_number);
            mTvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            mtvQuantity = (TextView)itemView.findViewById(R.id.tv_quantity);
            mIvColor = (ImageView)itemView.findViewById(R.id.iv_color);
            mProductOrderSqlHelper = new ProductOrderSqlHelper(mIvColor.getContext());
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(R.string.delete).setOnMenuItemClickListener(menuItem -> {
                ProductOrder productOrder = get(getLayoutPosition());
                if(!productOrder.getOrder().isSynced())
                    DialogHelper.confirm(view.getContext(), R.string.message_delete_product,
                            (dialogInterface, i) -> {
                                if(productOrder != null){
                                    if(mProductOrderSqlHelper.DELETE.execute(productOrder)) {
                                        remove(getLayoutPosition());
                                    }
                                }
                            },
                            (dialogInterface1, i1) -> notifyDataSetChanged()
                    );
                else{
                    DialogHelper.ok(view.getContext(), R.string.order_synced);
                }
                return true;
            });
            contextMenu.add(R.string.change_quantiy).setOnMenuItemClickListener(menuItem -> {
                ProductOrder productOrder = get(getLayoutPosition());
                if(!productOrder.getOrder().isSynced()){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                    alertDialog.setTitle(R.string.change_quantiy);

                    final EditText input = new EditText(view.getContext());
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    input.setText(String.valueOf(productOrder.getQuantity()));
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    alertDialog.setView(input);
                    alertDialog.setPositiveButton(view.getContext().getString(R.string.ok),  (dialogInterface, i) -> {
                        if(input.getText().length() > 0){
                            productOrder.setQuantity(Integer.valueOf(input.getText().toString()));
                            mProductOrderSqlHelper.UPDATE.execute(productOrder);
                            notifyDataSetChanged();
                        }
                    });
                    alertDialog.setNegativeButton(view.getContext().getString(R.string.cancel),
                            (dialogInterface, i) -> dialogInterface.dismiss());
                    alertDialog.show();
                }
                else{
                    DialogHelper.ok(view.getContext(), R.string.order_synced);
                }
                return true;
            });
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

    public ProductOrder get(final int position){
        if(mProducts != null && !mProducts.isEmpty())
            return mProducts.get(position);
        return null;
    }

    public void remove(int position) {
        if (position < 0 || position >= mProducts.size()) {
            return;
        }
        mProducts.remove(position);
        notifyItemRemoved(position);
    }
}
