package com.techies11;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayActivity extends ActionBarActivity {

	private DbHelper mHelper;
	private SQLiteDatabase dataBase;

	private TextView bkfst,lnch,dnr;


	private ListView userList;
	private AlertDialog.Builder build;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_activity);

		//userList = (ListView) findViewById(R.id.List);
		bkfst =(TextView)findViewById(R.id.bkfsttxt);
		lnch =(TextView)findViewById(R.id.lunchtxt);
		dnr =(TextView)findViewById(R.id.dinrtxt);


		mHelper = new DbHelper(this);


		//add new record
		findViewById(R.id.btnAdd).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(),
						AddActivity.class);
				i.putExtra("update", false);
				startActivity(i);

			}
		});

		//click to update data
		/*userList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {

				Intent i = new Intent(getApplicationContext(),
						AddActivity.class);
				i.putExtra("Breakfast", user_breakfast.get(arg2));
				i.putExtra("Lunch", user_lunch.get(arg2));
				i.putExtra("Dinner", user_dinner.get(arg2));
				i.putExtra("ID", userId.get(arg2));
				i.putExtra("update", true);
				startActivity(i);

			}
		});

		//long click to delete data
		userList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
										   final int arg2, long arg3) {

				build = new AlertDialog.Builder(DisplayActivity.this);
				build.setTitle("Delete " + user_breakfast.get(arg2) + " "
						+ user_lunch.get(arg2));
				build.setMessage("Do you want to delete ?");
				build.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
												int which) {

								Toast.makeText(
										getApplicationContext(),
										user_breakfast.get(arg2) + " "
												+ user_lunch.get(arg2)
												+ " is deleted.",Toast.LENGTH_LONG).show();

								dataBase.delete(
										DbHelper.TABLE_NAME,
										DbHelper.KEY_ID + "="
												+ userId.get(arg2), null);
								displayData();
								dialog.cancel();
							}
						});

				build.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
												int which) {
								dialog.cancel();
							}
						});
				AlertDialog alert = build.create();
				alert.show();

				return true;
			}
		});*/
	}

	@Override
	protected void onResume() {
		displayData();
		super.onResume();
	}

	/**
	 * displays data from SQLite
	 */
	private void displayData() {
		dataBase = mHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
				+ DbHelper.TABLE_NAME, null);


		if (mCursor.moveToFirst()) {
			do {
				String breakfast=mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_BREAKFAST));
				String lunch=mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_LUNCH));
				String dinner=mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_DINNER));
				bkfst.setText(breakfast);
				lnch.setText(lunch);
				dnr.setText(dinner);


				/*userId.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_ID)));
				user_breakfast.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_BREAKFAST)));
				user_lunch.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_LUNCH)));
				user_dinner.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_DINNER)));*/

			} while (mCursor.moveToNext());
		}

		/*DisplayAdapter disadpt = new DisplayAdapter(DisplayActivity.this,userId, user_breakfast, user_lunch,user_dinner);
		userList.setAdapter(disadpt);*/
		mCursor.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		CommonFunctions common = new CommonFunctions();
		common.menuItemClick(DisplayActivity.this, id);
		return super.onOptionsItemSelected(item);
	}

}
