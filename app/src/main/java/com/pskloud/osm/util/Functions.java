package com.pskloud.osm.util;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.pskloud.osm.OsmApplication;
import com.pskloud.osm.R;
import com.pskloud.osm.model.Order;
import com.pskloud.osm.model.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by andres on 09/06/16.
 */
public final class Functions {

    private static final String STATUS = "STATUS";

    public static void loadImageCircle(ImageView imageView, final String url){

        if(imageView != null || url != null)
            Glide.with(imageView.getContext())
                .load(url)
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_file_download_black_48dp)
                .animate(animationObject)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(OsmApplication.getInstance()
                                        .getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    static ViewPropertyAnimation.Animator animationObject = view -> {
            // if it's a custom view class, cast it here
            // then find subviews and do the animations
            // here, we just use the entire view for the fade animation
            view.setAlpha( 0f );

            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat( view, "alpha", 0f, 1f );
            fadeAnim.setDuration( 2500 );
            fadeAnim.start();
    };

    public static List<Product> getProduct(){
        List<Product> mProducts = new ArrayList<>();

        mProducts.add(new Product("70240216", "1K TEMPSISTOR 1% P/CS-800S", 4400, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("8A-14X ", "8A-14X CABLE DIM-DIM 8 CONTACTOS", 3400, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("Z-ML024-036", "ACCESORIOS DE LUZ MYSTIC LED", 4500, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("TAC-MP3M", "ADAPTADOR ENTRADA USB-MP3 PARA CONSOLAS", 21500, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("PN-USB-BLUE", "ADAPTADOR USB BLUETOOTH PARA CORNETAS", 1850, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("222012", "AGARRADERA DE GOMA NEGRA TIRADOR", 1050, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("44-2035", "AGARRADERA NEGRA PARA CORNETA 44-2035", 1650, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("44-2035", "AGARRADERA NEGRA PARA CORNETA 44-2035", 1650, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("44-2035", "AGARRADERA NEGRA PARA CORNETA 44-2035", 1650, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("44-2035", "AGARRADERA NEGRA PARA CORNETA 44-2035", 1650, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("44-2035", "AGARRADERA NEGRA PARA CORNETA 44-2035", 1650, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("44-2035", "AGARRADERA NEGRA PARA CORNETA 44-2035", 1650, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("44-2035", "AGARRADERA NEGRA PARA CORNETA 44-2035", 1650, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("44-2035", "AGARRADERA NEGRA PARA CORNETA 44-2035", 1650, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("44-2035", "AGARRADERA NEGRA PARA CORNETA 44-2035", 1650, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));
        mProducts.add(new Product("44-2035", "AGARRADERA NEGRA PARA CORNETA 44-2035", 1650, 0,
                "http://api.androidhive.info/images/glide/medium/deadpool.jpg"));

        return mProducts;

    }

    public static List<Order> getOrder(){

        List<Order> orders = new ArrayList<>();
        orders.add(new Order("001", new Date(), 5));
        /*orders.add(new Order("002", new Date(), 15));
        orders.add(new Order("003", new Date(), 3));
        orders.add(new Order("004", new Date(), 4));
        orders.add(new Order("005", new Date(), 2));
        orders.add(new Order("006", new Date(), 1));
        orders.add(new Order("007", new Date(), 10));
        orders.add(new Order("008", new Date(), 12));
        orders.add(new Order("009", new Date(), 6));
        orders.add(new Order("010", new Date(), 8));*/

        return orders;
    }

    public static String format(final Date date){
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);

        // formatting
        return format.format(date);
    }

    public static boolean getStatus(final Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(STATUS, false);
    }


    public static void setStatus(final Context context, boolean status){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(STATUS, status);
        editor.commit();
    }

    public static boolean checkInternetConnection(final Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo i = connectivityManager.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;
        return true;
    }

    public static void changeColor(final ImageView imageView, final String letter, final int index){
        int densityDpi = getDensity(imageView.getContext());

        Bitmap bitmap = Bitmap.createBitmap(densityDpi, densityDpi, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        imageView.setImageBitmap(bitmap);

        // Circle
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(getColor(index)));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(1);
        float x = densityDpi / 2;
        float y = densityDpi / 2;
        float radius = densityDpi / 2;
        canvas.drawCircle(x, y, radius, paint);

        Paint textPaint = new Paint();
        textPaint.setARGB(255,255,255, 255);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(densityDpi / 2);
        canvas.drawText(letter, canvas.getWidth()/2, canvas.getHeight() / 1.5f , textPaint);
    }

    private static String getColor(final int index){
        switch (index){
            case 0:
                return "#0277bd";
            case 1:
                return "#cddc39";
            case 2:
                return "#66bb6a";
            case 3:
                return "#76ff03";
            case 4:
                return "#ef6c00";
            case 5:
                return "#ffff00";
            case 6:
                return "#d84315";
            case 7:
                return "#424242";
            case 8:
                return "#607d8b";
            case 9:
                return "#039be5";
        }
        return "#ff1744";
    }

    private static int getDensity(final Context context){
        float density = context.getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            return 180;
        }
        if (density >= 3.0) {
            return 135;
        }
        if (density >= 2.0) {
            return 90;
        }
        if (density >= 1.5) {
            return 68;
        }
        if (density >= 1.0) {
            return 45;
        }
        return 35;
    }

}
