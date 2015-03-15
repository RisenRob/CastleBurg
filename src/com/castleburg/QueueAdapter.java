package com.castleburg;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class QueueAdapter {
	
	LayoutInflater lInflater;
	Context ctx;
	String queue;
	
	QueueAdapter(Context context, String mqueue) {
		Context ctx;
		queue=mqueue;
	    ctx = context;
	    lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	public void SetView(View v){
	View item = lInflater.inflate(R.layout.spisok, (LinearLayout)v, true);
	((LinearLayout) item.findViewById(R.id.lin1)).setBackgroundColor(getColor(queue.charAt(0)));
	((LinearLayout) item.findViewById(R.id.lin2)).setBackgroundColor(getColor(queue.charAt(1)));
	((LinearLayout) item.findViewById(R.id.lin3)).setBackgroundColor(getColor(queue.charAt(2)));
	}
	
	
	private int getColor(char a){
		if (a=='0') return Color.BLUE;
		if (a=='1') return Color.YELLOW;
		if (a=='2') return Color.GREEN;
		if (a=='3') return Color.RED;
		return 0;
		
	}

}
