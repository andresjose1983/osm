package com.pskloud.osm.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pskloud.osm.BuildConfig;
import com.pskloud.osm.model.Locality;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mendez Fernandez on 18/06/2016.
 */
public class LocalitySqlHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "osm.db";
    public static final String TABLE_NAME  = "locality";
    public static final String CODE  = "code";
    public static final String NAME  = "name";

    public LocalitySqlHelper(Context context) {
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
                .append(" text) ")
                .toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    final public AddLocality ADD_LOCALITY = (customer)->{
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        if(writableDatabase != null && writableDatabase.isOpen()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(CODE, customer.getCode().toUpperCase());
            contentValues.put(NAME, customer.getName().toUpperCase());
            writableDatabase.close();
        }

        return true;
    };

    final public GetLocalities GET_LOCALITIES = () -> {
        List<Locality> localities = new ArrayList<>();
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
                while(cursor.isAfterLast() == false){
                    localities.add(new Locality.Builder()
                            .code(cursor.getString(0))
                            .name(cursor.getString(1)).build());
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        sqLiteOpenHelper.close();
        return localities;
    };

    public DeleteLocalities DELETE_LOCALITY = () -> {
        SQLiteDatabase sqLiteOpenHelper = this.getReadableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            int delete = sqLiteOpenHelper.delete(TABLE_NAME, null, null);
            if(delete > 0)
                return true;
        }
        return false;
    };

    @FunctionalInterface
    public interface AddLocality{
        boolean execute(Locality locality);
    }

    @FunctionalInterface
    public interface GetLocalities{
        List<Locality> get();
    }

    @FunctionalInterface
    public interface DeleteLocalities{
        boolean execute();
    }
}
