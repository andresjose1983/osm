package com.pskloud.osm.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.pskloud.osm.R;

/**
 * Created by Mendez Fernandez on 18/06/2016.
 */
public final class DialogHelper {

    public static void confirm(final Context context, final int content,
                               final DialogInterface.OnClickListener positive){

        AlertDialog.Builder builder = get(context);
        builder.setMessage(content);
                builder.setPositiveButton(R.string.ok, positive);
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    public static void ok(final Context context, final int content){

        AlertDialog.Builder builder = get(context);
        builder.setMessage(content);
        builder.setPositiveButton(R.string.ok, null);
        builder.show();
    }

    private static AlertDialog.Builder get(final Context context){
        return  new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
    }
}
