package com.castleburg;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.castleburg.logic.Build;
import com.castleburg.logic.Monstr;
import com.castleburg.logic.Player;
import com.castleburg.logic.Time;
import com.castleburg.logic.arPlayer;

public class BuildActivity extends Activity {
	final String TAG="Build";
	Time time;
	private ImageView image[][]=new ImageView[5][6];
	public boolean[][] pos=new boolean [5][6];
	public boolean[][] sosbutt=new boolean [5][6];		
	public Build build;
	public arPlayer arplayer;
	public Player player;
	public FieldActivity fieldActivity;
	Monstr monstr;
	Intent intent;
	Button button1,button2,button3;
	   AlertDialog.Builder ad;
	    Context context;
	    private final int IDD_THREE_BUTTONS = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_build);
		image[0][0] = (ImageView)findViewById(R.id.imageView1);
		image[0][1] = (ImageView)findViewById(R.id.imageView5);
		image[0][2] = (ImageView)findViewById(R.id.imageView9);
		image[0][3] = (ImageView)findViewById(R.id.imageView13);
		image[0][4] = (ImageView)findViewById(R.id.imageView17);
		image[1][0] = (ImageView)findViewById(R.id.imageView2);
		image[1][1] = (ImageView)findViewById(R.id.imageView6);
		image[1][2] = (ImageView)findViewById(R.id.imageView10);
		image[1][3] = (ImageView)findViewById(R.id.imageView14);
		image[1][4] = (ImageView)findViewById(R.id.imageView18);
		image[2][0] = (ImageView)findViewById(R.id.imageView3);
		image[2][1] = (ImageView)findViewById(R.id.imageView7);
		image[2][2] = (ImageView)findViewById(R.id.imageView11);
		image[2][3] = (ImageView)findViewById(R.id.imageView15);
		image[2][4] = (ImageView)findViewById(R.id.imageView19);
		image[3][0] = (ImageView)findViewById(R.id.imageView4);
		image[3][1] = (ImageView)findViewById(R.id.imageView8);
		image[3][2] = (ImageView)findViewById(R.id.imageView12);
		image[3][3] = (ImageView)findViewById(R.id.imageView16);
		image[3][4] = (ImageView)findViewById(R.id.imageView20);
		button1=(Button)findViewById(R.id.button2);
		button2=(Button)findViewById(R.id.button4);
		button3=(Button)findViewById(R.id.button3);
		intent=getIntent();
		arplayer=(arPlayer)intent.getSerializableExtra("arplayer");
		time=(Time)intent.getSerializableExtra("time");
		monstr=new Monstr(time.year,intent.getIntExtra("id_monstr", -1),this);
		arplayer.sort();
		player=arplayer.ar[0];
		build=player.build;
		pos=build.pos;
		sosbutt=build.sosbut;
		
		

		///[Столбец][Строка]	


		for (int i=0;i<4;i++)
			for (int j=0;j<5;j++){
				if (sosbutt[i][j]) image[i][j].setOnClickListener(click); else image[i][j].setClickable(false);
				image[i][j].setImageResource(idim(i,j,build.po[i][j]));
			}

	}
public Player perecl (View v){	
	switch (v.getId()) {
	case R.id.button2:
		return	arplayer.ar[0];
	case R.id.button4:
		return	arplayer.ar[1];
	case R.id.button3:
		return	arplayer.ar[2];
};
return player;
}
	
