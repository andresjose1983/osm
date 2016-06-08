package com.pskloud.osm;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mendez Fernandez on 07/06/2016.
 */
public abstract class DefaultActivity extends AppCompatActivity {


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        init();
        setUp();
    }

    public abstract void init();

    public abstract void setUp();
}
