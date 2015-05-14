package ru.fragmentcastle.bluetooth;

import java.util.ArrayList;

import ru.fragmentcastle.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter {
	
	ArrayList<String> mes;
	LayoutInflater inflater;
	
	public ChatAdapter(Context context){
		mes=new ArrayList<String>();
		mes.add("1Ghbdtn");
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return mes.size();
	}

	public void add(String s){
		mes.add(s);
		notifyDataSetChanged();
	}
	
	@Override
	public String getItem(int arg0) {
		return mes.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view == null){
			view = inflater.inflate(R.layout.list_chat, parent, false);
			LinearLayout lin;
			((TextView) view.findViewById(R.id.mes)).setText(mes.get(position));
			lin=(LinearLayout)view.findViewById(R.id.lin);
			lin.setBackgroundColor(getColor(mes.get(position).charAt(0)));
		}
		return view;
	}

	private int getColor(int a){
		if (a==0) return Color.BLUE;
		if (a==1) return Color.YELLOW;
		if (a==2) return Color.GREEN;
		if (a==3) return Color.RED;
		return Color.CYAN;

	}
	
}
