package ru.fragmentcastle;


import java.util.Arrays;

import ru.fragmentcastle.logic.Player;
import ru.fragmentcastle.logic.Time;
import ru.fragmentcastle.logic.arPlayer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class GameActivity extends Activity {

	public arPlayer arplayer=new arPlayer(3);
	public arPlayer next_arplayer;
	public Player player;
	public ListView list_player;
	public Time time=new Time();
	public Field field;
	public Builder builder;
	public LinearLayout ltime;
	public FragmentTransaction ft;
	public PlayerAdapter adapter_player;
	public int pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		list_player=(ListView)findViewById(R.id.list_players);
		list_player.setOnItemClickListener(list_click);
		ltime=(LinearLayout)findViewById(R.id.time);
		field=new Field();
		builder=new Builder();

		next_arplayer=new arPlayer(arplayer.ar.length);
		next_arplayer.sort(); arplayer.sort();
		for (int i=0;i<arplayer.ar.length;i++){
			next_arplayer.ar[i].gold=arplayer.ar[i].gold;
			next_arplayer.ar[i].wood=arplayer.ar[i].wood;
			next_arplayer.ar[i].stone=arplayer.ar[i].stone;
			next_arplayer.ar[i].win=arplayer.ar[i].win;
			next_arplayer.ar[i].war=arplayer.ar[i].war;
			next_arplayer.ar[i].plus=arplayer.ar[i].plus;
			next_arplayer.ar[i].refresh(0);
		}
		Arrays.sort(arplayer.ar);
		refresh();
	}



	AdapterView.OnItemClickListener list_click=new AdapterView.OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
			if (time.phase==1 || time.phase==3 || time.phase==5){
				ft = getFragmentManager().beginTransaction();
				if (!builder.isVisible()) ft.replace(R.id.fragment, builder).addToBackStack(null).commit();
			}
			pos=position;
			builder.pere(adapter_player.getItem(position));

		}

	};

	public void next_player(){
		player=arplayer.next();
		refresh_players();
	}

	public void next(){
		//В теории игркои хоть как тут отсортированы
		for (int i=0;i<arplayer.ar.length;i++){
			next_arplayer.ar[i].gold=arplayer.ar[i].gold-next_arplayer.ar[i].gold;
			next_arplayer.ar[i].wood=arplayer.ar[i].wood-next_arplayer.ar[i].wood;
			next_arplayer.ar[i].stone=arplayer.ar[i].stone-next_arplayer.ar[i].stone;
			next_arplayer.ar[i].win=arplayer.ar[i].win-next_arplayer.ar[i].win;
			next_arplayer.ar[i].war=arplayer.ar[i].war-next_arplayer.ar[i].war;
			next_arplayer.ar[i].plus=arplayer.ar[i].plus-next_arplayer.ar[i].plus;

		}

		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		PlayerAdapter adapter_player=new PlayerAdapter(this,next_arplayer.ar,"012");
		adb.setAdapter(adapter_player, null);
		adb.setTitle("Результаты фазы");
		adb.setNeutralButton("Дальше",null);
		adb.setCancelable(false).create().show();


		next_arplayer=new arPlayer(arplayer.ar.length);
		next_arplayer.sort(); arplayer.sort();
		for (int i=0;i<arplayer.ar.length;i++){
			next_arplayer.ar[i].gold=arplayer.ar[i].gold;
			next_arplayer.ar[i].wood=arplayer.ar[i].wood;
			next_arplayer.ar[i].stone=arplayer.ar[i].stone;
			next_arplayer.ar[i].win=arplayer.ar[i].win;
			next_arplayer.ar[i].war=arplayer.ar[i].war;
			next_arplayer.ar[i].plus=arplayer.ar[i].plus;
			next_arplayer.ar[i].refresh(0);
		}
		time.next(arplayer);
		refresh();
	}

	public void pass(View v){
		//Опасная штука
		if (time.phase==7 || time.phase==8) next(); else
			if (time.phase==1 || time.phase==3 || time.phase==5) {
				player.refresh(0);
				if (!arplayer.empty()) {
					arplayer.sort();
					field.getres();
				}else {
					next_player();
					field.refresh();
				}
			} 
			else
				if (time.phase==2 || time.phase==4 || time.phase==6) {
					next_player();
					if (arplayer.cur==0) next();
				}

	}	

	public void refresh(){
		player=arplayer.next();
		refresh_fragment();
		refresh_players();
		refresh_time();
	}


	public void refresh_players(){
		adapter_player=new PlayerAdapter(this,arplayer.ar.clone(),arplayer.queue());
		list_player.setAdapter(adapter_player);
	}

	public void refresh_time(){
		LayoutParams linLayoutParam = new LayoutParams(LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT);
		ImageView image=new ImageView(this);
		image.setLayoutParams(linLayoutParam);
		ltime.removeAllViews();
		switch (time.phase){
		case 1:
			image.setImageResource(R.drawable.time1);
			break;
		case 2:
			image.setImageResource(R.drawable.time2);
			break;
		case 3:
			image.setImageResource(R.drawable.time3);
			break;
		case 4:
			image.setImageResource(R.drawable.time4);
			break;
		case 5:
			image.setImageResource(R.drawable.time5);
			break;
		case 6:
			image.setImageResource(R.drawable.time6);
			break;
		case 7:
			image.setImageResource(R.drawable.time7);
			break;
		case 8:
			image.setImageResource(R.drawable.time8);
			break;
		}
		ltime.addView(image);
	}

	public void refresh_fragment(){
		ft = getFragmentManager().beginTransaction();
		if (time.phase==1 || time.phase==3 || time.phase==5) ft.replace(R.id.fragment, field).commit();
		if (time.phase==2 || time.phase==4 || time.phase==6) ft.replace(R.id.fragment, builder).commit();	
		if (time.phase==7) Toast.makeText(this, "7 фаза", Toast.LENGTH_SHORT).show();
		if (time.phase==8) Toast.makeText(this, "8 фаза", Toast.LENGTH_SHORT).show();
	}
}
