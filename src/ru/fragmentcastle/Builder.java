package ru.fragmentcastle;

import ru.fragmentcastle.helpdial.dialog04;
import ru.fragmentcastle.logic.Build;
import ru.fragmentcastle.logic.Player;
import ru.fragmentcastle.logic.Time;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Builder extends Fragment {

	GameActivity game;
	private ImageView image[][]=new ImageView[5][6];
	public boolean[][] pos=new boolean [5][6];
	public boolean[][] sosbutt=new boolean [5][6];		
	public Build build;
	public Player player;
	 DialogFragment dlg4;
	public void  onAttach(Activity activity){
		super.onAttach(activity);
		game=(GameActivity) activity;
	}


	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_builder, container,false);
		image[0][0] = (ImageView)v.findViewById(R.id.imageView1);
		image[0][1] = (ImageView)v.findViewById(R.id.imageView5);
		image[0][2] = (ImageView)v.findViewById(R.id.imageView9);
		image[0][3] = (ImageView)v.findViewById(R.id.imageView13);
		image[0][4] = (ImageView)v.findViewById(R.id.imageView17);
		image[1][0] = (ImageView)v.findViewById(R.id.imageView2);
		image[1][1] = (ImageView)v.findViewById(R.id.imageView6);
		image[1][2] = (ImageView)v.findViewById(R.id.imageView10);
		image[1][3] = (ImageView)v.findViewById(R.id.imageView14);
		image[1][4] = (ImageView)v.findViewById(R.id.imageView18);
		image[2][0] = (ImageView)v.findViewById(R.id.imageView3);
		image[2][1] = (ImageView)v.findViewById(R.id.imageView7);
		image[2][2] = (ImageView)v.findViewById(R.id.imageView11);
		image[2][3] = (ImageView)v.findViewById(R.id.imageView15);
		image[2][4] = (ImageView)v.findViewById(R.id.imageView19);
		image[3][0] = (ImageView)v.findViewById(R.id.imageView4);
		image[3][1] = (ImageView)v.findViewById(R.id.imageView8);
		image[3][2] = (ImageView)v.findViewById(R.id.imageView12);
		image[3][3] = (ImageView)v.findViewById(R.id.imageView16);
		image[3][4] = (ImageView)v.findViewById(R.id.imageView20);
		/*game.arplayer.sort();
		game.next_player();
		game.refresh_players();*/
		 
		if (MainActivity.sta==true && Time.phase==2 && Time.year==1) {
			dlg4 = new dialog04();			  
		    dlg4.show(getFragmentManager(), "dlg4");
		}

		player=game.player;
		build=player.build;
		pos=build.pos;
		sosbutt=build.sosbut;

		///[Столбец][Строка]	


		for (int i=0;i<4;i++)
			for (int j=0;j<5;j++){
				if (sosbutt[i][j] && (game.id==-1 ||game.id==player.num)) image[i][j].setOnClickListener(click); else image[i][j].setClickable(false);
				image[i][j].setImageResource(idim(i,j,build.po[i][j]));
			}
		if (game.time.phase==1 || game.time.phase==3 || game.time.phase==5)pere(game.adapter_player.getItem(game.pos));
		return v;
	}


	public void pere(Player player){	 
		if (player.num==game.arplayer.ar[game.arplayer.cur].num
				&& game.time.phase!=1 && game.time.phase!=3 && game.time.phase!=5)
			refreshp(player);
		else { 
			refreshp(player); 
			for (int i=0;i<4;i++) 
				for (int j=0;j<5;j++) 
					image[i][j].setClickable(false);
		} 


	} 


	public void refreshp(Player player){
		build=player.build;
		sosbutt=build.sosbut;

		for (int i=0;i<4;i++)
			for (int j=0;j<5;j++){
				if (sosbutt[i][j] && (game.id==-1 ||game.id==player.num)) image[i][j].setOnClickListener(click); else image[i][j].setClickable(false);
				image[i][j].setImageResource(idim(i,j,build.po[i][j]));
			}
	}

	public void refresh(){
		player=game.player;
		build=player.build;
		sosbutt=build.sosbut;

		for (int i=0;i<4;i++)
			for (int j=0;j<5;j++){
				//возможна ошибка
				if (sosbutt[i][j] && (game.id==-1 ||game.id==player.num)) image[i][j].setOnClickListener(click);
				else image[i][j].setClickable(false);
				image[i][j].setImageResource(idim(i,j,build.po[i][j]));
			}
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
					
				}
				break;
			case R.id.imageView5:
				pos[0][1]=true;
				build.build(player,pos);
				if (build.po[0][1]){
					image[1][1].setClickable(true);
					image[0][1].setClickable(false);
					
				}
				break;
			case R.id.imageView9:
				pos[0][2]=true;
				build.build(player,pos);
				if (build.po[0][2]){
					image[1][2].setClickable(true);
					image[0][2].setClickable(false);
					
				}
				break;
			case R.id.imageView13:
				pos[0][3]=true;
				build.build(player,pos);
				if (build.po[0][3]){
					image[1][3].setClickable(true);
					image[0][3].setClickable(false);
					
				}
				break;
			case R.id.imageView17:
				pos[0][4]=true;
				build.build(player,pos);
				if (build.po[0][4]){
					image[1][4].setClickable(true);
					image[0][4].setClickable(false);
					
				}
				break;
			case R.id.imageView2:
				pos[1][0]=true;
				build.build(player,pos);
				if (build.po[1][0]){
					image[2][0].setClickable(true);
					image[1][0].setClickable(false);
					
				}
				break;
			case R.id.imageView6:
				pos[1][1]=true;
				build.build(player,pos);
				if (build.po[1][1]){
					image[2][1].setClickable(true);
					image[1][1].setClickable(false);
				}
				break;
			case R.id.imageView10:
				pos[1][2]=true;
				build.build(player,pos);
				if (build.po[1][2]){
					image[2][2].setClickable(true);
					image[1][2].setClickable(false);
					
				}         
				break;
			case R.id.imageView14:
				pos[1][3]=true;
				build.build(player,pos);
				if (build.po[1][3]){
					image[2][3].setClickable(true);
					image[1][3].setClickable(false);
					
				}
				break;
			case R.id.imageView18:
				pos[1][4]=true;
				build.build(player,pos);
				if (build.po[1][4]){
					image[2][4].setClickable(true);
					image[1][4].setClickable(false);
					
				}
				break;
			case R.id.imageView3:
				pos[2][0]=true;
				build.build(player,pos);
				if (build.po[2][0]){
					image[3][0].setClickable(true);
					image[2][0].setClickable(false);
								}
				break;
			case R.id.imageView7:
				pos[2][1]=true;
				build.build(player,pos);
				if (build.po[2][1]){
					image[3][1].setClickable(true);
					image[2][1].setClickable(false);
				;
				}
				break;
			case R.id.imageView11:
				pos[2][2]=true;
				build.build(player,pos);
				if (build.po[2][2]){
					image[3][2].setClickable(true);
					image[2][2].setClickable(false);
					
				}
				break;
			case R.id.imageView15:
				pos[2][3]=true;
				build.build(player,pos);
				if (build.po[2][3]){
					image[3][3].setClickable(true);
					image[2][3].setClickable(false);
					
				}
				break;
			case R.id.imageView19:
				pos[2][4]=true;
				build.build(player,pos);
				if (build.po[2][4]){
					image[3][4].setClickable(true);
					image[2][4].setClickable(false);
					
				}
				break;
			case R.id.imageView4:
				pos[3][0]=true;
				build.build(player,pos);
				if (build.po[3][0]){
					image[3][0].setClickable(false);
					
				}
				break;
			case R.id.imageView8:
				pos[3][1]=true;
				build.build(player,pos);
				if (build.po[3][1]){
					image[3][1].setClickable(false);   
				
				}
				break;
			case R.id.imageView12:
				pos[3][2]=true;    	
				build.build(player,pos);
				if (build.po[3][2]){
					image[3][2].setClickable(false); 
				
				}
				break;
			case R.id.imageView16:
				pos[3][3]=true;
				build.build(player,pos);
				if (build.po[3][3]){
					image[3][3].setClickable(false);    	
					
				}
				break;
			case R.id.imageView20:
				pos[3][4]=true;
				build.build(player,pos);
				if (build.po[3][4]){
					image[3][4].setClickable(false);    	
					
				}
				break; 	   	

			}
			for (int i=0;i<4;i++)
				for (int j=0;j<5;j++){
					sosbutt[i][j]=image[i][j].isClickable();

				}
			
			build.sosbut=sosbutt;
			build.pos=pos;
			
			
			refresh();
			for (int i=0;i<4;i++)
				for (int j=0;j<5;j++){
					image[i][j].setClickable(false);
				}
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

}
