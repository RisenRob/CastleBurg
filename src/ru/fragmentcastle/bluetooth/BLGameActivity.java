package ru.fragmentcastle.bluetooth;

import java.util.ArrayList;
import java.util.Arrays;

import ru.fragmentcastle.GameActivity;
import ru.fragmentcastle.MonstrDialog;
import ru.fragmentcastle.PlayerAdapter;
import ru.fragmentcastle.R;
import ru.fragmentcastle.ResAdapter;
import ru.fragmentcastle.WinAdapter;
import ru.fragmentcastle.logic.Monstr;
import ru.fragmentcastle.logic.Time;
import ru.fragmentcastle.logic.arPlayer;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class BLGameActivity extends GameActivity {

	ListView chat;
	EditText smes;
	ArrayAdapter<String> adap_chat;

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
		setContentView(R.layout.activity_bgame);
		Intent intent=getIntent();
		int idm=intent.getIntExtra("monstr", -1);
		id=intent.getIntExtra("id", -1);
		time=new Time();
		arplayer=(arPlayer)intent.getSerializableExtra("arplayer");
		monstr=new Monstr(time.year,idm,this);
		chat=(ListView)findViewById(R.id.list_chat);
		smes=(EditText)findViewById(R.id.editText1);
		adap_chat=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new ArrayList<String>());
		chat.setAdapter(adap_chat);
	}

	public void send(View v){
		Intent intent = new Intent(BLGameActivity.this, BlService.class);
		intent.putExtra("command",3);
		intent.putExtra("text", smes.getText().toString());
		startService(intent);
	}

	public void next_player(){
		Intent intent = new Intent(BLGameActivity.this, BlService.class);
		intent.putExtra("command",8);
		intent.putExtra("field", field.sov_chose);
		intent.putExtra("next", 2);
		intent.putExtra("arplayer", arplayer);
		startService(intent);
	}

	public void bnext_player(){
		player=arplayer.next();
		refresh_players();
		if (time.phase==1 || time.phase==3 || time.phase==5) field.refresh();
		if (time.phase==2 || time.phase==4 || time.phase==6) builder.refresh();
		if (id==player.num) pas.setClickable(true); else pas.setClickable(false);
	}

	public void next(){
		Intent intent = new Intent(BLGameActivity.this, BlService.class);
		if (time.phase==2 || time.phase==4 || time.phase==6) {
			arplayer.refresh();
			Arrays.sort(arplayer.ar);
		}
		intent.putExtra("command",8);
		intent.putExtra("next", 1);
		intent.putExtra("field", field.sov_chose);
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
		//if (time.phase==1) monstr=new Monstr(time.year,this);
		//if (time.phase==1 || time.phase==3 || time.phase==5)checkplayers();


		AlertDialog.Builder adb = new AlertDialog.Builder(this);

		PlayerAdapter adapter_player=new PlayerAdapter(this,next_arplayer.ar,arplayer.oqueue());
		adb.setAdapter(adapter_player, null);
		adb.setTitle("Результаты фазы");
		if (time.phase!=7) adb.setNeutralButton("Дальше",null); else
			adb.setNeutralButton("Дальше",new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Toast.makeText(BLGameActivity.this, monstr.name+" "+monstr.war, Toast.LENGTH_SHORT).show();
					MonstrDialog md=new MonstrDialog();
					md.show(getFragmentManager(), "md");

				}

			});
		if (time.year==6) {
			adb.setTitle("Конец игры!");
			WinAdapter win=new WinAdapter(this,arplayer.ar.clone());
			adb.setAdapter(win, null);
			adb.setNeutralButton("Конец",new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dial, int arg1) {
					dial.cancel();
					BLGameActivity.this.finish();
				}

			});
		}


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
		refresh();
	}

	public void sgetres(int n){
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		ResAdapter adapter;
		adapter=new ResAdapter(field.res,this);
		adb.setAdapter(adapter, field.resclick);
		adb.setCustomTitle(getTitle(field.sov_chose[n],"Выберите желаемые ресурсы"));
		adb.setCancelable(false).create().show();
	}
	
	public int bgetres(){
		
		
		for (int i=1;i<19;i++){
			if (field.sov_chose[i]!=-1 && id==field.sov_chose[i]){
				switch(i){
				case 4:
					//Дерево или золото
					field.res=new String[]{"w","g"};
					sgetres(i);
					return 1;
				case 6:
					field.res=new String[]{"dawg","easg","haws"};
					sgetres(i);
					return 1;
				case 7:
					field.res=new String[]{"dawg","easg","haws"};
					sgetres(i);
					return 1;
				case 9:
					field.res=new String[]{"gw","sw"};
					sgetres(i);
					return 1;
				case 10:
					AlertDialog.Builder adb = new AlertDialog.Builder(this);
					adb.setNeutralButton("Да",new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							MonstrDialog md=new MonstrDialog();
							md.show(getFragmentManager(), "md");
							arg0.cancel();
						}

					});
					adb.setCustomTitle(getTitle(field.sov_chose[i],"Вы готовы подсмотреть монстра?"));
					adb.setCancelable(false).create().show();
					field.sov_chose[i]=-1;
					return 1;
				case 11:
					field.res=new String[]{"gs","ws"};
					sgetres(i);
					return 1;
				case 12:
					field.res=new String[]{"w","g","s"};
					sgetres(i);
					return 1;
				case 14:
					field.res=new String[]{"w","g","s"};
					sgetres(i);
					return 1;
				case 17:
					field.res=new String[]{"w","g","s"};
					sgetres(i);
					return 1;
				}
				
				
				
				
			}
		}
		return 0;
	}

	private final BroadcastReceiver blinreceiver=new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			arPlayer tarplayer=(arPlayer)intent.getSerializableExtra("arplayer");
			int next=intent.getIntExtra("next", -1),idm=intent.getIntExtra("idm", -1),
					year=intent.getIntExtra("year", -1);
			int[] sov=intent.getIntArrayExtra("field");
			String mes=intent.getStringExtra("text");
			Log.d("TAG",next+"");
			//Toast.makeText(BLGameActivity.this, "След "+next, Toast.LENGTH_SHORT).show();
			if (sov!=null){
				field.sov_chose=sov;
				field.refresh();
			}
			if (tarplayer!=null && next==1){
				arplayer=tarplayer;
				bnext();
			}
			if (tarplayer!=null && next==2){
				arplayer=tarplayer;
				bnext_player();
			}
			if (tarplayer!=null && next==3){
				arplayer=tarplayer;
				bgetres();
				
			}
			if (tarplayer!=null && next==4 && id==0){
				arplayer=tarplayer;
				field.getres();
			}
			if (idm!=-1 && year!=-1){
				monstr=new Monstr(year, idm, context);
			}
			if (mes!=null){
				adap_chat.add(mes);
			}
		}

	};

}
