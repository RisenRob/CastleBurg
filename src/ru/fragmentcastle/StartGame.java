package ru.fragmentcastle;

import ru.fragmentcastle.logic.arPlayer;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class StartGame extends DialogFragment {


	Button next,prev,start;
	TextView tv;
	int cur=2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.dialog_startgame,container,false);
		Dialog dial=getDialog();
		dial.setTitle("Количество игроков");
		tv=(TextView)v.findViewById(R.id.textView1);
		next=(Button)v.findViewById(R.id.button1);
		prev=(Button)v.findViewById(R.id.button2);
		start=(Button)v.findViewById(R.id.button3);
		next.setOnClickListener(click);
		prev.setOnClickListener(click);
		start.setOnClickListener(click);
		tv.setText(cur+"");
		return v;
	}

	View.OnClickListener click= new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			switch (v.getId()){
			case R.id.button1:
				cur--;
				if (cur<2) cur=4;
				break;
			case R.id.button2:
				cur++;
				if (cur>4) cur=2;
				break;
			case R.id.button3:
				StartGame.this.getDialog().cancel();
				Intent intent=new Intent(StartGame.this.getActivity(),GameActivity.class);
				int n=Integer.valueOf((String) tv.getText());
				arPlayer arplayer=new arPlayer(n);
				intent.putExtra("arplayer", arplayer);
				startActivity(intent);
				break;
			}
	
			tv.setText(cur+"");
		}


	};
}
