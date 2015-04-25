package ru.fragmentcastle;

import ru.fragmentcastle.logic.Monstr;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MonstrAdapter extends BaseAdapter {

	Context ctx;
	LayoutInflater inflater;
	String[] win_monstr,lose_monstr;
	boolean win;


	public MonstrAdapter(Context context, Monstr monstr,boolean mwin){
		ctx=context;
		win=mwin;
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (win) win_monstr=monstr.win_monstr(); else lose_monstr=monstr.lose_monstr();
	}

	@Override
	public int getCount() {
		if (win) return win_monstr.length; else return lose_monstr.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) convertView = (LinearLayout) inflater.inflate(R.layout.adapter_monstr, parent, false);
		if (win){
			((TextView)convertView.findViewById(R.id.textView1)).setText(win_monstr[position].charAt(0)+"");
			((ImageView)convertView.findViewById(R.id.imageView1)).setImageResource(getImage(win_monstr[position].charAt(1)));
		} else {
			((TextView)convertView.findViewById(R.id.textView1)).setText(lose_monstr[position].charAt(0)+"");
			((ImageView)convertView.findViewById(R.id.imageView1)).setImageResource(getImage(lose_monstr[position].charAt(1)));
		}
		return convertView;
	}

	private int getImage(char c){
		switch (c){
		case 'w':return R.drawable.wood;
		case 's':return R.drawable.stone;
		case 'g':return R.drawable.gold;
		case 'p':return R.drawable.winpoint;
		}
		return 0;
	}

}
