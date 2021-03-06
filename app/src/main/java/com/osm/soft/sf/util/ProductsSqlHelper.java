package com.osm.soft.sf.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.osm.soft.sf.BuildConfig;
import com.osm.soft.sf.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mendez Fernandez on 18/06/2016.
 */
public class ProductsSqlHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "osm.db";
    public static final String TABLE_NAME  = "product";
    public static final String CODE  = "code";
    public static final String NAME  = "name";
    public static final String GROUP  = "groupo";
    public static final String PRICE  = "price";
    public static final String STOCK  = "stock";

    public ProductsSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, BuildConfig.VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(new StringBuilder()
                .append("create table ")
                .append(TABLE_NAME)
                .append(" ( ")
                .append(CODE)
                .append(" text, ")
                .append(NAME)
                .append(" text, ")
                .append(GROUP)
                .append(" text, ")
                .append(PRICE)
                .append(" text, ")
                .append(STOCK)
                .append(" integer) ")
                .toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    final public AddProduct ADD = (product)->{
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        if(writableDatabase != null && writableDatabase.isOpen()){
            ContentValues contentValues = setConteValues(product);
            long result = writableDatabase.insert(TABLE_NAME, null, contentValues);
            writableDatabase.close();
            if(BuildConfig.DEBUG)
                Log.i(ProductsSqlHelper.class.getCanonicalName(), "" + result);
            return result;
        }

        return -1;
    };

    private ContentValues setConteValues(Product product){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CODE, product.getCode());
        contentValues.put(NAME, product.getName().toUpperCase());
        contentValues.put(GROUP, product.getGroup());
        Log.i("PRoducts" , android.text.TextUtils.join(",", product.getPrice()));
        contentValues.put(PRICE, android.text.TextUtils.join(",", product.getPrice()));
        contentValues.put(STOCK, product.getStock());
        return contentValues;
    }

    final public GetProducts GET = () -> {
        return get(new StringBuilder()
                .append("select * from ")
                .append(TABLE_NAME)
                .append(" order by ")
                .append(NAME)
                .append(" asc")
                .toString());
    };

    private List<Product> get(final String sql) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase sqLiteOpenHelper = this.getReadableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            try{
                Cursor cursor = sqLiteOpenHelper.rawQuery(sql, null);
                if(cursor != null){
                    cursor.moveToFirst();
                    while(cursor.isAfterLast() == false){
                        products.add(new Product.Builder()
                                .code(cursor.getString(0))
                                .name(cursor.getString(1))
                                .group(cursor.getString(2))
                                .price(Arrays.asList(cursor.getString(3).split(",")))
                                .stock(cursor.getInt(4)).build());
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }catch(Exception e){
                if(BuildConfig.DEBUG)
                    Log.e(ProductsSqlHelper.class.getCanonicalName(), e.getMessage());
            }
        }
        sqLiteOpenHelper.close();
        return products;
    }

    public DeleteProducts DELETE = () -> {
        SQLiteDatabase sqLiteOpenHelper = this.getReadableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            sqLiteOpenHelper.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteOpenHelper);
            return true;
        }
        return false;
    };

    @FunctionalInterface
    public interface AddProduct{
        long execute(Product product);
    }

    @FunctionalInterface
    public interface GetProducts{
        List<Product> execute();
    }

    @FunctionalInterface
    public interface DeleteProducts{
        boolean execute();
    }
}
