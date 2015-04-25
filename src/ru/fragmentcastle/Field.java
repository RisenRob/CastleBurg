package ru.fragmentcastle;

import java.util.Arrays;
import java.util.Comparator;

import ru.fragmentcastle.helpdial.dialog01;
import ru.fragmentcastle.helpdial.dialog02;
import ru.fragmentcastle.helpdial.dialog03;
import ru.fragmentcastle.helpdial.dialog05;
import ru.fragmentcastle.logic.Player;
import ru.fragmentcastle.logic.Time;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class Field extends Fragment {

	GameActivity game;
	ImageView[] sov=new ImageView[19];
	LinearLayout[] lsov=new LinearLayout[19];
	public int[] sov_chose=new int[19];
	int num,kol;
	String[] res;
	 DialogFragment dlg1;
	  DialogFragment dlg2;
	  DialogFragment dlg3;
	  DialogFragment dlg5;
	  boolean so=false;
	public void  onAttach(Activity activity){
		super.onAttach(activity);
		game=(GameActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_field, container,false);;
		sov[1]=(ImageView)v.findViewById(R.id.sov1);
		sov[2]=(ImageView)v.findViewById(R.id.sov2);
		sov[3]=(ImageView)v.findViewById(R.id.sov3);
		sov[4]=(ImageView)v.findViewById(R.id.sov4);
		sov[5]=(ImageView)v.findViewById(R.id.sov5);
		sov[6]=(ImageView)v.findViewById(R.id.sov6);
		sov[7]=(ImageView)v.findViewById(R.id.sov7);
		sov[8]=(ImageView)v.findViewById(R.id.sov8);
		sov[9]=(ImageView)v.findViewById(R.id.sov9);
		sov[10]=(ImageView)v.findViewById(R.id.sov10);
		sov[11]=(ImageView)v.findViewById(R.id.sov11);
		sov[12]=(ImageView)v.findViewById(R.id.sov12);
		sov[13]=(ImageView)v.findViewById(R.id.sov13);
		sov[14]=(ImageView)v.findViewById(R.id.sov14);
		sov[15]=(ImageView)v.findViewById(R.id.sov15);
		sov[16]=(ImageView)v.findViewById(R.id.sov16);
		sov[17]=(ImageView)v.findViewById(R.id.sov17);
		sov[18]=(ImageView)v.findViewById(R.id.sov18);
		lsov[1]=(LinearLayout)v.findViewById(R.id.linsov_1);
		lsov[2]=(LinearLayout)v.findViewById(R.id.linsov_2);
		lsov[3]=(LinearLayout)v.findViewById(R.id.linsov_3);
		lsov[4]=(LinearLayout)v.findViewById(R.id.linsov_4);
		lsov[5]=(LinearLayout)v.findViewById(R.id.linsov_5);
		lsov[6]=(LinearLayout)v.findViewById(R.id.linsov_6);
		lsov[7]=(LinearLayout)v.findViewById(R.id.linsov_7);
		lsov[8]=(LinearLayout)v.findViewById(R.id.linsov_8);
		lsov[9]=(LinearLayout)v.findViewById(R.id.linsov_9);
		lsov[10]=(LinearLayout)v.findViewById(R.id.linsov_10);
		lsov[11]=(LinearLayout)v.findViewById(R.id.linsov_11);
		lsov[12]=(LinearLayout)v.findViewById(R.id.linsov_12);
		lsov[13]=(LinearLayout)v.findViewById(R.id.linsov_13);
		lsov[14]=(LinearLayout)v.findViewById(R.id.linsov_14);
		lsov[15]=(LinearLayout)v.findViewById(R.id.linsov_15);
		lsov[16]=(LinearLayout)v.findViewById(R.id.linsov_16);
		lsov[17]=(LinearLayout)v.findViewById(R.id.linsov_17);
		lsov[18]=(LinearLayout)v.findViewById(R.id.linsov_18);
		for (int i=1;i<19;i++) sov_chose[i]=-1;
		refresh();
		if (MainActivity.sta==true && Time.phase==1 && Time.year==1) {
			dlg1 = new dialog01();
		    dlg2 = new dialog02();
		    dlg2.show(getFragmentManager(), "dlg2");
		    dlg1.show(getFragmentManager(), "dlg1");
		}
		if (MainActivity.sta==true && Time.phase==3 && Time.year==1) {
			dlg5 = new dialog05();			  
		    dlg5.show(getFragmentManager(), "dlg5");
		}
		return v;
	
	}
	
	
	//получение всех ресурсов с советников
		public int getres(){
			ResAdapter adapter;
			AlertDialog.Builder adb;
			adb = new AlertDialog.Builder(game);
			adb.setTitle("Выберите желаемые ресурсы");

			for (int i=1;i<19;i++){
				int num=sov_chose[i];
				if (num!=-1)
					switch (i){
					case 1 :
						game.arplayer.ar[num].win++;
						sov_chose[i]=-1;
						break;
					case 2:
						game.arplayer.ar[num].gold++;
						sov_chose[i]=-1;
						break;
					case 3:
						game.arplayer.ar[num].wood++;
						sov_chose[i]=-1;
						break;
					case 4:
						//Дерево или золото
						res=new String[]{"w","g"};
						adapter=new ResAdapter(res,game);
						adb.setAdapter(adapter, resclick);
						adb.setCustomTitle(getTitle(num));
						adb.setCancelable(false).create().show();
						return 1;
					case 5:
						game.arplayer.ar[num].war++;
						sov_chose[i]=-1;
						break;
					case 6:
						res=new String[]{"dawg","easg","haws"};
						adapter=new ResAdapter(res,game);
						adb.setAdapter(adapter, resclick);
						adb.setCustomTitle(getTitle(num));
						adb.setCancelable(false).create().show();
						return 1;
					case 7:
						game.arplayer.ar[num].plus++;
						res=new String[]{"w","g","s"};
						adapter=new ResAdapter(res,game);
						adb.setAdapter(adapter, resclick);
						adb.setCustomTitle(getTitle(num));
						adb.setCancelable(false).create().show();
						return 1;
					case 8:
						game.arplayer.ar[num].gold+=2;
						sov_chose[i]=-1;
						break;
					case 9:
						res=new String[]{"gw","sw"};
						adapter=new ResAdapter(res,game);
						adb.setAdapter(adapter, resclick);
						adb.setCustomTitle(getTitle(num));
						adb.setCancelable(false).create().show();
						return 1;
					case 10:
						game.arplayer.ar[num].war+=2;
						//Toast.makeText(game, "Имя монстра:"+game.monstr.name+" Сила:"+monstr.war, Toast.LENGTH_SHORT).show();
						sov_chose[i]=-1;
						break;
						//Ещё монстра подглядеть
					case 11:
						res=new String[]{"gs","ws"};
						adapter=new ResAdapter(res,game);
						adb.setAdapter(adapter, resclick);
						adb.setCustomTitle(getTitle(num));
						adb.setCancelable(false).create().show();
						return 1;
					case 12:
						game.arplayer.ar[num].plus++;
						res=new String[]{"w","g","s"};
						adapter=new ResAdapter(res,game);
						adb.setAdapter(adapter, resclick);
						adb.setCustomTitle(getTitle(num));
						adb.setCancelable(false).create().show();
						return 1;
					case 13:
						game.arplayer.ar[num].stone+=3;
						sov_chose[i]=-1;
						break;
					case 14:
						res=new String[]{"w","g","s"};
						adapter=new ResAdapter(res,game);
						adb.setAdapter(adapter, resclick);
						adb.setCustomTitle(getTitle(num));
						adb.setCancelable(false).create().show();
						return 1;

					case 15:
						game.arplayer.ar[num].stone++;
						game.arplayer.ar[num].wood++;
						game.arplayer.ar[num].gold++;
						sov_chose[i]=-1;
						break;
					case 16:
						game.arplayer.ar[num].gold+=4;
						sov_chose[i]=-1;
						break;
					case 17:
						res=new String[]{"w","g","s"};
						adapter=new ResAdapter(res,game);
						adb.setAdapter(adapter, resclick);
						adb.setCustomTitle(getTitle(num));
						adb.setCancelable(false).create().show();
						return 1;
					case 18:
						game.arplayer.ar[num].stone++;
						game.arplayer.ar[num].wood++;
						game.arplayer.ar[num].gold++;
						game.arplayer.ar[num].stone++;
						game.arplayer.ar[num].wood++;
						game.arplayer.ar[num].war++;
						sov_chose[i]=-1;
						break;
					}
			}
			
			game.next();
			return 0;
		}
		
		DialogInterface.OnClickListener resclick=new DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog, int position) {
				for (int i=1;i<19;i++)
					if (sov_chose[i]!=-1) {num=i;break;}
				if (num==7 && kol<=0) kol=1;
				if (num==12 && kol<=0) kol=2;
				if (num==14 && kol<=0) {kol=3;game.arplayer.ar[sov_chose[num]].win--;}
				if (num==17 && kol<=0) {kol=2;game.arplayer.ar[sov_chose[num]].win+=3;
				/*Toast.makeText(game, "Имя монстра:"+monstr.name+" Сила:"+monstr.war, Toast.LENGTH_SHORT).show();*/}
				for (int i=0;i<res[position].length();i++){
					switch (res[position].charAt(i)){
					case 'w':game.arplayer.ar[sov_chose[num]].wood++;
					kol--;
					break;
					case 'e':game.arplayer.ar[sov_chose[num]].wood--;
					break;
					case 'g':game.arplayer.ar[sov_chose[num]].gold++;
					kol--;
					break;
					case 'h':game.arplayer.ar[sov_chose[num]].gold--;
					break;
					case 's':game.arplayer.ar[sov_chose[num]].stone++;
					kol--;
					break;
					case 'd':game.arplayer.ar[sov_chose[num]].stone--;
					break;
					case 'p':game.arplayer.ar[sov_chose[num]].win++;
					break;
					case '[':game.arplayer.ar[sov_chose[num]].win--;
					break;
					case 'q':game.arplayer.ar[sov_chose[num]].war++;
					break;
					case 'z':game.arplayer.ar[sov_chose[num]].war--;
					break;
					}
				}
				if (kol<=0) {sov_chose[num]=-1;	kol=0;}
				getres();

			}

		};

	public void refresh(){		
		for (int i=1;i<19;i++){
			if (!game.player.tess.steps[i].isEmpty() && sov_chose[i]==-1){
				sov[i].setImageResource(idim(i,-2));
				sov[i].setClickable(true);
				sov[i].setOnClickListener(make);
			} else {
				sov[i].setClickable(false);
				sov[i].setImageResource(idim(i,sov_chose[i]));
			}
		}
		linrefresh(game.arplayer.ar.clone());
	}
	
	//Обновляем маркеры над советниками
	public void linrefresh(Player[] ar){
		//сортируем игроков впорядка возрастания номера
		Arrays.sort(ar, new Comparator<Player>() {
			@Override
			public int compare(Player a,Player b) {
				if (a.num>b.num) return 1;
				if (a.num<b.num) return -1;
				return 0;
			}
		});


		//забиваем маркеры
		for (int i=1;i<19;i++){
			lsov[i].removeAllViews();
			for (int j=0;j<ar.length;j++){
				//проверка может ли на этого совтника ходить игрок
				if (!ar[j].tess.steps[i].isEmpty()){
					lsov[i].addView(lin(ar[j].num));
				}
			}
		}
	}

	//создание linearlayuot для маркера
	public LinearLayout lin(int c){
		LayoutParams linLayoutParam = new LayoutParams(10, 10);
		LinearLayout lin=new LinearLayout(game);
		lin.setLayoutParams(linLayoutParam);
		lin.setBackgroundColor(getColor(c));
		return lin;
	}

	//получаем цвет по номеру
	private int getColor(int a){
		if (a==0) return Color.BLUE;
		if (a==1) return Color.YELLOW;
		if (a==2) return Color.GREEN;
		if (a==3) return Color.RED;
		return 0;

	}

	View.OnClickListener make = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			num=id(v);
			CreateDialog(num).show();
		}
	};

	
	public View getTitle(int num){
		LayoutInflater inflater=getActivity().getLayoutInflater();
		View v=inflater.inflate(R.layout.title_sov, null);
		LinearLayout lin=(LinearLayout)v.findViewById(R.id.lin);
		lin.setBackgroundColor(getColor(num));
		return v;
	}

	
	protected Dialog CreateDialog(int num) {
		AlertDialog.Builder adb = new AlertDialog.Builder(game);
		adb.setTitle("Выбор комбинации");
		TessAdapter adapter=new TessAdapter(game,game.player.tess,num);
		adb.setAdapter(adapter, dialclick);
		if (so==false && MainActivity.sta==true){
			   dlg3 = new dialog03();
			    dlg3.show(getFragmentManager(), "dlg2");
			    so=true;
		}
		return adb.create();
	}

	DialogInterface.OnClickListener dialclick=new DialogInterface.OnClickListener(){

		public void onClick(DialogInterface dialog, int position) {
			game.player.del(num, position);
			sov_chose[num]=game.player.num;
			if (!game.arplayer.empty()) {game.arplayer.sort();getres();return;}
			game.next_player();
			
			refresh();
		}

	};

	public int id(View v){
		switch (v.getId()) {
		case R.id.sov1:
			return 1;
		case R.id.sov2:
			return 2;
		case R.id.sov3:
			return 3;
		case R.id.sov4:
			return 4;
		case R.id.sov5:
			return 5;
		case R.id.sov6:
			return 6;
		case R.id.sov7:
			return 7;
		case R.id.sov8:
			return 8;
		case R.id.sov9:
			return 9;
		case R.id.sov10:
			return 10;
		case R.id.sov11:
			return 11;
		case R.id.sov12:
			return 12;
		case R.id.sov13:
			return 13;
		case R.id.sov14:
			return 14;
		case R.id.sov15:
			return 15;
		case R.id.sov16:
			return 16;
		case R.id.sov17:
			return 17;
		case R.id.sov18:
			return 18;
		}
		return 0;
	}

	public int idim(int i,int f){
		if (f==-2) switch (i){
		case 1: return R.drawable.sov_1;
		case 2: return R.drawable.sov_2;
		case 3: return R.drawable.sov_3;
		case 4: return R.drawable.sov_4;
		case 5: return R.drawable.sov_5;
		case 6: return R.drawable.sov_6;
		case 7: return R.drawable.sov_7;
		case 8: return R.drawable.sov_8;
		case 9: return R.drawable.sov_9;
		case 10: return R.drawable.sov_10;
		case 11: return R.drawable.sov_11;
		case 12: return R.drawable.sov_12;
		case 13: return R.drawable.sov_13;
		case 14: return R.drawable.sov_14;
		case 15: return R.drawable.sov_15;
		case 16: return R.drawable.sov_16;
		case 17: return R.drawable.sov_17;
		case 18: return R.drawable.sov_18;		
		}
		if (f==-1) switch (i){
		case 1: return R.drawable.sov_1_dis;
		case 2: return R.drawable.sov_2_dis;
		case 3: return R.drawable.sov_3_dis;
		case 4: return R.drawable.sov_4_dis;
		case 5: return R.drawable.sov_5_dis;
		case 6: return R.drawable.sov_6_dis;
		case 7: return R.drawable.sov_7_dis;
		case 8: return R.drawable.sov_8_dis;
		case 9: return R.drawable.sov_9_dis;
		case 10: return R.drawable.sov_10_dis;
		case 11: return R.drawable.sov_11_dis;
		case 12: return R.drawable.sov_12_dis;
		case 13: return R.drawable.sov_13_dis;
		case 14: return R.drawable.sov_14_dis;
		case 15: return R.drawable.sov_15_dis;
		case 16: return R.drawable.sov_16_dis;
		case 17: return R.drawable.sov_17_dis;
		case 18: return R.drawable.sov_18_dis;
		}
		if (f==0) switch (i){
		case 1: return R.drawable.sov_1_b;
		case 2: return R.drawable.sov_2_b;
		case 3: return R.drawable.sov_3_b;
		case 4: return R.drawable.sov_4_b;
		case 5: return R.drawable.sov_5_b;
		case 6: return R.drawable.sov_6_b;
		case 7: return R.drawable.sov_7_b;
		case 8: return R.drawable.sov_8_b;
		case 9: return R.drawable.sov_9_b;
		case 10: return R.drawable.sov_10_b;
		case 11: return R.drawable.sov_11_b;
		case 12: return R.drawable.sov_12_b;
		case 13: return R.drawable.sov_13_b;
		case 14: return R.drawable.sov_14_b;
		case 15: return R.drawable.sov_15_b;
		case 16: return R.drawable.sov_16_b;
		case 17: return R.drawable.sov_17_b;
		case 18: return R.drawable.sov_18_b;
		}
		if (f==1) switch (i){
		case 1: return R.drawable.sov_1_y;
		case 2: return R.drawable.sov_2_y;
		case 3: return R.drawable.sov_3_y;
		case 4: return R.drawable.sov_4_y;
		case 5: return R.drawable.sov_5_y;
		case 6: return R.drawable.sov_6_y;
		case 7: return R.drawable.sov_7_y;
		case 8: return R.drawable.sov_8_y;
		case 9: return R.drawable.sov_9_y;
		case 10: return R.drawable.sov_10_y;
		case 11: return R.drawable.sov_11_y;
		case 12: return R.drawable.sov_12_y;
		case 13: return R.drawable.sov_13_y;
		case 14: return R.drawable.sov_14_y;
		case 15: return R.drawable.sov_15_y;
		case 16: return R.drawable.sov_16_y;
		case 17: return R.drawable.sov_17_y;
		case 18: return R.drawable.sov_18_y;
		}
		if (f==2) switch (i){
		case 1: return R.drawable.sov_1_g;
		case 2: return R.drawable.sov_2_g;
		case 3: return R.drawable.sov_3_g;
		case 4: return R.drawable.sov_4_g;
		case 5: return R.drawable.sov_5_g;
		case 6: return R.drawable.sov_6_g;
		case 7: return R.drawable.sov_7_g;
		case 8: return R.drawable.sov_8_g;
		case 9: return R.drawable.sov_9_g;
		case 10: return R.drawable.sov_10_g;
		case 11: return R.drawable.sov_11_g;
		case 12: return R.drawable.sov_12_g;
		case 13: return R.drawable.sov_13_g;
		case 14: return R.drawable.sov_14_g;
		case 15: return R.drawable.sov_15_g;
		case 16: return R.drawable.sov_16_g;
		case 17: return R.drawable.sov_17_g;
		case 18: return R.drawable.sov_18_g;
		}
		return 0;
	}



}
