package com.pskloud.osm;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Mendez Fernandez on 07/06/2016.
 */
public abstract class DefaultActivity extends AppCompatActivity {


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        setUp();
    }

    protected abstract void init();

    protected abstract void setUp();

    protected void showSnackBar(View view, int message) {
        Snackbar snackbar = Snackbar.make(view, getString(message), Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    protected void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
