package com.techies11;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends ActionBarActivity implements OnClickListener {
	private Button btn_save;
	private EditText edit_breakfast,edit_lunch,edit_dinner;
	private DbHelper mHelper;
	private SQLiteDatabase dataBase;
	private String id,breakfast,lunch,dinner;
	private boolean isUpdate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_activity);

		btn_save=(Button)findViewById(R.id.save_btn);
		edit_breakfast=(EditText)findViewById(R.id.frst_editTxt);
		edit_lunch=(EditText)findViewById(R.id.last_editTxt);
		edit_dinner = (EditText) findViewById(R.id.sms_editTxt);

		isUpdate=getIntent().getExtras().getBoolean("update");
		if(isUpdate)
		{
			id=getIntent().getExtras().getString("ID");
			breakfast=getIntent().getExtras().getString("Name");
			lunch=getIntent().getExtras().getString("Num");
			dinner=getIntent().getExtras().getString("Msg");
			edit_breakfast.setText(breakfast);
			edit_lunch.setText(lunch);
			edit_dinner.setText(dinner);

		}

		btn_save.setOnClickListener(this);

		mHelper=new DbHelper(this);

	}

	// saveButton click event
	public void onClick(View v) {
		breakfast=edit_breakfast.getText().toString().trim();
		lunch=edit_lunch.getText().toString().trim();
		dinner=edit_dinner.getText().toString().trim();
		if(breakfast.length()>0 && lunch.length()>0 && dinner.length()>0)
		{
			//if()
			saveData();
		}
		else
		{
			AlertDialog.Builder alertBuilder=new AlertDialog.Builder(AddActivity.this);
			alertBuilder.setTitle("Invalid Data");
			alertBuilder.setMessage("Please,Enter Your Full Diet Plan!");
			alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();

				}
			});
			alertBuilder.create().show();
		}

	}

	/**
	 * save data into SQLite
	 */
	private void saveData(){
		dataBase=mHelper.getWritableDatabase();
		ContentValues values=new ContentValues();

		values.put(DbHelper.KEY_BREAKFAST,breakfast);
		values.put(DbHelper.KEY_LUNCH,lunch );
		values.put(DbHelper.KEY_DINNER,dinner );

		System.out.println("");
		if(isUpdate)
		{
			//update database with new data
			dataBase.update(DbHelper.TABLE_NAME, values, DbHelper.KEY_ID+"="+id, null);
		}
		else
		{
			//insert data into database
			dataBase.insert(DbHelper.TABLE_NAME, null, values);
		}
		//close database
		dataBase.close();
		finish();

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
		common.menuItemClick(AddActivity.this, id);
		return super.onOptionsItemSelected(item);
	}
}