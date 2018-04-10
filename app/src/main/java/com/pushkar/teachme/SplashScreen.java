package com.pushkar.teachme;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.setContentView(R.layout.activity_splash_screen);

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo
                (ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            //we are connected to a network
            connected = true;
            System.out.println("connection status"+connected);
            Toast toast = Toast.makeText(getApplicationContext(),""+connected, Toast.LENGTH_LONG);
            toast.show();

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    Intent start_login = new Intent(SplashScreen.this,Login.class);
                    startActivity(start_login);
                }
            }, 2000);
        }
        else{
            connected = false;
            Toast toast = Toast.makeText(getApplicationContext(),""+connected, Toast.LENGTH_LONG);
            toast.show();
            System.out.println("connection status"+connected);
        }

    }
}
