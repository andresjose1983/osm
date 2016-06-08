package com.pskloud.osm;

import android.app.Application;

/**
 * Created by andres on 08/06/16.
 */
public class OsmApplication extends Application{

    private static OsmApplication ourInstance = new OsmApplication();

    public static OsmApplication getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
