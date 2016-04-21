package com.techies11;

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
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import appconfig.ConstValue;

public class RegisterActivity extends ActionBarActivity {
	Button btnRegister;
	EditText editEmail,editPhone,editPassword,editName;
	ProgressDialog dialog;
	String deviceid;
	public SharedPreferences settings;
	public ConnectionDetector cd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		settings = getSharedPreferences(ConstValue.MAIN_PREF, 0);
		 cd=new ConnectionDetector(this);
		 //this.getActionBar().hide();
		 //ActionBar actionBar = getActionBar();
		 //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33ffffff")));
		 
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(getApplicationContext().TELEPHONY_SERVICE);
		deviceid=telephonyManager.getDeviceId();
		
		editName = (EditText)findViewById(R.id.editTextFullName);
		editEmail = (EditText)findViewById(R.id.editTextEmail);
		editPassword = (EditText)findViewById(R.id.editTextPassword);
		editPhone = (EditText)findViewById(R.id.editTextMobile);
		
		btnRegister = (Button)findViewById(R.id.buttonRegister);
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new registerTask().execute(true);
			}
		});
	}

	class registerTask extends AsyncTask<Boolean, Void, String> {
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(RegisterActivity.this, "", 
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
	    	        nameValuePairs.add(new BasicNameValuePair("email", editEmail.getText().toString()));
	    	        nameValuePairs.add(new BasicNameValuePair("name",editName.getText().toString()));
	    	        nameValuePairs.add(new BasicNameValuePair("phone",editPhone.getText().toString()));
	    	        nameValuePairs.add(new BasicNameValuePair("password",editPassword.getText().toString()));
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
				Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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

}
