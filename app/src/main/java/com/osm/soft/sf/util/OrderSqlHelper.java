package com.osm.soft.sf.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.osm.soft.sf.BuildConfig;
import com.osm.soft.sf.model.Customer;
import com.osm.soft.sf.model.Order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Mendez Fernandez on 05/07/2016.
 */
public class OrderSqlHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "osm.db";
    private static final String TABLE_NAME  = "orders";
    private static final String ID  = "id";
    private static final String CUSTOMER_CODE  = "id_customer";
    private static final String DATE  = "order_date";
    private static final String SYNC  = "sync";
    private ProductOrderSqlHelper mProductOrderSqlHelper;

    public OrderSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, BuildConfig.VERSION_BD);
        mProductOrderSqlHelper = new ProductOrderSqlHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(new StringBuilder()
                .append("create table ")
                .append(TABLE_NAME)
                .append(" ( ")
                .append(ID)
                .append(" integer primary key autoincrement, ")
                .append(CUSTOMER_CODE)
                .append(" text, ")
                .append(SYNC)
                .append(" integer, ")
                .append(DATE)
                .append(" integer)")
                .toString());
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

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public AddOrder ADD = order->{
        SQLiteDatabase sqLiteOpenHelper = this.getWritableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(CUSTOMER_CODE, order.getCustomer().getCode());
            contentValues.put(SYNC, 0);
            contentValues.put(DATE, Calendar.getInstance().getTimeInMillis());
            long id = sqLiteOpenHelper.insert(TABLE_NAME, null, contentValues);
            order.setId((int)id);
            sqLiteOpenHelper.close();
        }
        return order;
    };

    public DeleteOrder DELETE = order->{
        SQLiteDatabase sqLiteOpenHelper = this.getWritableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            int delete = 0;
            if(order != null && !order.isSynced()) {
                delete = sqLiteOpenHelper.delete(TABLE_NAME, ID + " = " + order.getId(), null);
                if(delete > 0)
                    mProductOrderSqlHelper.DELETE_BY_ORDER.execute(order);
            }
            sqLiteOpenHelper.close();
            return delete > 0;
        }
        return false;
    };

    public GetCustomerOrder GET = customer->{
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase sqLiteOpenHelper = this.getReadableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select * from ");
            stringBuilder.append(TABLE_NAME);
            stringBuilder.append(" where ");
            stringBuilder.append(CUSTOMER_CODE);
            stringBuilder.append( "=");
            stringBuilder.append("'");
            stringBuilder.append(customer.getCode());
            stringBuilder.append("'");
            Cursor cursor = sqLiteOpenHelper.rawQuery(stringBuilder.toString(), null);
            if(cursor != null){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){

                    Calendar instance = Calendar.getInstance();
                    instance.setTimeInMillis(cursor.getLong(3));
                    Order order = new Order.Builder().isView(false)
                            .customer(customer)
                            .id(cursor.getInt(0))
                            .isSynced(cursor.getInt(2)==1)
                            .date(instance.getTime()).build();
                    order.setProducts(ProductOrderSqlHelper.getProductsOrder(order, sqLiteOpenHelper));
                    orders.add(order);
                    cursor.moveToNext();
                }
            }
            sqLiteOpenHelper.close();
        }
        return orders;
    };

    @FunctionalInterface
    public interface AddOrder{
        Order execute(Order order);
    }

    @FunctionalInterface
    public interface DeleteOrder{
        boolean execute(Order order);
    }

    @FunctionalInterface
    public interface GetCustomerOrder{
        List<Order> execute(Customer customer);
    }

    @FunctionalInterface
    public interface Get{
        List<Order> execute();
    }
}