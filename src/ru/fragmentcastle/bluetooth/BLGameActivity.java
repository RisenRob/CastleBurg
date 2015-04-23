package ru.fragmentcastle.bluetooth;

import java.util.Arrays;

import ru.fragmentcastle.GameActivity;
import ru.fragmentcastle.PlayerAdapter;
import ru.fragmentcastle.logic.Time;
import ru.fragmentcastle.logic.arPlayer;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

public class BLGameActivity extends GameActivity {
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		IntentFilter blFilt = new IntentFilter("ru.castleburg.bluetooth");
		registerReceiver(blinreceiver, blFilt);
	}
	
	protected void onDestroy(){
		Intent intent = new Intent(BLGameActivity.this, BlService.class);
		stopService(intent);
		unregisterReceiver(blinreceiver);
		super.onDestroy();

	}
	
	public void inic(){
		Intent intent=getIntent();
		arplayer=(arPlayer)intent.getSerializableExtra("arplayer");
		time=new Time();
	}
	
	public void next_player(){
		Intent intent = new Intent(BLGameActivity.this, BlService.class);
		intent.putExtra("command",6);
		intent.putExtra("field", field.sov_chose);
		intent.putExtra("next", 2);
		intent.putExtra("arplayer", arplayer);
		startService(intent);
	}
	
	public void bnext_player(){
		player=arplayer.next();
		Toast.makeText(this, "Номер текщего игрока"+arplayer.cur, Toast.LENGTH_SHORT).show();
		//Log.d("LOG","Номер текщего игрока"+arplayer.cur);
		refresh_players();
		if (time.phase==1 || time.phase==3 || time.phase==5) field.refresh();
		if (time.phase==2 || time.phase==4 || time.phase==6) builder.refresh();
	}
	
	public void next(){
		Intent intent = new Intent(BLGameActivity.this, BlService.class);
		if (time.phase==2 || time.phase==4 || time.phase==6) {
			arplayer.refresh();
			Arrays.sort(arplayer.ar);
		}
		intent.putExtra("command",6);
		intent.putExtra("next", 1);
		intent.putExtra("arplayer", arplayer);
		startService(intent);
	}
	
	public void bnext(){
		for (int i=0;i<arplayer.ar.length;i++){
			next_arplayer.ar[i].gold=arplayer.ar[i].gold-next_arplayer.ar[i].gold;
			next_arplayer.ar[i].wood=arplayer.ar[i].wood-next_arplayer.ar[i].wood;
			next_arplayer.ar[i].stone=arplayer.ar[i].stone-next_arplayer.ar[i].stone;
			next_arplayer.ar[i].win=arplayer.ar[i].win-next_arplayer.ar[i].win;
			next_arplayer.ar[i].war=arplayer.ar[i].war-next_arplayer.ar[i].war;
			next_arplayer.ar[i].plus=arplayer.ar[i].plus-next_arplayer.ar[i].plus;

		}
		time.next();
		//if (time.phase==1 || time.phase==3 || time.phase==5)checkplayers();
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
		Arrays.sort(arplayer.ar);
		if (time.phase==1 || time.phase==3 || time.phase==5){
			arplayer.sort();
			arplayer.cur=7;
		}
		refresh();
	}
	
	private final BroadcastReceiver blinreceiver=new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			arPlayer tarplayer=(arPlayer)intent.getSerializableExtra("arplayer");
			int next=intent.getIntExtra("next", -1);
			int[] sov=intent.getIntArrayExtra("field");
			Toast.makeText(BLGameActivity.this, "След "+next, Toast.LENGTH_SHORT).show();
			if (tarplayer!=null && next==1){
				arplayer=tarplayer;
				bnext();
			}
			if (tarplayer!=null && next==2){
				arplayer=tarplayer;
				bnext_player();
			}
			if (sov!=null){
				field.sov_chose=sov;
				field.refresh();
			}
		}

	};
	
}
