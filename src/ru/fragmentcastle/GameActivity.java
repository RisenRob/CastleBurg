package ru.fragmentcastle;


import ru.fragmentcastle.logic.Player;
import ru.fragmentcastle.logic.Time;
import ru.fragmentcastle.logic.arPlayer;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class GameActivity extends Activity {
	
	public arPlayer arplayer=new arPlayer(3);
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
		refresh();
	}
	
	public void next(){
		time.next(arplayer);
		refresh();
	}
	
	public void pass(View v){
		player.refresh(0);
		player=arplayer.next();
		if (!arplayer.empty()) next();
		refresh_players();
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
