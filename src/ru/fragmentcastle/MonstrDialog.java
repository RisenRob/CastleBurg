package ru.fragmentcastle;

import ru.fragmentcastle.bluetooth.BlService;
import ru.fragmentcastle.logic.Monstr;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MonstrDialog extends DialogFragment {

	GameActivity game;
	TextView war;
	TextView name;
	Button btn;
	ListView list1,list2;

	public void  onAttach(Activity activity){
		super.onAttach(activity);
		game=(GameActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE,0);
		setCancelable(false);
	}

	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.dialog_monstr,container,false);
		name=(TextView)v.findViewById(R.id.textView1);
		war=(TextView)v.findViewById(R.id.textView2);
		btn=(Button)v.findViewById(R.id.button1);
		list1=(ListView)v.findViewById(R.id.list_chat);
		list2=(ListView)v.findViewById(R.id.listView2);
		MonstrAdapter winadap=new MonstrAdapter(getActivity(),game.monstr,true);
		MonstrAdapter loseadap=new MonstrAdapter(getActivity(),game.monstr,false);
		list1.setAdapter(winadap);
		list2.setAdapter(loseadap);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (game.id==0|| game.id==-1){
					if (game.id==-1){
						if (game.time.phase==7) {
							game.arplayer.fight(game.monstr);
							game.next();
						} else game.field.getres();
					}
					if (game.id==0) {
						if (game.time.phase==7){
							game.arplayer.fight(game.monstr);
							Intent intent = new Intent(game, BlService.class);
							intent.putExtra("command",8);
							intent.putExtra("next", 1);
							intent.putExtra("field", game.field.sov_chose);
							intent.putExtra("arplayer", game.arplayer);
							game.startService(intent);

							Monstr mosntr=new Monstr(game.time.year+1,game);
							intent = new Intent(game, BlService.class);
							intent.putExtra("command",9);
							intent.putExtra("idm", mosntr.id);
							intent.putExtra("year", game.time.year+1);
							game.startService(intent);
						} else game.field.getres();
					}
				} else {
					Intent intent = new Intent(game, BlService.class);
					intent.putExtra("command",8);
					intent.putExtra("field", game.field.sov_chose);
					intent.putExtra("next", 4);
					intent.putExtra("arplayer", game.arplayer);
					game.startService(intent);
				}
				MonstrDialog.this.getDialog().cancel();
			}
		});
		name.setText("Имя монстра: "+game.monstr.name);
		war.setText("Очки силы:"+game.monstr.war);
		return v;
	}

}
