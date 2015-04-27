package ru.fragmentcastle;


import java.util.Arrays;

import ru.fragmentcastle.logic.Monstr;
import ru.fragmentcastle.logic.Player;
import ru.fragmentcastle.logic.Time;
import ru.fragmentcastle.logic.arPlayer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {

	public arPlayer arplayer;
	public arPlayer next_arplayer;
	public Player player;
	public ListView list_player;
	public Time time;
	public Field field;
	public Builder builder;
	public LinearLayout ltime;
	public FragmentTransaction ft;
	public PlayerAdapter adapter_player;
	public int pos;
	public int id;
	public Monstr monstr;
	boolean[][] hbuild=new boolean[2][6];
	public ImageView pas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		list_player=(ListView)findViewById(R.id.list_players);
		ltime=(LinearLayout)findViewById(R.id.time);
		pas=(ImageView)findViewById(R.id.imageView1);
		field=new Field();
		builder=new Builder();
		inic();
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


	public void inic(){
		id=-1;
		arplayer=(arPlayer)getIntent().getSerializableExtra("arplayer");
		time=new Time();
		monstr=new Monstr(time.year,this);
	}

	AdapterView.OnItemClickListener list_click=new AdapterView.OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
			if (time.phase==1 || time.phase==3 || time.phase==5){
				ft = getFragmentManager().beginTransaction();
				if (!builder.isVisible()) ft.replace(R.id.fragment, builder).addToBackStack(null).commit();
			}
			pos=position;
			if (builder.isVisible()) builder.pere(adapter_player.getItem(position));

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
		time.next(arplayer);
		if (time.phase==1) monstr=new Monstr(time.year,this);
		if (time.phase==1 || time.phase==3 || time.phase==5)checkplayers();


		AlertDialog.Builder adb = new AlertDialog.Builder(this);

		PlayerAdapter adapter_player=new PlayerAdapter(this,next_arplayer.ar,arplayer.oqueue());
		adb.setAdapter(adapter_player, null);
		adb.setTitle("Результаты фазы");
		if (time.phase!=7) adb.setNeutralButton("Дальше",null); else
			adb.setNeutralButton("Дальше",new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Toast.makeText(GameActivity.this, monstr.name+" "+monstr.war, Toast.LENGTH_SHORT).show();
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
					GameActivity.this.finish();
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

	public void pass(View v){
		//Опасная штука
		if (time.phase==7) next(); else
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


	public boolean check_build(){
		int kol=0;
		for (int i=1;i>=0;i--){
			for (int j=5;j>=0;j--){
				if (hbuild[i][j]==true) kol++;
			}
		}
		return (kol>=1);
	}


	public void checkplayers(){
		for (int i=0;i<arplayer.ar.length;i++){
			if (arplayer.ar[i].build.po[1][0]==true && arplayer.ar[i].tess.toStringSum()<=7) {
				hbuild[0][i]=true;
				AlertDialog.Builder adb = new AlertDialog.Builder(this);
				adb.setCustomTitle(getTitle(arplayer.ar[i].num,"Вы хотите использовать Часовню?"));
				adb.setPositiveButton("Да", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						plef();
					}});
				adb.setNegativeButton("Нет",  new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {

						plefn();
					}});
				adb.setCancelable(false).create().show();
			}
		}
		if (!check_build()) checkplayers2();
	}

	public void checkplayers2(){
		for (int i=0;i<arplayer.ar.length;i++){
			if (arplayer.ar[i].build.po[0][0]==true && arplayer.ar[i].tess.check()) {
				hbuild[1][i]=true;
				AlertDialog.Builder adb = new AlertDialog.Builder(this);
				adb.setCustomTitle(getTitle(arplayer.ar[i].num,"Вы хотите использовать Статую?"));
				adb.setPositiveButton("Да", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {

						plef1();
					}});
				adb.setNegativeButton("Нет", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {

						plef1n();
					}});
				adb.setCancelable(false).create().show();
			}
		}
	}


	public int plef(){
		for (int j=5;j>=0;j--){
			if (hbuild[0][j]==true){
				arplayer.ar[j].tess.refresh(arplayer.ar[j].tess.toString().length());
				hbuild[0][j]=false;
				if (!check_build()) {Arrays.sort(arplayer.ar);checkplayers2();
				arplayer.cur=7;
				refresh();
				}
				return 0;
			}
		}
		return 0;
	}
	public int plefn(){
		for (int j=5;j>=0;j--){
			if (hbuild[0][j]==true){
				hbuild[0][j]=false;
				if (!check_build()) {Arrays.sort(arplayer.ar);checkplayers2();
				arplayer.cur=7;
				refresh();
				}
				return 0;
			}
		}
		return 0;
	}

	public int plef1(){
		for (int j=5;j>=0;j--){
			if (hbuild[1][j]==true){
				arplayer.ar[j].tess.reftess();
				hbuild[1][j]=false;
				if (!check_build()) {
				Arrays.sort(arplayer.ar);
				arplayer.cur=7;
				refresh();
				}
				return 0;
			}
		}
		return 0;
	}
	
	public int plef1n(){
		for (int j=5;j>=0;j--){
			if (hbuild[1][j]==true){
				hbuild[1][j]=false;
				if (!check_build()) {
				Arrays.sort(arplayer.ar);
				arplayer.cur=7;
				refresh();
				}
				return 0;
			}
		}
		return 0;
	}

	public void refresh(){
		player=arplayer.next();
		if (id==-1 || id==player.num) pas.setClickable(true); else pas.setClickable(false);
		refresh_fragment();
		refresh_players();
		refresh_time();
	}


	public void refresh_players(){
		adapter_player=new PlayerAdapter(this,arplayer.ar.clone(),arplayer.queue());
		list_player.setAdapter(adapter_player);
		list_player.setOnItemClickListener(list_click);
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

	}
	
	public View getTitle(int num,String mes){
		LayoutInflater inflater=getLayoutInflater();
		View v=inflater.inflate(R.layout.title_sov, null);
		TextView tv=(TextView)v.findViewById(R.id.textView1);
		LinearLayout lin=(LinearLayout)v.findViewById(R.id.lin);
		lin.setBackgroundColor(getColor(num));
		tv.setText(mes);
		return v;
	}
	
	private int getColor(int a){
		if (a==0) return Color.BLUE;
		if (a==1) return Color.YELLOW;
		if (a==2) return Color.GREEN;
		if (a==3) return Color.RED;
		return 0;

	}
}
