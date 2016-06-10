package com.pskloud.osm.util;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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

    public static void loadImageCircle(ImageView imageView, final String url){

        if(imageView != null || url != null)
            Glide.with(OsmApplication.getInstance())
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

}
