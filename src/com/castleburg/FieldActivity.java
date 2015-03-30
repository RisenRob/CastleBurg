package com.castleburg;

import java.util.Arrays;
import java.util.Comparator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.castleburg.logic.Build;
import com.castleburg.logic.Monstr;
import com.castleburg.logic.Player;
import com.castleburg.logic.Time;
import com.castleburg.logic.arPlayer;

public class FieldActivity extends Activity {

	//советники
	private ImageView[] sov=new ImageView[19];
	//маркеры сосветников
	private LinearLayout[] lsov=new LinearLayout[19];
	//текущий игрок
	private Player player;
	//состояние советника
	int[] sov_chose=new int[19];
	//очередь игроков
	arPlayer arplayer= new arPlayer(3);
	arPlayer next_arplayer;
	//список игроков
	private ListView list_player;
	//время
	Time time=new Time();
	LinearLayout ltime;
	//номер выбраннвго советника, нужно ниже
	int num;
	//Монстр
	Monstr monstr;
	public Build build= new Build();

	String[] res;
	int kol=0,pl;
	boolean[][] hbuild=new boolean[2][3];
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		sov[1]=(ImageView)findViewById(R.id.sov1);
		sov[2]=(ImageView)findViewById(R.id.sov2);
		sov[3]=(ImageView)findViewById(R.id.sov3);
		sov[4]=(ImageView)findViewById(R.id.sov4);
		sov[5]=(ImageView)findViewById(R.id.sov5);
		sov[6]=(ImageView)findViewById(R.id.sov6);
		sov[7]=(ImageView)findViewById(R.id.sov7);
		sov[8]=(ImageView)findViewById(R.id.sov8);
		sov[9]=(ImageView)findViewById(R.id.sov9);
		sov[10]=(ImageView)findViewById(R.id.sov10);
		sov[11]=(ImageView)findViewById(R.id.sov11);
		sov[12]=(ImageView)findViewById(R.id.sov12);
		sov[13]=(ImageView)findViewById(R.id.sov13);
		sov[14]=(ImageView)findViewById(R.id.sov14);
		sov[15]=(ImageView)findViewById(R.id.sov15);
		sov[16]=(ImageView)findViewById(R.id.sov16);
		sov[17]=(ImageView)findViewById(R.id.sov17);
		sov[18]=(ImageView)findViewById(R.id.sov18);
		lsov[1]=(LinearLayout)findViewById(R.id.linsov_1);
		lsov[2]=(LinearLayout)findViewById(R.id.linsov_2);
		lsov[3]=(LinearLayout)findViewById(R.id.linsov_3);
		lsov[4]=(LinearLayout)findViewById(R.id.linsov_4);
		lsov[5]=(LinearLayout)findViewById(R.id.linsov_5);
		lsov[6]=(LinearLayout)findViewById(R.id.linsov_6);
		lsov[7]=(LinearLayout)findViewById(R.id.linsov_7);
		lsov[8]=(LinearLayout)findViewById(R.id.linsov_8);
		lsov[9]=(LinearLayout)findViewById(R.id.linsov_9);
		lsov[10]=(LinearLayout)findViewById(R.id.linsov_10);
		lsov[11]=(LinearLayout)findViewById(R.id.linsov_11);
		lsov[12]=(LinearLayout)findViewById(R.id.linsov_12);
		lsov[13]=(LinearLayout)findViewById(R.id.linsov_13);
		lsov[14]=(LinearLayout)findViewById(R.id.linsov_14);
		lsov[15]=(LinearLayout)findViewById(R.id.linsov_15);
		lsov[16]=(LinearLayout)findViewById(R.id.linsov_16);
		lsov[17]=(LinearLayout)findViewById(R.id.linsov_17);
		lsov[18]=(LinearLayout)findViewById(R.id.linsov_18);
		ltime=(LinearLayout)findViewById(R.id.time);
		for (int i=1;i<19;i++) sov_chose[i]=-1;
		list_player=(ListView)findViewById(R.id.list_player);
		refresh();
		monstr=new Monstr(time.year,this);
	}

	//Действия после сражения с монстрами или постройки строений
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode){
			case 1:
				arplayer=(arPlayer)data.getSerializableExtra("arplayer");
				time=(Time)data.getSerializableExtra("time");
				time.next(arplayer);
				arplayer.cur=7;
				refresh();
				break;
			case 2:
				arplayer=(arPlayer)data.getSerializableExtra("arplayer");
				time.next(arplayer);
				arplayer.cur=7;
				refresh();
				monstr=new Monstr(time.year,this);
				break;
			}
		}
		else Toast.makeText(this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
	}

	//пасс(игрок не может ходить)
	public void pass(View v){
		player.refresh(0);
		if (!arplayer.empty()) next();
		player=arplayer.next();
		field_refresh();
	}


	//следующая фаза игры
	public void next(){
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
		time.next(arplayer);
		getres();
	}

	public void next2(){
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
		adb.setTitle("Результаты урожая");
		adb.setNeutralButton("Дальше",new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				activnext();			
			}} );
		adb.setCancelable(false).create().show();
		field_refresh();
	}

	public void activnext(){
		Intent intent;
	 for (int i=0;i<arplayer.ar.length;i++)
	if ((time.phase==7) && (arplayer.ar[i].build.po[2][2]==true)) kazarma().show();
	else	
		if (time.phase==7) sevenf().show();
		if (time.phase==8) {
			Toast.makeText(this, "Монстры", Toast.LENGTH_SHORT).show();
			intent=new Intent(this,MonstrActivity.class);
			intent.putExtra("id_monstr", monstr.id);
			intent.putExtra("arplayer", arplayer);
			intent.putExtra("year", time.year);
			startActivityForResult(intent,2);
		}
		if (time.phase%2==0 && time.phase!=8) {
			intent=new Intent(this, BuildActivity.class);
			intent.putExtra("id_monstr", monstr.id);
			intent.putExtra("arplayer", arplayer);
			intent.putExtra("time", time);
			startActivityForResult(intent,1);			
		}

	}

	public int sov_nul(){
		for (int i=1;i<19;i++)
			if (sov_chose[i]!=-1) return i;
		return 0;
	}

	DialogInterface.OnClickListener resclick=new DialogInterface.OnClickListener(){

		public void onClick(DialogInterface dialog, int position) {
			int num=sov_nul();
			if (num==7 && kol<=0) kol=1;
			if (num==12 && kol<=0) kol=2;
			if (num==14 && kol<=0) {kol=3;arplayer.ar[sov_chose[num]].win--;}
			if (num==17 && kol<=0) {kol=2;arplayer.ar[sov_chose[num]].win+=3;
			Toast.makeText(FieldActivity.this, "Имя монстра:"+monstr.name+" Сила:"+monstr.war, Toast.LENGTH_SHORT).show();}
			for (int i=0;i<res[position].length();i++){
				switch (res[position].charAt(i)){
				case 'w':arplayer.ar[sov_chose[num]].wood++;
				kol--;
				break;
				case 'e':arplayer.ar[sov_chose[num]].wood--;
				break;
				case 'g':arplayer.ar[sov_chose[num]].gold++;
				kol--;
				break;
				case 'h':arplayer.ar[sov_chose[num]].gold--;
				break;
				case 's':arplayer.ar[sov_chose[num]].stone++;
				kol--;
				break;
				case 'd':arplayer.ar[sov_chose[num]].stone--;
				break;
				case 'p':arplayer.ar[sov_chose[num]].win++;
				break;
				case '[':arplayer.ar[sov_chose[num]].win--;
				break;
				case 'q':arplayer.ar[sov_chose[num]].war++;
				break;
				case 'z':arplayer.ar[sov_chose[num]].war--;
				break;
				}
			}
			if (kol<=0) {sov_chose[num]=-1;	kol=0;}
			getres();

		}

	};


	//получение всех ресурсов с советников
	public int getres(){
		ResAdapter adapter;
		AlertDialog.Builder adb;
		adb = new AlertDialog.Builder(this);
		adb.setTitle("Выберите желаемые ресурсы");

		for (int i=1;i<19;i++){
			int num=sov_chose[i];
			if (num!=-1)
				switch (i){
				case 1 :
					arplayer.ar[num].win++;
					sov_chose[i]=-1;
					break;
				case 2:
					arplayer.ar[num].gold++;
					sov_chose[i]=-1;
					break;
				case 3:
					arplayer.ar[num].wood++;
					sov_chose[i]=-1;
					break;
				case 4:
					//Дерево или золото
					res=new String[]{"w","g"};
					adapter=new ResAdapter(res,this);
					adb.setAdapter(adapter, resclick);
					adb.setCancelable(false).create().show();
					Toast.makeText(this, "Привет дурак", Toast.LENGTH_SHORT);
					return 1;
				case 5:
					arplayer.ar[num].war++;
					sov_chose[i]=-1;
					break;
				case 6:
					res=new String[]{"dawg","easg","haws"};
					adapter=new ResAdapter(res,this);
					adb.setAdapter(adapter, resclick);
					adb.setCancelable(false).create().show();
					return 1;
				case 7:
					arplayer.ar[num].plus++;
					res=new String[]{"w","g","s"};
					adapter=new ResAdapter(res,this);
					adb.setAdapter(adapter, resclick);
					adb.setCancelable(false).create().show();
					return 1;
				case 8:
					arplayer.ar[num].gold+=2;
					sov_chose[i]=-1;
					break;
				case 9:
					res=new String[]{"gw","sw"};
					adapter=new ResAdapter(res,this);
					adb.setAdapter(adapter, resclick);
					adb.setCancelable(false).create().show();
					return 1;
				case 10:
					arplayer.ar[num].war+=2;
					Toast.makeText(FieldActivity.this, "Имя монстра:"+monstr.name+" Сила:"+monstr.war, Toast.LENGTH_SHORT).show();
					sov_chose[i]=-1;
					break;
					//Ещё монстра подглядеть
				case 11:
					res=new String[]{"gs","ws"};
					adapter=new ResAdapter(res,this);
					adb.setAdapter(adapter, resclick);
					adb.setCancelable(false).create().show();
					return 1;
				case 12:
					arplayer.ar[num].plus++;
					res=new String[]{"w","g","s"};
					adapter=new ResAdapter(res,this);
					adb.setAdapter(adapter, resclick);
					adb.setCancelable(false).create().show();
					return 1;
				case 13:
					arplayer.ar[num].stone+=3;
					sov_chose[i]=-1;
					break;
				case 14:
					res=new String[]{"w","g","s"};
					adapter=new ResAdapter(res,this);
					adb.setAdapter(adapter, resclick);
					adb.setCancelable(false).create().show();
					return 1;

				case 15:
					arplayer.ar[num].stone++;
					arplayer.ar[num].wood++;
					arplayer.ar[num].gold++;
					sov_chose[i]=-1;
					break;
				case 16:
					arplayer.ar[num].gold+=4;
					sov_chose[i]=-1;
					break;
				case 17:
					res=new String[]{"w","g","s"};
					adapter=new ResAdapter(res,this);
					adb.setAdapter(adapter, resclick);
					adb.setCancelable(false).create().show();
					return 1;
				case 18:
					arplayer.ar[num].stone++;
					arplayer.ar[num].wood++;
					arplayer.ar[num].gold++;
					arplayer.ar[num].stone++;
					arplayer.ar[num].wood++;
					arplayer.ar[num].war++;
					sov_chose[i]=-1;
					break;
				}
		}
		next2();
		return 0;
	}

	//перезагрузить поле
	private void refresh(){
		time_refresh();
		player=arplayer.next();
		checkplayers();
		field_refresh();
	}

	private void field_refresh(){
		//обновление списка игроков
		PlayerAdapter adapter_player=new PlayerAdapter(this,arplayer.ar.clone(),arplayer.queue());
		list_player.setAdapter(adapter_player);
		//обновление маркеров
		linrefresh(arplayer.ar.clone());
		//обработака картинок
		prepare();
	}


	public void checkplayers2(){
		for (int i=0;i<arplayer.ar.length;i++){
			if (arplayer.ar[i].build.po[0][0]==true && arplayer.ar[i].tess.check()) {
				hbuild[1][i]=true;
				AlertDialog.Builder adb = new AlertDialog.Builder(this);
				adb.setTitle("Вы хотите использовать Статую?");
				adb.setPositiveButton("Да", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {

						plef1();
					}});
				adb.setNegativeButton("Нет", null);
				adb.create().show();
			}
		}
	}
	public void checkplayers(){
		for (int i=0;i<arplayer.ar.length;i++){
			if (arplayer.ar[i].build.po[1][0]==true && arplayer.ar[i].tess.toStringSum()<=7) {
				hbuild[0][i]=true;
				AlertDialog.Builder adb = new AlertDialog.Builder(this);
				adb.setTitle("Вы хотите использовать Часовню?");
				adb.setPositiveButton("Да", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						plef();
					}});
				adb.setNegativeButton("Нет", null);
				adb.create().show();
			}
		}
		if (!check_build0()) checkplayers2();
	}

	public boolean check_build(){
		int kol=0;
		for (int i=1;i>=0;i--){
			for (int j=2;j>=0;j--){
				if (hbuild[i][j]==true) kol++;
			}
		}
		return (kol==1);
	}
	public boolean check_build0(){
		for (int j=2;j>=0;j--){
			if (hbuild[0][j]==true) return true;
		}
		return false;
	}
	public boolean check_build1(){
		for (int j=2;j>=0;j--){
			if (hbuild[1][j]==true) return true;
		}
		return false;
	}

	public int plef(){
		for (int j=2;j>=0;j--){
			if (hbuild[0][j]==true){
				arplayer.ar[j].tess.refresh(arplayer.ar[j].tess.toString().length());
				hbuild[0][j]=false;
				if (!check_build0()) {Arrays.sort(arplayer.ar);checkplayers2();}
				field_refresh();
				return 0;
			}
		}
		return 0;
	}

	public int plef1(){
		for (int j=2;j>=0;j--){
			if (hbuild[1][j]==true){
				arplayer.ar[j].tess.reftess();
				hbuild[1][j]=false;
				if (!check_build1()) Arrays.sort(arplayer.ar);
				field_refresh();
				return 0;
			}
		}
		return 0;
	}
	//обновление времени
	public void time_refresh(){
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

	//подготоваить поле(работа с картинками)
	public void prepare(){
		for (int i=1;i<19;i++){
			if (!player.tess.steps[i].isEmpty() && sov_chose[i]==-1){
				sov[i].setImageResource(idim(i,-2));
				sov[i].setClickable(true);
				sov[i].setOnClickListener(make);
			} else {
				sov[i].setClickable(false);
				sov[i].setImageResource(idim(i,sov_chose[i]));
			}
		}

	}

	//создание linearlayuot для маркера
	public LinearLayout lin(int c){
		LayoutParams linLayoutParam = new LayoutParams(10, 10);
		LinearLayout lin=new LinearLayout(this);
		lin.setLayoutParams(linLayoutParam);
		lin.setBackgroundColor(getColor(c));
		return lin;
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

	//получаем цвет по номеру
	private int getColor(int a){
		if (a==0) return Color.BLUE;
		if (a==1) return Color.YELLOW;
		if (a==2) return Color.GREEN;
		if (a==3) return Color.RED;
		return 0;

	}
public Dialog sevenf(){
		final String[] check={"Дерево","Золото","Камень"};
		final boolean[] mCheckedItems = { false, false, false };
		 AlertDialog.Builder sf = new AlertDialog.Builder(this);
		 sf.setTitle("Выберите 2 разных ресурса")
		 .setCancelable(false)
		 .setMultiChoiceItems(check,mCheckedItems,
				 new DialogInterface.OnMultiChoiceClickListener() {
             @Override
             public void onClick(DialogInterface dialog,
                     int which, boolean isChecked) {
                 mCheckedItems[which] = isChecked;             
             }
         })
        .setPositiveButton("Отдать",
        		 new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog,
                     int id) {            	 
            	int ch=0,che=0;            	
            	for(int i=0;i<check.length;i++)
            		if(mCheckedItems[i]) ch++;
            	if (ch==2) {
            			if (mCheckedItems[0] && player.wood>0 ) {player.wood--;che++;}
            			if (mCheckedItems[1] && player.gold>0) {player.gold--;che++;}
            			if (mCheckedItems[2] && player.stone>0) {player.stone--;che++;} 
            		if (che==2)player.war++;            		
            		else  {Toast.makeText(
                            getApplicationContext(),
                            "Нехватает ресурсов",		                                            
                            Toast.LENGTH_SHORT).show();        		  		
            		}
            		
            	}else {Toast.makeText(
                        getApplicationContext(),
                        "Выберите ровно 2 ресурса",		                                            
                        Toast.LENGTH_SHORT).show();
            	}
             }
          
             }
         )
             .setNegativeButton("Далее",
                     new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog,
                                 int id) {
                             dialog.cancel();
                             
                         }
                     });
