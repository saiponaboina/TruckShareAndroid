package com.ritul.truckshare.RegisterActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.ritul.truckshare.MainActivity;
import com.ritul.truckshare.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
   private static final String TWITTER_KEY = "nWaaux7Kf6EIvQFO57hwqSVgn";
   private static final String TWITTER_SECRET = "wMu2AdMb9IIMag2Y6Tuo2WdEbHL5LjYf2qDvGGnymxtjH3dG4M";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));
        setContentView(R.layout.activity_splash);

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left);
        ImageView image=(ImageView)findViewById(R.id.imageView);
        image.startAnimation(animation);

       /* Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);*/

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, 2000);



       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

}
