package com.osm.soft.sf.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.osm.soft.sf.BuildConfig;
import com.osm.soft.sf.model.Customer;
import com.osm.soft.sf.model.Order;
import com.osm.soft.sf.model.Product;
import com.osm.soft.sf.model.ProductOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mendez Fernandez on 05/07/2016.
 */
public class ProductOrderSqlHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "osm.db";
    public static final String TABLE_NAME  = "product_order";
    public static final String ID  = "id";
    public static final String ORDER_ID  = "id_order";
    public static final String PRODUCT_CODE  = "product_code";
    public static final String PRODUCT_DESCRIPTION  = "product_description";
    public static final String QUANTITY  = "quantity";

    public ProductOrderSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, BuildConfig.VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(new StringBuilder()
                .append("create table ")
                .append(TABLE_NAME)
                .append(" ( ")
                .append(ID)
                .append(" integer primary key autoincrement, ")
                .append(PRODUCT_CODE)
                .append(" text not null, ")
                .append(QUANTITY)
                .append(" integer not null, ")
                .append(ORDER_ID)
                .append(" integer not null, ")
                .append(PRODUCT_DESCRIPTION)
                .append(" text not null) ")
                .toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void init(){
        SQLiteDatabase sqLiteOpenHelper = this.getReadableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            sqLiteOpenHelper.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteOpenHelper);
            if(sqLiteOpenHelper != null)
                sqLiteOpenHelper.close();
        }
        if(sqLiteOpenHelper != null)
            sqLiteOpenHelper.close();
    }

    public AddProductOrder ADD = productOrder->{
        SQLiteDatabase sqLiteOpenHelper = this.getWritableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(ORDER_ID, productOrder.getOrder().getId());
            contentValues.put(PRODUCT_CODE, productOrder.getProduct().getCode());
            contentValues.put(QUANTITY, productOrder.getQuantity());
            contentValues.put(PRODUCT_DESCRIPTION, productOrder.getProduct().getName());
            long id = sqLiteOpenHelper.insert(TABLE_NAME, null, contentValues);
            productOrder.setId((int)id);
            sqLiteOpenHelper.close();
        }
        return productOrder;
    };

    public UpdateProductOrder UPDATE = productOrder->{
        SQLiteDatabase sqLiteOpenHelper = this.getWritableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(QUANTITY, productOrder.getQuantity());
            sqLiteOpenHelper.update(TABLE_NAME, contentValues, ID + "=" + productOrder.getId(), null);
            sqLiteOpenHelper.close();
        }
        return productOrder;
    };

    public DeleteProductOrder DELETE = productOrder->{
        SQLiteDatabase sqLiteOpenHelper = this.getWritableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            int delete = 0;
            if(productOrder != null){
                delete = sqLiteOpenHelper.delete(TABLE_NAME, ID + "=" + productOrder.getId(), null);
            }
            sqLiteOpenHelper.close();
            return delete > 0;
        }
        return false;
    };

    public DeleteByOrder DELETE_BY_ORDER = order->{
        SQLiteDatabase sqLiteOpenHelper = this.getWritableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            if(order != null){
                sqLiteOpenHelper.delete(TABLE_NAME, ORDER_ID + "=" +order.getId() , null);
            }
            sqLiteOpenHelper.close();
        }
    };

    public static List<ProductOrder> getProductsOrder(final Order order,
                                                      final SQLiteDatabase sqLiteDatabase){
        return GET.execute(order, sqLiteDatabase);
    }

    private static GetByOrder GET = (order, sqLiteDatabase)->{
        List<ProductOrder> orders = new ArrayList<>();
        SQLiteDatabase sqLiteOpenHelper = sqLiteDatabase;
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select * from ");
            stringBuilder.append(TABLE_NAME);
            stringBuilder.append(" where ");
            stringBuilder.append(ORDER_ID);
            stringBuilder.append( "=");
            stringBuilder.append(order.getId());
            Cursor cursor = sqLiteOpenHelper.rawQuery(stringBuilder.toString(), null);
            if(cursor != null){
                cursor.moveToFirst();
                // TODO add description and cost on product object
                while (!cursor.isAfterLast()){
                    orders.add(new ProductOrder.Builder()
                            .order(order)
                            .id(cursor.getInt(0))
                            .product(new Product.Builder()
                                    .code(cursor.getString(1))
                                    .name(cursor.getString(4))
                                    .build())
                            .quantity(cursor.getInt(2))
                            .build());
                    cursor.moveToNext();
                }
            }
        }
        return orders;
    };

    @FunctionalInterface
    public interface AddProductOrder{
        ProductOrder execute(ProductOrder productOrder);
    }

    @FunctionalInterface
    public interface DeleteProductOrder{
        boolean execute(ProductOrder productOrder);
    }

    @FunctionalInterface
    public interface GetByOrder{
        List<ProductOrder> execute(final Order order, final SQLiteDatabase sqLiteDatabase);
    }

    @FunctionalInterface
    public interface DeleteByOrder{
        void execute(Order order);
    }

    @FunctionalInterface
    public interface UpdateProductOrder{
        ProductOrder execute(ProductOrder productOrder);
    }
}
