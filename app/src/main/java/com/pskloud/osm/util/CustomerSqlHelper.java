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
    public static final String CODE  = "code";
    public static final String NAME  = "name";
    public static final String ADDRESS  = "address";
    public static final String PHONE  = "phone";
    public static final String TIN  = "tin";
    public static final String ZONE  = "zone";
    public static final String TYPE  = "taxType";
    public static final String NEW  = "new";
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
                .append(CODE)
                .append(" text, ")
                .append(NAME)
                .append(" text, ")
                .append(ADDRESS)
                .append(" text, ")
                .append(PHONE)
                .append(" text, ")
                .append(TIN)
                .append(" text, ")
                .append(ZONE)
                .append(" text, ")
                .append(TYPE)
                .append(" integer, ")
                .append(NEW)
                .append(" integer, ")
                .append(SYNC)
                .append(" integer) ")
                .toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    final public AddCustomer ADD = (customer)->{
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        if(writableDatabase != null && writableDatabase.isOpen()){
            ContentValues contentValues = setConteValues(customer);
            writableDatabase.insert(TABLE_NAME, null, contentValues);
            writableDatabase.close();
        }

        return true;
    };

    private ContentValues setConteValues(Customer customer){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CODE, customer.getCode());
        contentValues.put(NAME, customer.getName().toUpperCase());
        contentValues.put(ADDRESS, customer.getAddress().toUpperCase());
        if(customer.getPhones() != null)
            contentValues.put(PHONE, customer.getPhones().isEmpty()?"":
                    customer.getPhones().get(0));
        contentValues.put(TIN, customer.getTin());
        contentValues.put(ZONE, customer.getZone());
        contentValues.put(TYPE, customer.getTaxType());
        contentValues.put(NEW, customer.isNew() == true?1:0);
        contentValues.put(SYNC, customer.isSync() == true?1:0);
        return contentValues;
    }

    final public UpdateCustomer UPDATE = (customer)->{
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        if(writableDatabase != null && writableDatabase.isOpen()){
            ContentValues contentValues = setConteValues(customer);

            writableDatabase.update(TABLE_NAME, contentValues, CODE + " = ?",
                    new String[] { customer.getCode() } );
            writableDatabase.close();
        }

        return true;
    };

    final public GetCustomers GET = () -> {
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
                    telephones.add(cursor.getString(3));
                    customers.add(new Customer
                            .Builder()
                    .code(cursor.getString(0))
                    .name(cursor.getString(1))
                    .address(cursor.getString(2))
                    .phones(telephones)
                    .tin(cursor.getString(4))
                    .zone(cursor.getString(5))
                    .taxType(cursor.getInt(6))
                    .isNew(cursor.getInt(7) == 1? true: false)
                    .sync(cursor.getInt(8) == 1? true: false)
                    .isView(false).build());
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        sqLiteOpenHelper.close();
        return customers;
    };

    public DeleteCustomers DELETE = () -> {
        SQLiteDatabase sqLiteOpenHelper = this.getReadableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            sqLiteOpenHelper.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteOpenHelper);
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
        List<Customer> execute();
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
