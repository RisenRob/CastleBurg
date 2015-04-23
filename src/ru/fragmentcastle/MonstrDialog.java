package ru.fragmentcastle;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MonstrDialog extends DialogFragment {
	
	GameActivity game;
	TextView war;
	TextView name;
	
	
	public void  onAttach(Activity activity){
		super.onAttach(activity);
		game=(GameActivity) activity;
	}
	
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.dialog_monstr, container,false);
		name=(TextView)v.findViewById(R.id.textView1);
		war=(TextView)v.findViewById(R.id.textView2);
		name.setText(game.monstr.name);
		war.setText(game.monstr.war);
		//getDialog().setTitle("Сражение с монстрами");
		return v;
	}
	
}
