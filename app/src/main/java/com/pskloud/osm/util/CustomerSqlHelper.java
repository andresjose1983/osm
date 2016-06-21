package com.pskloud.osm.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pskloud.osm.BuildConfig;
import com.pskloud.osm.model.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mendez Fernandez on 18/06/2016.
 */
public class CustomerSqlHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "osm.db";
    public static final String TABLE_NAME  = "customer";
    public static final String NAME  = "name";
    public static final String ADDRESS  = "address";
    public static final String PHONE  = "customer";
    public static final String TIN  = "tin";
    public static final String IDENTIFICATION  = "identification";
    public static final String CODE  = "code";
    public static final String PRICE  = "price";
    public static final String TYPE  = "type";
    public static final String TAG  = "tag";
    public static final String SYNC  = "sync";

    public CustomerSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, BuildConfig.VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(new StringBuilder()
                .append("create table ")
                .append(TABLE_NAME)
                .append(" ( ")
                .append(NAME)
                .append(" text, ")
                .append(ADDRESS)
                .append(" text, ")
                .append(PHONE)
                .append(" text, ")
                .append(TIN)
                .append(" text, ")
                .append(IDENTIFICATION)
                .append(" text, ")
                .append(CODE)
                .append(" text, ")
                .append(PRICE)
                .append(" integer, ")
                .append(TYPE)
                .append(" text, ")
                .append(TAG)
                .append(" text, ")
                .append(SYNC)
                .append(" integer) ")
                .toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    final public AddCustomer ADD_CUSTOMER = (customer)->{
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        if(writableDatabase != null && writableDatabase.isOpen()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(NAME, customer.getName().toUpperCase());
            contentValues.put(ADDRESS, customer.getAddress().toUpperCase());
            if(customer.getPhones() != null)
                contentValues.put(PHONE, customer.getPhones().isEmpty()?"":
                        customer.getPhones().get(0));
            contentValues.put(TIN, customer.getTin());
            contentValues.put(IDENTIFICATION, customer.getIdentification());
            contentValues.put(CODE, customer.getCode());
            contentValues.put(PRICE, customer.getPrice());
            contentValues.put(TYPE, customer.getType());
            contentValues.put(TAG, customer.getTag());
            contentValues.put(SYNC, 0);
            writableDatabase.insert(TABLE_NAME, null, contentValues);
            writableDatabase.close();
        }

        return true;
    };

    final public AddCustomer UPDATE_CUSTOMER = (customer)->{
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        if(writableDatabase != null && writableDatabase.isOpen()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(NAME, customer.getName().toUpperCase());
            contentValues.put(ADDRESS, customer.getAddress().toUpperCase());
            if(customer.getPhones() != null)
                contentValues.put(PHONE, customer.getPhones().isEmpty()?"":
                        customer.getPhones().get(0));
            contentValues.put(TIN, customer.getTin());
            contentValues.put(IDENTIFICATION, customer.getIdentification());
            contentValues.put(CODE, customer.getCode());
            contentValues.put(PRICE, customer.getPrice());
            contentValues.put(TYPE, customer.getType());
            contentValues.put(TAG, customer.getTag());
            contentValues.put(SYNC, customer.isSync() == true?1:0);
            writableDatabase.update(TABLE_NAME, contentValues, CODE + " = ?",
                    new String[] { customer.getCode() } );
            writableDatabase.close();
        }

        return true;
    };

    final public GetCustomers GET_CUSTOMERS = () -> {
        List<Customer> customers = new ArrayList<>();
        SQLiteDatabase sqLiteOpenHelper = this.getReadableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            Cursor cursor = sqLiteOpenHelper.rawQuery(new StringBuilder()
                    .append("select * from ")
                    .append(TABLE_NAME)
                    .append(" order by ")
                    .append(NAME)
                    .append(" asc")
                    .toString(), null);
            if(cursor != null){
                cursor.moveToFirst();
                List<String> telephones;
                while(cursor.isAfterLast() == false){
                    telephones = new ArrayList<>();
                    telephones.add(cursor.getString(2));
                    customers.add(new Customer(cursor.getString(0),
                            cursor.getString(1),
                            telephones,
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getInt(6),
                            cursor.getString(7),
                            cursor.getString(8),
                            cursor.getInt(9) == 1?true:false));
                    cursor.moveToNext();
                }
                customers.add(new Customer("2",
                        "asdasdas",
                        null,
                        "asdasd",
                        "asdasd",
                        "asdasd",
                        2,
                        "asdasd",
                        "asdasd",
                        true));
            }
            cursor.close();
        }
        sqLiteOpenHelper.close();
        return customers;
    };

    public DeleteCustomers DELETE_CUSTOMER = () -> {
        SQLiteDatabase sqLiteOpenHelper = this.getReadableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            int delete = sqLiteOpenHelper.delete(TABLE_NAME, null, null);
            if(delete > 0)
                return true;
        }
        return false;
    };

    @FunctionalInterface
    public interface AddCustomer{
        boolean execute(Customer customer);
    }

    @FunctionalInterface
    public interface GetCustomers{
        List<Customer> get();
    }

    @FunctionalInterface
    public interface UpdateCustomer{
        boolean execute(Customer customer);
    }

    @FunctionalInterface
    public interface DeleteCustomers{
        boolean execute();
    }
}