return sf.create();
		}
public Dialog kazarma(){
	final String[] chec={"Дерево","Золото","Камень"};
	final boolean[] mCheckedItem = { false, false, false };
	 AlertDialog.Builder sfa = new AlertDialog.Builder(this);
	 sfa.setTitle("Выберите ровно 1 ресурс ")
	 .setCancelable(false)
	 .setMultiChoiceItems(chec,mCheckedItem,
			 new DialogInterface.OnMultiChoiceClickListener() {
         @Override
         public void onClick(DialogInterface dialog,
                 int which, boolean isChecked) {
             mCheckedItem[which] = isChecked;
         
         }
     })
     .setPositiveButton("Отдать",
    		 new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog,
                 int id) {        	 
        	int ch1=0,che1=0;        	
        	for(int i=0;i<chec.length;i++)
        		if(mCheckedItem[i]) ch1++;
        	if (ch1==1) {
        			if (mCheckedItem[0] && player.wood>0 ) {player.wood--;che1++;}
        			if (mCheckedItem[1] && player.gold>0) {player.gold--;che1++;}
        			if (mCheckedItem[2] && player.stone>0) {player.stone--;che1++;} 
        		if (che1==1) player.war++;        		
        		else  {Toast.makeText(
                        getApplicationContext(),
                        "Нехватает ресурсов",		                                            
                        Toast.LENGTH_SHORT).show();    
        		}        		
        	}else {Toast.makeText(
                    getApplicationContext(),
                    "Выберите ровно 1 ресурс",		                                            
                    Toast.LENGTH_SHORT).show();
        	}
         }
         }
     )
         .setNegativeButton("Далее",
                 new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog,
                             int id) {
                         dialog.cancel();
                         
                     }
                 });
return sfa.create();
	}	


	/*
	  Обрабатываем советников и комбинации
	 */
	//обрабочтик нажатий на советника
	View.OnClickListener make = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			num=id(v);
			onCreateDialog(num).show();
		}
	};

	//создание диалога с комбинациями
	protected Dialog onCreateDialog(int num) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Выбор комбинации");
		TessAdapter adapter=new TessAdapter(this,player.tess,num);
		adb.setAdapter(adapter, dialclick);
		return adb.create();
	}

	//обрабочтик выбора комбинации
	DialogInterface.OnClickListener dialclick=new DialogInterface.OnClickListener(){

		public void onClick(DialogInterface dialog, int position) {
			player.del(num, position);
			sov_chose[num]=player.num;
			if (!arplayer.empty()) next();
			player=arplayer.next();
			field_refresh();
		}

	};

	/*
	полчуаем всякие всякие id
	 */
	//функция возращаающая номер советника по ImgeView

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


	//возращаем кратнку для imageView
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
