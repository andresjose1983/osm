package com.osm.soft.sf.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.osm.soft.sf.BuildConfig;
import com.osm.soft.sf.model.Locality;

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

    final public AddLocality ADD = (locality)->{
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        if(writableDatabase != null && writableDatabase.isOpen()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(CODE, locality.getCode().toUpperCase());
            contentValues.put(NAME, locality.getName().toUpperCase());
            writableDatabase.insert(TABLE_NAME, null, contentValues);

            writableDatabase.close();
        }

        return true;
    };

    final public GetLocalities GET = () -> {
        List<Locality> localities = new ArrayList<>();
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
                        localities.add(new Locality.Builder()
                                .code(cursor.getString(0))
                                .name(cursor.getString(1)).build());
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }catch(SQLException e){
                if(BuildConfig.DEBUG)
                    Log.e(LocalitySqlHelper.class.getCanonicalName(), e.getMessage());
            }
        }
        sqLiteOpenHelper.close();
        return localities;
    };

    public DeleteLocalities DELETE = () -> {
        SQLiteDatabase sqLiteOpenHelper = this.getReadableDatabase();
        if(sqLiteOpenHelper != null && sqLiteOpenHelper.isOpen()){
            sqLiteOpenHelper.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteOpenHelper);
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
        List<Locality> execute();
    }

    @FunctionalInterface
    public interface DeleteLocalities{
        boolean execute();
    }
}
