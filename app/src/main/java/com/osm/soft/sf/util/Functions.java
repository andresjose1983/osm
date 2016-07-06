package com.osm.soft.sf.util;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.osm.soft.sf.OsmApplication;
import com.osm.soft.sf.R;
import com.osm.soft.sf.model.Order;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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

    public static String format(final Date date){
        String pattern = "dd/MMM/yyyy hh:mm aaa";
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

    public static void changeColor(final ImageView imageView, final String letter, final int index, final int TYPE){
        float densityDpi = 0;
        if(imageView.getContext().getResources().getBoolean(R.bool.isTable)){
            if (TYPE < 0)
                densityDpi = imageView.getContext().getResources().getDisplayMetrics().densityDpi / 2.75f;
            else
                densityDpi = imageView.getContext().getResources().getDisplayMetrics().densityDpi;
        }else {
            if (TYPE < 0)
                densityDpi = imageView.getContext().getResources().getDisplayMetrics().densityDpi / 3.5f;
            else
                densityDpi = imageView.getContext().getResources().getDisplayMetrics().densityDpi;
        }

        Bitmap bitmap = Bitmap.createBitmap((int)densityDpi, (int)densityDpi, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        imageView.setImageBitmap(bitmap);

        // Circle
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(getColor(index)));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        float measure = densityDpi / 2;
        canvas.drawCircle(measure, measure, measure, paint);

        Paint textPaint = new Paint();
        textPaint.setARGB(255,255,255, 255);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(measure);
        canvas.drawText(letter, canvas.getWidth()/2, canvas.getHeight() / 1.5f , textPaint);
    }

    public static String getColor(final int index){
        switch (index){
            case 0:
                return "#E040FB";
            case 1:
                return "#EDE7F6";
            case 2:
                return "#9FA8DA";
            case 3:
                return "#7C4DFF";
            case 4:
                return "#4DD0E1";
            case 5:
                return "#AED581";
            case 6:
                return "#D4E157";
            case 7:
                return "#FFD54F";
            case 8:
                return "#9E9E9E";
            case 9:
                return "#8D6E63";
        }
        return "#78909C";
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

    public static String format(double value){
        DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
        simbolo.setDecimalSeparator(',');
        simbolo.setGroupingSeparator('.');
        DecimalFormat formateador = new DecimalFormat("###,###.##",simbolo);
        return formateador.format(value);
    }

    public static void setViewSelected(View view){
        TypedValue outValue = new TypedValue();
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            view.getContext().getTheme().resolveAttribute(
                    android.R.attr.selectableItemBackgroundBorderless, outValue, true);
        else
            view.getContext().getTheme().resolveAttribute(
                    android.R.attr.selectableItemBackground, outValue, true);

        view.setBackgroundResource(outValue.resourceId);
    }
}
