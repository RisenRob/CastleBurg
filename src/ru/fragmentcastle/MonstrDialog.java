package ru.fragmentcastle;

import android.app.Activity;
import android.app.DialogFragment;
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
    }
	
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.dialog_monstr,container,false);
		name=(TextView)v.findViewById(R.id.textView1);
		war=(TextView)v.findViewById(R.id.textView2);
		btn=(Button)v.findViewById(R.id.button1);
		list1=(ListView)v.findViewById(R.id.listView1);
		list2=(ListView)v.findViewById(R.id.listView2);
		MonstrAdapter winadap=new MonstrAdapter(getActivity(),game.monstr,true);
		MonstrAdapter loseadap=new MonstrAdapter(getActivity(),game.monstr,false);
		list1.setAdapter(winadap);
		list2.setAdapter(loseadap);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				game.arplayer.fight(game.monstr);
				game.next();
				MonstrDialog.this.getDialog().cancel();
			}
		});
		name.setText("Имя монстра: "+game.monstr.name);
		war.setText("Очки силы:"+game.monstr.war);
		return v;
	}
	
}