public void pere(View v){	
	player=perecl(v);
	if (perecl(v)==arplayer.ar[arplayer.cur])refresh();else {
		refresh();
		for (int i=0;i<4;i++)
		for (int j=0;j<5;j++)
		image[i][j].setClickable(false);}
	
	
}

	public void goMain(){
		intent =new Intent();
		intent.putExtra("arplayer", arplayer);
		intent.putExtra("time", time);
		setResult(RESULT_OK, intent);
		finish();
	}

	public void refresh(){
		build=player.build;
		sosbutt=build.sosbut;

		for (int i=0;i<4;i++)
			for (int j=0;j<5;j++){
				if (sosbutt[i][j]) image[i][j].setOnClickListener(click); else image[i][j].setClickable(false);
				image[i][j].setImageResource(idim(i,j,build.po[i][j]));
			}
	}

	public void pass(View v){
	    arplayer.next();
		if (arplayer.cur==0) goMain();
		refresh();
	
	}
	
	View.OnClickListener click=new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			build.sosbut=sosbutt;
			pos=build.pos;
			switch (v.getId()) {
			case R.id.imageView1:
				pos[0][0]=true;
				build.build(player,pos);
				if (build.po[0][0]){
					image[1][0].setClickable(true);
					image[0][0].setClickable(false);
					player=arplayer.next();
				}
				break;
			case R.id.imageView5:
				pos[0][1]=true;
				build.build(player,pos);
				if (build.po[0][1]){
					image[1][1].setClickable(true);
					image[0][1].setClickable(false);
					player=arplayer.next();
				}
				break;
			case R.id.imageView9:
				pos[0][2]=true;
				build.build(player,pos);
				if (build.po[0][2]){
					image[1][2].setClickable(true);
					image[0][2].setClickable(false);
					player=arplayer.next();
				}
				break;
			case R.id.imageView13:
				pos[0][3]=true;
				build.build(player,pos);
				if (build.po[0][3]){
					image[1][3].setClickable(true);
					image[0][3].setClickable(false);
					player=arplayer.next();
				}
				break;
			case R.id.imageView17:
				pos[0][4]=true;
				build.build(player,pos);
				if (build.po[0][4]){
					image[1][4].setClickable(true);
					image[0][4].setClickable(false);
					player=arplayer.next();
				}
				break;
			case R.id.imageView2:
				pos[1][0]=true;
				build.build(player,pos);
				if (build.po[1][0]){
					image[2][0].setClickable(true);
					image[1][0].setClickable(false);
					player=arplayer.next();
				}
				break;
			case R.id.imageView6:
				pos[1][1]=true;
				build.build(player,pos);
				if (build.po[1][1]){
					image[2][1].setClickable(true);
					image[1][1].setClickable(false);
					player=arplayer.next();
				}
				break;
			case R.id.imageView10:
				pos[1][2]=true;
				build.build(player,pos);
				if (build.po[1][2]){
					image[2][2].setClickable(true);
					image[1][2].setClickable(false);
					player=arplayer.next();
				}         
				break;
			case R.id.imageView14:
				pos[1][3]=true;
				build.build(player,pos);
				if (build.po[1][3]){
					image[2][3].setClickable(true);
					image[1][3].setClickable(false);
					player=arplayer.next();
				}
				break;
			case R.id.imageView18:
				pos[1][4]=true;
				build.build(player,pos);
				if (build.po[1][4]){
					image[2][4].setClickable(true);
					image[1][4].setClickable(false);
					player=arplayer.next();
				}
				break;
			case R.id.imageView3:
				pos[2][0]=true;
				build.build(player,pos);
				if (build.po[2][0]){
					image[3][0].setClickable(true);
					image[2][0].setClickable(false);
					player=arplayer.next();
				}
				break;
			case R.id.imageView7:
				pos[2][1]=true;
				build.build(player,pos);
				if (build.po[2][1]){
					image[3][1].setClickable(true);
					image[2][1].setClickable(false);
					player=arplayer.next();
				}
				break;
			case R.id.imageView11:
				pos[2][2]=true;
				build.build(player,pos);
				if (build.po[2][2]){
					image[3][2].setClickable(true);
					image[2][2].setClickable(false);
					player=arplayer.next();
				}
				break;
			case R.id.imageView15:
				pos[2][3]=true;
				build.build(player,pos);
				if (build.po[2][3]){
					image[3][3].setClickable(true);
					image[2][3].setClickable(false);
					player=arplayer.next();
				}
				break;
			case R.id.imageView19:
				pos[2][4]=true;
				build.build(player,pos);
				if (build.po[2][4]){
					image[3][4].setClickable(true);
					image[2][4].setClickable(false);					
					player=arplayer.next();					
				}
				break;
			case R.id.imageView4:
				pos[3][0]=true;
				build.build(player,pos);
				if (build.po[3][0]){
					image[3][0].setClickable(false);
					player=arplayer.next();
				}
				break;
			case R.id.imageView8:
				pos[3][1]=true;
				build.build(player,pos);
				if (build.po[3][1]){
					image[3][1].setClickable(false);   
					player=arplayer.next();
				}
				break;
			case R.id.imageView12:
				pos[3][2]=true;    	
				build.build(player,pos);
				if (build.po[3][2]){
					image[3][2].setClickable(false); 
					player=arplayer.next();
				}
				break;
			case R.id.imageView16:
				pos[3][3]=true;
				build.build(player,pos);
				if (build.po[3][3]){
					image[3][3].setClickable(false);    	
					player=arplayer.next();
				}
				break;
			case R.id.imageView20:
				pos[3][4]=true;
				build.build(player,pos);
				if (build.po[3][4]){
					image[3][4].setClickable(false);    	
					player=arplayer.next();
				}
				break; 	   	

			}
			for (int i=0;i<4;i++)
				for (int j=0;j<5;j++){
					 sosbutt[i][j]=image[i][j].isClickable();
					 
				}
			build.sosbut=sosbutt;
			build.pos=pos;
			if (arplayer.cur==0) goMain();
			refresh();
		}
		
	};
	


	public int idim(int i, int j, boolean f){
		if (f) switch (i){
		case 0:
			switch (j){
			case 0: return R.drawable.cbuild_01;
			case 1: return R.drawable.cbuild_05;
			case 2: return R.drawable.cbuild_09;
			case 3: return R.drawable.cbuild_13;
			case 4: return R.drawable.cbuild_17;
			}
			break;
		case 1:
			switch (j){
			case 0: return R.drawable.cbuild_02;
			case 1: return R.drawable.cbuild_06;
			case 2: return R.drawable.cbuild_10;
			case 3: return R.drawable.cbuild_14;
			case 4: return R.drawable.cbuild_18;
			}
			break;
		case 2:
			switch (j){
			case 0: return R.drawable.cbuild_03;
			case 1: return R.drawable.cbuild_07;
			case 2: return R.drawable.cbuild_11;
			case 3: return R.drawable.cbuild_15;
			case 4: return R.drawable.cbuild_19;
			}
			break;
		case 3:
			switch (j){
			case 0: return R.drawable.cbuild_04;
			case 1: return R.drawable.cbuild_08;
			case 2: return R.drawable.cbuild_12;
			case 3: return R.drawable.cbuild_16;
			case 4: return R.drawable.cbuild_20;
			}
			break;

		} else switch (i){
		case 0:
			switch (j){
			case 0: return R.drawable.nbuild_01;
			case 1: return R.drawable.nbuild_05;
			case 2: return R.drawable.nbuild_09;
			case 3: return R.drawable.nbuild_13;
			case 4: return R.drawable.nbuild_17;
			}
			break;
		case 1:
			switch (j){
			case 0: return R.drawable.nbuild_02;
			case 1: return R.drawable.nbuild_06;
			case 2: return R.drawable.nbuild_10;
			case 3: return R.drawable.nbuild_14;
			case 4: return R.drawable.nbuild_18;
			}
			break;
		case 2:
			switch (j){
			case 0: return R.drawable.nbuild_03;
			case 1: return R.drawable.nbuild_07;
			case 2: return R.drawable.nbuild_11;
			case 3: return R.drawable.nbuild_15;
			case 4: return R.drawable.nbuild_19;
			}
			break;
		case 3:
			switch (j){
			case 0: return R.drawable.nbuild_04;
			case 1: return R.drawable.nbuild_08;
			case 2: return R.drawable.nbuild_12;
			case 3: return R.drawable.nbuild_16;
			case 4: return R.drawable.nbuild_20;
			}
			break;

		}
		return 0;		
	}
	
	public void onCli (View v){
		//Когда будет вызываться функция проверять на постройку первого столба
		int[] ii=new int[4];
		int[] jj=new int[5];
		for (int i=0;i<4;i++)
			for (int j=0;j<5;j++){	
				
			if (sosbutt[i][j]==(true)) {ii[i]=i;jj[j]=j;}
			}
	
				int t;
				for ( int i=0;i<ii.length-1;i++){
					for (int j=i+1;j<ii.length;j++){
						if (ii[i]>ii[j]) {
							t=ii[j];
							ii[j]=ii[i];
							ii[i]=t;
						}
					}
				}
								
				for (int i=0;i<5;i++){
					if ((build.po[3][0]==true || build.po[3][1]==true || build.po[3][2]==true || build.po[3][3]==true || build.po[3][4]==true) ){
						for (int j=0;j<5;j++)
						if (build.po[3][j]==true) {sosbutt[3][j]=(true);build.po[3][j]=false;break;}break;}
					if (sosbutt[ii[3]][i]==(true)&&((build.po[3][0]==false || build.po[3][1]==false || build.po[3][2]==false || build.po[3][3]==false || build.po[3][4]==false))){
						sosbutt[ii[3]][i]=(false);
						sosbutt[ii[3]-1][i]=(true);
						break;
					}
					
				}
					
				build.sosbut=sosbutt;
				sosbutt=build.sosbut;
				for (int i=0;i<4;i++)
					for (int j=0;j<5;j++){
						image[i][j].setClickable(sosbutt[i][j]);
					}
				pass(v);
	
	}

	public Dialog slych(){
		final String[] chec={"Дерево","Золото","Камень"};
		final boolean[] mCheckedItem = { false, false, false };
		 AlertDialog.Builder sfab = new AlertDialog.Builder(this);
		 sfab.setTitle("Выберите ровно 1 ресурс ")
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
	        		if (che1==1) player.win++;        		
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
	return sfab.create();
		}	
	
	   public Dialog onCreateDialog4() {     
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Хотите использовать ратушу")
					.setCancelable(false)
					.setPositiveButton("Да,сжечь +2 к кубику",new 
							DialogInterface.OnClickListener() {			
								public void onClick(DialogInterface dialog,int id) {
									if (player.plus>0) {player.plus--;player.win++; dialog.cancel();} else Toast.makeText(
		                                    getApplicationContext(),
		                                    "Нехватет ресурса",		                                            
		                                    Toast.LENGTH_SHORT).show();
									
								}
					})
					.setNegativeButton("Да,сжечь 1 ресурс",
							new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog,
                                int id) {
                    	   slych().show();
                        	
                       }
                        })
						.setNeutralButton("Нет",
								new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int id) {
                                dialog.cancel();
                            }
                        });
			return builder.create();
	   
	        }
	
	
		}
	
	
			





