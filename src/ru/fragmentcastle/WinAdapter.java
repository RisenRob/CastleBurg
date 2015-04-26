package ru.fragmentcastle;

import java.util.Arrays;
import java.util.Comparator;

import ru.fragmentcastle.logic.Player;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WinAdapter extends BaseAdapter {

	Context ctx;
	LayoutInflater inflater;
	Player[] ar;
	
	
	public WinAdapter(Context context, Player[] mar){
		ar=mar;
		ctx=context;
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Arrays.sort(ar, new Comparator<Player>() {
			@Override
			public int compare(Player a,Player b) {
				if (a.win<b.win) return 1;
				if (a.win>b.win) return -1;
				return 0;
			}
		});
	}
	
	@Override
	public int getCount() {
		return ar.length;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.adapter_win, parent, false);
			((ImageView)view.findViewById(R.id.imageView7)).setImageResource(getImage(position));
			((TextView) view.findViewById(R.id.tess)).setText(ar[position].win+"");  
			((TextView) view.findViewById(R.id.textView2)).setText(ar[position].war+"");
			((TextView) view.findViewById(R.id.textView3)).setText(ar[position].wood+"");
			((TextView) view.findViewById(R.id.textView4)).setText(ar[position].gold+"");
			((TextView) view.findViewById(R.id.textView5)).setText(ar[position].stone+"");
			((TextView) view.findViewById(R.id.textView6)).setText(ar[position].plus+"");
			((LinearLayout)view.findViewById(R.id.lin_num)).setBackgroundColor(getColor(ar[position].num));
		}
		return view;
	}
	
	private int getColor(int a){
		if (a==0) return Color.BLUE;
		if (a==1) return Color.YELLOW;
		if (a==2) return Color.GREEN;
		if (a==3) return Color.RED;
		return 0;

	}
	
	private int getImage(int c){
		switch (c){
		case 0:return R.drawable.frist;
		case 1:return R.drawable.second;
		case 2:return R.drawable.fird;
		}
		return R.drawable.promed;
	}

}
