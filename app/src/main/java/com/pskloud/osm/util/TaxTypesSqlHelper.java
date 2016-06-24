package com.pskloud.osm.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.pskloud.osm.BuildConfig;
import com.pskloud.osm.model.TypesOfTax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mendez Fernandez on 18/06/2016.
 */
public class TaxTypesSqlHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "osm.db";
    public static final String TABLE_NAME  = "tax";
    public static final String CODE  = "id";
    public static final String NAME  = "name";

    public TaxTypesSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, BuildConfig.VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(new StringBuilder()
                .append("create table ")
                .append(TABLE_NAME)
                .append(" ( ")
                .append(CODE)
                .append(" integer, ")
                .append(NAME)
                .append(" text) ")
                .toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    final public AddTaxType ADD = (id, description)->{
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        if(writableDatabase != null && writableDatabase.isOpen()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(CODE, id);
            contentValues.put(NAME, description);
            writableDatabase.insert(TABLE_NAME, null, contentValues);

            writableDatabase.close();
        }

        return true;
    };

    final public GetTaxTypes GET = () -> {
        List<TypesOfTax> typesOfTaxes = new ArrayList<>();

        SQLiteDatabase sqLiteOpenHelper = this.getReadableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            try{
                Cursor cursor = sqLiteOpenHelper.rawQuery(new StringBuilder()
                        .append("select * from ")
                        .append(TABLE_NAME)
                        .append(" order by ")
                        .append(NAME)
                        .append(" asc")
                        .toString(), null);
                if(cursor != null){
                    cursor.moveToFirst();
                    while(cursor.isAfterLast() == false){
                        typesOfTaxes.add(new TypesOfTax.Builder().code(cursor.getInt(0))
                        .name(cursor.getString(1)).build());
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }catch(SQLException e){
                if(BuildConfig.DEBUG)
                    Log.e(TaxTypesSqlHelper.class.getCanonicalName(), e.getMessage());
            }
        }
        sqLiteOpenHelper.close();
        return typesOfTaxes;
    };

    public DeleteTaxType DELETE = () -> {
        SQLiteDatabase sqLiteOpenHelper = this.getReadableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            sqLiteOpenHelper.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteOpenHelper);
            return true;
        }
        return false;
    };

    @FunctionalInterface
    public interface AddTaxType{
        boolean execute(int id, String description);
    }

    @FunctionalInterface
    public interface GetTaxTypes{
        List<TypesOfTax> execute();
    }

    @FunctionalInterface
    public interface DeleteTaxType{
        boolean execute();
    }
}
