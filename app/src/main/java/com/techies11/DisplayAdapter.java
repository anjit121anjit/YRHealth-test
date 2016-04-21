package com.techies11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> id;
	private ArrayList<String> Breakfast;
	private ArrayList<String> Lunch;
	private ArrayList<String> Dinner;


	public DisplayAdapter(Context c, ArrayList<String> id, ArrayList<String> breakfast, ArrayList<String> lunch, ArrayList<String> dinner) {
		this.mContext = c;

		this.id = id;
		this.Breakfast = breakfast;
		this.Lunch = lunch;
		this.Dinner = dinner;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return id.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int pos, View child, ViewGroup parent) {
		Holder mHolder;
		LayoutInflater layoutInflater;
		if (child == null) {
			layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child = layoutInflater.inflate(R.layout.listcell, null);
			mHolder = new Holder();
			mHolder.txt_id = (TextView) child.findViewById(R.id.txt_id);
			mHolder.txt_Name = (TextView) child.findViewById(R.id.txt_breakfast);
			mHolder.txt_Num = (TextView) child.findViewById(R.id.txt_lunch);
			mHolder.txt_Msg = (TextView) child.findViewById(R.id.txt_dinner);
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}
		mHolder.txt_id.setText(id.get(pos));
		mHolder.txt_Name.setText(Breakfast.get(pos));
		mHolder.txt_Num.setText(Lunch.get(pos));
		mHolder.txt_Msg.setText(Dinner.get(pos));

		return child;
	}

	public class Holder {
		TextView txt_id;
		TextView txt_Name;
		TextView txt_Num;
		TextView txt_Msg;
	}

}


