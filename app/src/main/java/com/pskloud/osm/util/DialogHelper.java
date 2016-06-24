package com.pskloud.osm.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

import com.pskloud.osm.R;

/**
 * Created by Mendez Fernandez on 18/06/2016.
 */
public final class DialogHelper {

    private static AlertDialog builder;

    public static void confirm(final Context context, final int content,
                               final DialogInterface.OnClickListener positive){

        if(builder == null)
            builder = get(context);
        else {
            builder.dismiss();
            builder = null;
            builder = get(context);
        }
        builder.setMessage(context.getString(content));

        builder.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.ok), positive );
        builder.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.cancel), (dialogInterface, i) -> {
            dialogInterface.dismiss();
        } );

        builder.show();
    }

    public static void ok(final Context context, final int content){

        if(builder == null)
            builder = get(context);
        else {
            builder.dismiss();
            builder = null;
            builder = get(context);
        }
        builder.setMessage(context.getString(content));
        builder.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.ok), (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.show();
    }

    private static AlertDialog get(final Context context){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            return  new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle).create();
        else
            return  new AlertDialog.Builder(context).create();
    }
}
