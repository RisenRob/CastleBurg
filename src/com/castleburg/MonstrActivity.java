package com.castleburg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.castleburg.logic.Monstr;
import com.castleburg.logic.arPlayer;

public class MonstrActivity extends Activity {
	
	private arPlayer arplayer;
	private int year;
	Intent intent;
    Monstr monstr;
    private ListView list_player;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monstr);
		intent=getIntent();
		arplayer=(arPlayer)intent.getSerializableExtra("arplayer");
		year=intent.getIntExtra("year", -1);
		monstr=new Monstr(year,intent.getIntExtra("id_monstr", -1),this);
		list_player=(ListView)findViewById(R.id.listView1);
		refplayer();
	}
	
	public void fight(View v){
		for (int i=0;i<arplayer.ar.length;i++){
			arplayer.ar[i].fight(monstr);
		}
		refplayer();
	}
	
	public void goMain(View v){
		intent =new Intent();
		intent.putExtra("arplayer", arplayer);
		setResult(RESULT_OK, intent);
		finish();
	}
	

	
	
	
	public void refplayer(){
		PlayerAdapter adapter=new PlayerAdapter(this,arplayer.ar.clone(),"012");
		list_player.setAdapter(adapter);
		
	}
}
