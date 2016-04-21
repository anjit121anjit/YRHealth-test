package com.techies11;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	static String DATABASE_NAME="userdata";
	public static final String TABLE_NAME="dietplan";
	public static final String KEY_BREAKFAST="breakfast";
	public static final String KEY_LUNCH="lunch";
	public static final String KEY_DINNER="dinner";
	public static final String KEY_ID="id";
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_BREAKFAST+" TEXT,"+KEY_LUNCH+" TEXT, "+KEY_DINNER+" TEXT)";
		db.execSQL(CREATE_TABLE);

	}



	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		onCreate(db);

	}

}