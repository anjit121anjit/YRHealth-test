package com.techies11;

import util.ConnectionDetector;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;

import appconfig.ConstValue;

public class Splash extends Activity {
	//public SharedPreferences settings;
	//public ConnectionDetector cd;
	private static int SPLASH_TIME_OUT = 4000;
	//@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		//this.getActionBar();
		
		/* settings = getSharedPreferences(ConstValue.MAIN_PREF, 0);
		 cd=new ConnectionDetector(this);
		 
		 if(settings.getString("user_id", "").equalsIgnoreCase("")){
			 Intent intent = new Intent(Splash.this,FbLoginActivity.class);
			 startActivity(intent);
			 finish();
		 }else{
			 
			 Intent intent = new Intent(Splash.this,MainActivity.class);
			 startActivity(intent);
			 finish();
		 }
	}
	
}
*/
		new Handler().postDelayed(new Runnable() {

			/*
            * Showing splash screen with a timer. This will be useful when you
            * want to show case your app logo / company
            */
			@Override
			public void run() {
// TODO Auto-generated method stu

				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(Splash.this,HomeActivity.class);
				startActivity(i);

				//Close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}
}