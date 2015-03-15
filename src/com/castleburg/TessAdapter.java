package com.castleburg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.castleburg.logic.Tessera;

public class TessAdapter extends BaseAdapter {
	
	Context ctx;
	LayoutInflater inflater;
	Tessera tess;
	int num;


	public TessAdapter(Context context,Tessera mtess,int mnum ){
		ctx=context;
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		num=mnum;
		tess=mtess;
		
	}
	
	@Override
	public int getCount() {
		return tess.steps[num].size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view =convertView;
	    if (view == null) {
	    	
	      view = (LinearLayout) inflater.inflate(R.layout.tess, parent, false);
	     
	    }
	    
	    LinearLayout lin=(LinearLayout) view.findViewById(R.id.lin);
	    lin.removeAllViews();
	      String comb=tess.toComb(num, position);
	    	for (int j=0;j<comb.length();j++){
	    		lin.addView(getImage(comb.charAt(j)));
	    	}
		return view;
	}
	
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
		case 'p':image.setImageResource(R.drawable.market);
		break;
		case 'l':image.setImageResource(R.drawable.plus);
		break;
		}
		return image;
	}

}
