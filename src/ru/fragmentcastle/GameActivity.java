package ru.fragmentcastle;


import ru.fragmentcastle.logic.Player;
import ru.fragmentcastle.logic.Time;
import ru.fragmentcastle.logic.arPlayer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		list_player=(ListView)findViewById(R.id.list_players);
		ltime=(LinearLayout)findViewById(R.id.time);
		field=new Field();
		builder=new Builder();

		next_arplayer=new arPlayer(arplayer.ar.length);
		for (int i=0;i<arplayer.ar.length;i++){
			next_arplayer.ar[i].gold=arplayer.ar[i].gold;
			next_arplayer.ar[i].wood=arplayer.ar[i].wood;
			next_arplayer.ar[i].stone=arplayer.ar[i].stone;
			next_arplayer.ar[i].win=arplayer.ar[i].win;
			next_arplayer.ar[i].war=arplayer.ar[i].war;
			next_arplayer.ar[i].plus=arplayer.ar[i].plus;
			next_arplayer.ar[i].refresh(0);
		}

		refresh();
	}


	public void next_player(){
		player=arplayer.next();
		refresh_players();
	}

	public void next(){
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

		time.next(arplayer);
		next_arplayer=new arPlayer(arplayer.ar.length);
		for (int i=0;i<arplayer.ar.length;i++){
			next_arplayer.ar[i].gold=arplayer.ar[i].gold;
			next_arplayer.ar[i].wood=arplayer.ar[i].wood;
			next_arplayer.ar[i].stone=arplayer.ar[i].stone;
			next_arplayer.ar[i].win=arplayer.ar[i].win;
			next_arplayer.ar[i].war=arplayer.ar[i].war;
			next_arplayer.ar[i].plus=arplayer.ar[i].plus;
			next_arplayer.ar[i].refresh(0);
		}

		refresh();
	}

	public void pass(View v){
		if (time.phase%2==1) {
			player.refresh(0);
			if (!arplayer.empty()) field.getres();
			next_player();
		} else {
			next_player();
			if (arplayer.cur==0) next();
		}
	}	

	public void refresh(){
		player=arplayer.next();
		refresh_fragment();
		refresh_time();
		refresh_players();
	}


	public void refresh_players(){
		PlayerAdapter adapter_player=new PlayerAdapter(this,arplayer.ar.clone(),arplayer.queue());
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
		if (time.phase%2==1) 
		{ft.replace(R.id.fragment, field);ft.commit();}
		if (time.phase%2==0)
		{ft.replace(R.id.fragment, builder);ft.commit();}	
	}
}
