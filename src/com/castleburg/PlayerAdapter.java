package com.castleburg;

import java.util.Arrays;
import java.util.Comparator;

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

import com.castleburg.logic.Player;

public class PlayerAdapter extends BaseAdapter {

	Context ctx;
	LayoutInflater inflater;
	Player[] ar;
	String queue;

	public PlayerAdapter(Context context, Player[] mar,String mqueue){
		ar=mar;
		queue=mqueue;
		ctx=context;
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Arrays.sort(ar, new Comparator<Player>() {
			@Override
			public int compare(Player a,Player b) {
				if (a.num>b.num) return 1;
				if (a.num<b.num) return -1;
				return 0;
			}
		});
	}	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ar.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.spis_player, parent, false);
			((TextView) view.findViewById(R.id.tess)).setText(ar[queue.charAt(position)-'0'].win+"");  
			((TextView) view.findViewById(R.id.textView2)).setText(ar[queue.charAt(position)-'0'].war+"");
			((TextView) view.findViewById(R.id.textView3)).setText(ar[queue.charAt(position)-'0'].wood+"");
			((TextView) view.findViewById(R.id.textView4)).setText(ar[queue.charAt(position)-'0'].gold+"");
			((TextView) view.findViewById(R.id.textView5)).setText(ar[queue.charAt(position)-'0'].stone+"");
			((TextView) view.findViewById(R.id.textView6)).setText(ar[queue.charAt(position)-'0'].plus+"");
			((LinearLayout)view.findViewById(R.id.lin)).setBackgroundColor(getColor(queue.charAt(position)-'0'));
			LinearLayout tessera=((LinearLayout)view.findViewById(R.id.tessera));
			gettess(queue.charAt(position)-'0',tessera);

		}
		//Log.d("TAG", position+"");


		return view;
	}

	private int getColor(int a){
		if (a==0) return Color.BLUE;
		if (a==1) return Color.YELLOW;
		if (a==2) return Color.GREEN;
		if (a==3) return Color.RED;
		return 0;

	}

	//обновлние кубиков игрока
	public void gettess(int num,LinearLayout lin_comb){
		String tess=ar[num].tess.toString();
		for (int j=0;j<tess.length();j++){
			lin_comb.addView(getImage(tess.charAt(j)));
		}
	}

	//создание картнки какого-то кубика
	private ImageView getImage(char c){
		LayoutParams linLayoutParam = new LayoutParams(LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT);
		ImageView image=new ImageView(ctx);
		image.setLayoutParams(linLayoutParam);
		switch (c){
		case '1':image.setImageResource(R.drawable.tess1);
		break;
		case '2':image.setImageResource(R.drawable.tess2);
		break;
		case '3':image.setImageResource(R.drawable.tess3);
		break;
		case '4':image.setImageResource(R.drawable.tess4);
		break;
		case '5':image.setImageResource(R.drawable.tess5);
		break;
		case '6':image.setImageResource(R.drawable.tess6);
		break;
		}
		return image;
	}

}
