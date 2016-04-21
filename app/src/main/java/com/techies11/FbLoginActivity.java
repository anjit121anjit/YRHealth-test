/**
 * Copyright 2010-present Facebook.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.techies11;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import appconfig.ConstValue;

/*
import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import util.ConnectionDetector;

public class FbLoginActivity extends FragmentActivity {

	public SharedPreferences settings;
	public ConnectionDetector cd;
	Button btnLogin, btnRegister;
	String deviceid;
	String fullname,emailid,password,phone;
	ProgressDialog dialog;
//    private LoginButton loginButton;
//    CallbackManager callbackManager;
//	AccessTokenTracker accessTokenTracker;
//	AccessToken accessToken;
//	ProfileTracker profileTracker;
	public int RC_SIGN_IN = 101;
	GoogleApiClient mGoogleApiClient;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //this.getActionBar().hide();

//		FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.fblogin);
		MultiDex.install(this);


		//HttpGet httpGet = new HttpGet(“http://192.168.1.12”) ;
		// Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestEmail()
				.build();
		// Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
				.build();
		SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
		signInButton.setSize(SignInButton.SIZE_STANDARD);
		signInButton.setScopes(gso.getScopeArray());
		signInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
				startActivityForResult(signInIntent, RC_SIGN_IN);
			}
		});
//		callbackManager = CallbackManager.Factory.create();
//		accessTokenTracker= new AccessTokenTracker() {
//			@Override
//			protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
//
//			}
//		};

//		profileTracker = new ProfileTracker() {
//			@Override
//			protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
//				displayMessage(newProfile);
//			}
//		};
//
//		accessTokenTracker.startTracking();
//		profileTracker.startTracking();
//
//		 loginButton = (LoginButton) findViewById(R.id.login_button);
//		loginButton.setReadPermissions("user_friends");
//
//		loginButton.registerCallback(callbackManager, callback);

//		//LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email", "user_friends"));

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(getApplicationContext().TELEPHONY_SERVICE);
		deviceid=telephonyManager.getDeviceId();
		
        settings = getSharedPreferences(ConstValue.MAIN_PREF, 0);
		 cd=new ConnectionDetector(this);
		 
		 btnLogin = (Button)findViewById(R.id.buttonLogin);
		
		 btnRegister = (Button)findViewById(R.id.buttonRegister);
		 
		 btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FbLoginActivity.this,Login2Activity.class);
				startActivity(intent);
				
			}
		});
		 btnRegister.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(FbLoginActivity.this,RegisterActivity.class);
					startActivity(intent);
					
				}
			});
		 
		 Button continuebtn = (Button)findViewById(R.id.button1);
		 continuebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(FbLoginActivity.this,MainActivity.class);
				 startActivity(intent);
				 finish();
			}
		});



    }





    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    
    
	class registerTask extends AsyncTask<Boolean, Void, String> {
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(FbLoginActivity.this, "", 
                    "Loading. Please wait...", true);
			super.onPreExecute();

		}
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);

		}
		@Override
		protected String doInBackground(Boolean... params) {
			// TODO Auto-generated method stub
		
			String responseString = null;
			 
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(ConstValue.JSON_REGISTER);
            //if (getEmailId()==null) {
            //	Toast.makeText(getApplicationContext(),"Create new Account Please",Toast.LENGTH_LONG).show();
            //	finish();
			//}

            try {
            
            	
            	 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	    	        nameValuePairs.add(new BasicNameValuePair("email", emailid));
	    	        nameValuePairs.add(new BasicNameValuePair("name",fullname));
	    	        nameValuePairs.add(new BasicNameValuePair("phone",phone));
	    	        nameValuePairs.add(new BasicNameValuePair("password",password));
	    	        nameValuePairs.add(new BasicNameValuePair("imei",deviceid));
	    	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

 
                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                	InputStream is = r_entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
        					is, "iso-8859-1"), 8);
        			StringBuilder sb = new StringBuilder();
        			String line = null;
        			while ((line = reader.readLine()) != null) {
        				sb.append(line + "\n");
        			}
        			is.close();
        			String json = sb.toString();
        			JSONObject jObj = new JSONObject(json);
        			if (jObj.getString("responce").equalsIgnoreCase("success")) {
	        			JSONObject data = jObj.getJSONObject("data");
	        			if (!data.getString("id").equalsIgnoreCase("")) {
	        				settings.edit().putString("user_id", data.getString("id")).commit();
		                    settings.edit().putString("user_email",data.getString("email")).commit();
		                    settings.edit().putString("user_name", data.getString("name")).commit();
		                    settings.edit().putString("newslater", data.getString("newslater")).commit();
		                    settings.edit().putString("notification", data.getString("notification")).commit();
		                    
		                    //gcmAppRegister();
		                    
						}
	        			
        			}else{
        				responseString = jObj.getString("data"); 
        			}
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }
 
            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }catch (JSONException e) {
    			Log.e("JSON Parser", "Error parsing data " + e.toString());
    		}
 
            return responseString;
			
			
		}
		

		@Override
		protected void onPostExecute(String result) {
			if(result!=null){
				Toast.makeText(getApplicationContext(), "Error :"+result, Toast.LENGTH_LONG).show();
				//AlertDialogManager am = new AlertDialogManager(); 
				//am.showAlertDialog(getApplicationContext(), "Error", result, true);
			}else{
				Intent intent = new Intent(FbLoginActivity.this,MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				finish();
			}
			dialog.dismiss();
		}
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
		}
		
	}


/*	private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
		@Override
		public void onSuccess(LoginResult loginResult) {
			AccessToken accessToken = loginResult.getAccessToken();
			Profile profile = Profile.getCurrentProfile();
			displayMessage(profile);
		}

		@Override
		public void onCancel() {

		}

		@Override
		public void onError(FacebookException e) {

		}
	};
	private void displayMessage(Profile profile){
		if(profile != null){
			//textView.setText(profile.getName());
			Toast.makeText(getApplicationContext(),profile.getName(),Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		callbackManager.onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onStop() {
		super.onStop();
		accessTokenTracker.stopTracking();
		profileTracker.stopTracking();
	}

	@Override
	public void onResume() {
		super.onResume();
		Profile profile = Profile.getCurrentProfile();
		displayMessage(profile);
	}
*/
/*	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		profileTracker.stopTracking();
		accessTokenTracker.stopTracking();
	}
*/


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			handleSignInResult(result);
		}
	}
	private void handleSignInResult(GoogleSignInResult result) {
		Log.d("Google Signin", "handleSignInResult:" + result.isSuccess());
		if (result.isSuccess()) {
			// Signed in successfully, show authenticated UI.
			GoogleSignInAccount acct = result.getSignInAccount();
			emailid = acct.getEmail();
			fullname = acct.getDisplayName();
			password = acct.getId();
			new registerTask().execute(true);

		} else {
			// Signed out, show unauthenticated UI.

		}
	}

}
