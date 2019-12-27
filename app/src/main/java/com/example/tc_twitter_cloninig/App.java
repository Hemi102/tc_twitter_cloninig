package com.example.tc_twitter_cloninig;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("tEryoa5x9BQRmptpROLewmpo1foH7XGAChgBpH6B")
                // if defined
                .clientKey("y6bNTpOvfkAAZYumk9XB0SqLpvhzvTUtDpuePkmG")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }
}
