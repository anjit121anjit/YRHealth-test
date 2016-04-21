package com.techies11;

import gcm.QuickstartPreferences;
import gcm.RegistrationIntentService;
import imgLoader.JSONParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import util.ConnectionDetector;
import util.ObjectSerializer;
import adapters.MainAdapter;
import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import appconfig.ConstValue;

public class MainActivity extends ActionBarActivity {
	public SharedPreferences settings;
	public ConnectionDetector cd;
	ArrayList<HashMap<String, String>> newsArray;
	MainAdapter adapter;

	AsyncTask<Void, Void, Void> mRegisterTask;
	public static String email;

	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final String TAG = "MainActivity";

	private BroadcastReceiver mRegistrationBroadcastReceiver;

	DrawerLayout mDrawerLayout;
	NavigationView mNavigationView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationView = (NavigationView) findViewById(R.id.drawerstuff);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

		if(!isNetworkAvailable()){
			// do network operation here
			Intent ino = new Intent(MainActivity.this,NiActivity.class);
			startActivity(ino);
			// Toast.makeText(MainActivity.this, "No Network,Connect to internet first", Toast.LENGTH_LONG).show();

		}else {
			settings = getSharedPreferences(ConstValue.MAIN_PREF, 0);
			cd = new ConnectionDetector(this);

			email = settings.getString("user_email", "");

			mRegistrationBroadcastReceiver = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent) {
					SharedPreferences sharedPreferences =
							PreferenceManager.getDefaultSharedPreferences(context);
					boolean sentToken = sharedPreferences
							.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
					if (sentToken) {

					} else {

					}
				}
			};

			if (checkPlayServices()) {
				if (!email.equalsIgnoreCase("")) {
					// Start IntentService to register this application with GCM.
					Intent intent = new Intent(this, RegistrationIntentService.class);
					startService(intent);
				}
			}

			newsArray = new ArrayList<HashMap<String, String>>();
			try {
				newsArray = (ArrayList<HashMap<String, String>>) ObjectSerializer.deserialize(settings.getString(ConstValue.PREFS_MAIN_CAT, ObjectSerializer.serialize(new ArrayList<HashMap<String, String>>())));
			} catch (IOException e) {
				e.printStackTrace();
			}
			new loadNewsTask().execute(true);

			adapter = new MainAdapter(getApplicationContext(), newsArray);
			GridView gridview = (GridView) findViewById(R.id.gridView1);
			gridview.setAdapter(adapter);
			gridview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
					// TODO Auto-generated method stub
					ConstValue.selected_category = newsArray.get(position);
					Intent intent = new Intent(MainActivity.this, DoctorListActivity.class);
					startActivity(intent);
				}


			});

							}

		mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem menuItem) {
				mDrawerLayout.closeDrawers();

				if (menuItem.getItemId() == R.id.nav_item_home) {

					Intent i = new Intent(MainActivity.this, HomeActivity.class);
					startActivity(i);

				}

				if (menuItem.getItemId() == R.id.nav_item_doctors) {

					Intent i = new Intent(MainActivity.this, MainActivity.class);
					startActivity(i);
				}
				if (menuItem.getItemId() == R.id.nav_item_log_sign) {

					Intent i = new Intent(MainActivity.this, FbLoginActivity.class);
					startActivity(i);
				}
				if (menuItem.getItemId() == R.id.nav_item_appointment) {

					Intent i = new Intent(MainActivity.this, MyAppointmentActivity.class);
					startActivity(i);

				}
				/*if (menuItem.getItemId() == R.id.nav_item_rate_us) {

					Intent intent = null;
					String shareurl = "https://play.google.com/store/apps/details?id=" + "com.techies11";

				}*/
				if (menuItem.getItemId() == R.id.nav_item_doc_admin_log) {

					Uri uri = Uri.parse("http://www.bridgetechnosoft.com/hospital/ServerSite/Source/index.php"); // missing 'http://' will cause crashed
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);

				}

				if (menuItem.getItemId() == R.id.nav_item_abt_us) {

					Intent i = new Intent(MainActivity.this, AboutusActivity.class);
					startActivity(i);
				}
				if (menuItem.getItemId() == R.id.nav_item_contact_us) {

					String mob = "+353899564620";
					Intent intent = new Intent("android.intent.action.CALL");
					Uri data = Uri.parse("tel:"+ mob );
					intent.setData(data);
					startActivity(intent);
				}
				if (menuItem.getItemId() == R.id.nav_item_mail_us) {
					Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
							"mailto","mayankchauhan95@gmail.com", null));
					emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
					emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
					startActivity(Intent.createChooser(emailIntent, "Send email..."));
				}

				return false;
			}

		});

		android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.mytoolbar);
		setSupportActionBar(toolbar);

		ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
				R.string.app_name);



		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mDrawerToggle.syncState();

		return;

	}

	public class loadNewsTask extends AsyncTask<Boolean, Void, ArrayList<HashMap<String, String>>> {

		JSONParser jParser;
		JSONObject json;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
			// TODO Auto-generated method stub
			if (result != null) {
				//adapter.notifyDataSetChanged();

			}
			try {
				settings.edit().putString(ConstValue.PREFS_MAIN_CAT, ObjectSerializer.serialize(newsArray)).commit();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			adapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@SuppressLint("NewApi")
		@Override
		protected void onCancelled(ArrayList<HashMap<String, String>> result) {
			// TODO Auto-generated method stub
			super.onCancelled(result);
		}


		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				Boolean... params) {
			// TODO Auto-generated method stub

			try {
				jParser = new JSONParser();

				if (cd.isConnectingToInternet()) {
					json = jParser.getJSONFromUrl(ConstValue.JSON_MAINCAT);
					if (json.has("data")) {


						if (json.get("data") instanceof JSONArray) {

							JSONArray menus = json.getJSONArray("data");
							if (menus != null) {
								newsArray.clear();
								for (int i = 0; i < menus.length(); i++) {
									JSONObject d = menus.getJSONObject(i);
									HashMap<String, String> map2 = new HashMap<String, String>();
									map2.put("id", d.getString("id"));
									map2.put("title", d.getString("title"));
									map2.put("icon", d.getString("icon"));
									map2.put("iconpath", d.getString("iconpath"));

									newsArray.add(map2);
								}
							}

						}

					}
				} else {
					Toast.makeText(getApplicationContext(), "Please connect mobile with working Internet", Toast.LENGTH_LONG).show();
				}

				jParser = null;
				json = null;

			} catch (Exception e) {
				// TODO: handle exception

				return null;
			}
			return newsArray;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		Button setting = (Button) findViewById(R.id.action_settings);
		Button prfl = (Button) findViewById(R.id.action_profile);
		Button d_plan = (Button) findViewById(R.id.action_dietplan);
		Button rate = (Button) findViewById(R.id.action_rate);
		Button share = (Button) findViewById(R.id.action_share);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {


			case R.id.action_settings :
				Intent in = new Intent(MainActivity.this,SettingsActivity.class);
				startActivity(in);

				break;
			case R.id.action_profile:
				Intent in1 = new Intent(MainActivity.this,ProfileActivity.class);
				startActivity(in1);

				break;
			case R.id.action_dietplan:
				Intent in3 = new Intent(MainActivity.this,DisplayActivity.class);
				startActivity(in3);

				break;
			case R.id.action_rate:
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + this.getPackageName()));
				startActivity(browserIntent);

				break;
			case R.id.action_share:
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + this.getPackageName());
				sendIntent.setType("text/plain");
				startActivity(sendIntent);

				break;
		}
		return false;
	}



	@Override
	protected void onResume() {
		super.onResume();
		LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
				new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
	}
	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	protected void onPause() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
		super.onPause();
	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
		GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
		int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (apiAvailability.isUserResolvableError(resultCode)) {
				apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
						.show();
			} else {
				Log.i(TAG, "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

}