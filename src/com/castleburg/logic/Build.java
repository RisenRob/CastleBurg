package com.castleburg.logic;


import java.io.IOException;
import java.io.Serializable;


public class Build implements Serializable{

	private static final long serialVersionUID = 1L;
	final String TAG="Build";
	public boolean[][]po=new boolean [5][6];
	public boolean[][]pos=new boolean [5][6];
	public boolean[][]sosbut=new boolean [5][6];
	
	
	public Build(){
		for (int i=0;i<5;i++){
			sosbut[0][i]=true;
		}
	}	
	public  void build (Player player,boolean [][] pos) {
	
		if(pos[0][0]==true && player.gold>=2 && po[0][0]==false){
			player.gold-=2;
			player.win+=3;
			po[0][0]=true;
			//Статуя ,если на все кубики одинаковые то один можно перекинуть
		}
		if (pos[0][1]==true && player.gold>=1 && player.wood>=1 && po[0][1]==false){
			player.gold--;
			player.wood--;
			po[0][1]=true;
			//Трактир у 2 к кубику в конце лета +
		}
		if (pos[0][2]==true && player.gold>=1 && player.stone>=1 && po[0][2]==false) {
			player.war++;
			player.win++;
			player.gold--;
			player.stone--;
			po[0][2]=true;//Форт +1 в битве+
		}
		if(pos[0][3]==true && player.wood>=2 && po[0][3]==false){
			 player.wood-=2;
			 player.war++;
			 po[0][3]=true;//Палисад +1 в битве (+ 2 если зомби)+
		}
		if(pos[0][4]==true && player.wood>=1 && po[0][4]==false){
			player.wood--;
			po[0][4]=true;
			//частокол +1 в битве против гоблинов+
		}
		if (pos[1][0]==true && player.gold>=3 && player.stone>=1 && po[1][0]==false){
			player.gold-=3;
			player.stone--;
			player.win+=5;
			po[1][0]=true;
			//Часовня если сумма на кубиках <= 7 то можно перебросить
		}
if (pos[1][1]==true && player.wood>=2 && player.gold>=2 && po[1][1]==false){
	player.wood-=2;
	player.wood-=2;
	player.win++;
	po[1][1]=true;//Рынок
}
		if (pos[1][2]==true && player.gold>=1 && player.wood>=2 && po[1][2]==false) {
			player.war++;
			player.win+=2;
			player.gold--;
			player.wood-=2;
			po[1][2]=true;//Кузница +1 в битве+
		}
		if (pos[1][3]==true && player.gold>=1 && player.wood>=1 && player.stone>=1 && po[1][3]==false ){
			player.gold--;	
			player.wood--;
			player.stone--;
			player.win+=2;
			po[1][3]=true;
			//Конюшни елси повлеяли на военного советника то +1 в бою дополнительно+
		}
		if (pos[1][4]==true && player.wood>=1 && player.stone>=1 && po[1][4]==false ){
			player.wood--;
			player.stone--;
			player.win++;
			po[1][4]=true;
			//Подъёмник ,на одно золото менбше при строение из 2 и 3 колоны
		}
				if (pos[2][0]==true && player.gold>=2 && player.wood>=1 && player.stone>=2 &&po[2][0]==false && po[1][4]==true){
					player.gold-=2;
					player.wood--;
					player.stone-=2;
					player.win+=7;
					po[2][0]=true;//Церковь при битве против демонов +1 в битве+
				} else
		if (pos[2][0]==true && player.gold>=3 && player.wood>=1 && player.stone>=2 &&po[2][0]==false){
			player.gold-=3;
			player.wood--;
			player.stone-=2;
			player.win+=7;
			po[2][0]=true;//Церковь при битве против демонов +1 в битве+
		}
				if (pos[2][1]==true && player.gold>=1 && player.wood>=3 && player.stone>=1 &&po[2][1]==false && po[1][4]==true){
					player.gold-=1;
					player.wood-=3;
					player.stone--;	
					player.win+=2;
					po[2][1]=true;//Фермы в каждом ходу +1 кубик но -1 в битве
				}else
		if (pos[2][1]==true && player.gold>=2 && player.wood>=3 && player.stone>=1 &&po[2][1]==false){
			player.gold-=2;
			player.wood-=3;
			player.stone--;	
			player.win+=2;
			po[2][1]=true;//Фермы в каждом ходу +1 кубик но -1 в битве
		}
		if (pos[2][2]==true && player.gold>=1 && player.wood>=2 && player.stone>=1 &&po[2][2]==false && po[1][4]==true){
			player.gold-=1;
			player.wood-=2;
			player.stone--;	
			player.win+=4;
			po[2][2]=true;//Казармы в фазе наёма войск платить всего 1 тавар
		}else
		if (pos[2][2]==true && player.gold>=2 && player.wood>=2 && player.stone>=1 &&po[2][2]==false){
			player.gold-=2;
			player.wood-=2;
			player.stone--;	
			player.win+=4;
		    po[2][2]=true;//Казармы в фазе наёма войск платить всего 1 тавар
		}
		if (pos[2][3]==true && player.gold>=1 &&  player.stone>=2 &&po[2][3]==false && po[1][4]==true){
			player.gold-=1;	
			player.stone-=2;
			player.war++;
			player.win+=2;
			po[2][3]=true;//Каменные стены ,+1 в битве, Победа при равенстве сил+
		}else
		if (pos[2][3]==true && player.gold>=2 &&  player.stone>=2 &&po[2][3]==false){
			player.gold-=2;	
			player.stone-=2;
			player.war++;
			player.win+=2;
			po[2][3]=true;//Каменные стены ,+1 в битве, Победа при равенстве сил+
		}
		if (pos[2][4]==true && player.gold>=1 && player.wood>=1 && player.stone>=1 &&po[2][4]==false && po[1][4]==true){
			player.gold-=1;	
			player.stone--;	
			player.wood--;	
			player.win+=2;
			po[2][4]=true;//Ратуша,можно отдать +2 к кубику или 1 товар что бы получить победное очко
		}else
		if (pos[2][4]==true && player.gold>=2 && player.wood>=1 && player.stone>=1 &&po[2][4]==false){
			player.gold-=2;	
			player.stone--;	
			player.wood--;	
			player.win+=2;
			po[2][4]=true;//Ратуша,можно отдать +2 к кубику или 1 товар что бы получить победное очко
		}
		if (pos[3][0]==true && player.gold>=4 &&  player.stone>=3 &&po[3][0]==false && po[1][4]==true){
			player.gold-=4;	
			player.stone-=3;
			player.win+=9;
			po[3][0]=true;//Собор,в концке игры 1 победное очко за каждые 2 товара
		}else 
		if (pos[3][0]==true && player.gold>=5 &&  player.stone>=3 &&po[3][0]==false){
			player.gold-=5;	
			player.stone-=3;
			player.win+=9;
			po[3][0]=true;//Собор,в концке игры 1 победное очко за каждые 2 товара
		}
		if (pos[3][1]==true && player.gold>=2 && player.wood>=1 && player.stone>=2 &&po[3][1]==false && po[1][4]==true){
			player.gold-=2;	
			player.stone--;	
			player.wood-=2;	
			player.win+=4;
			po[3][1]=true;//Торговая гильдия,+1 золото в начале каждого урожая+
		}else
		if (pos[3][1]==true && player.gold>=3 && player.wood>=1 && player.stone>=2 &&po[3][1]==false){
			player.gold-=3;	
			player.stone--;	
			player.wood-=2;	
			player.win+=4;
			po[3][1]=true;//Торговая гильдия,+1 золото в начале каждого урожая+
		}
		if (pos[3][2]==true && player.gold>=2 && player.wood>=2 && player.stone>=2 && po[3][2]==false && po[1][4]==true){
			player.war+=2;
			player.win+=6;
			player.gold-=2;
			player.wood-=2;
			player.stone-=2;
			po[3][2]=true;//Гильдия магов +2 в битве+
		}else
		if (pos[3][2]==true && player.gold>=3 && player.wood>=2 && player.stone>=2 && po[3][2]==false){
			player.war+=2;
			player.win+=6;
			player.gold-=3;
			player.wood-=2;
			player.stone-=2;
			po[3][2]=true;//Гильдия магов +2 в битве+
		}
		if (pos[3][3]==true && player.gold>=2 &&  player.stone>=2 &&po[3][3]==false && po[1][4]==true){
			player.gold-=2;	
			player.stone-=2;
			player.win+=4;
			player.war++;
			po[3][3]=true;//Крепость,+1 в битве,при победе в битве +1 победно очко дополнительно+
		}else
		if (pos[3][3]==true && player.gold>=3 &&  player.stone>=2 &&po[3][3]==false){
			player.gold-=3;	
			player.stone-=2;
			player.win+=4;
			player.war++;
			po[3][3]=true;//Крепость,+1 в битве,при победе в битве +1 победно очко дополнительно+
		}
		if (pos[3][4]==true && player.gold>=1 && player.wood>=2 && player.stone>=2 && po[3][4]==false && po[1][4]==true){
			player.win+=4;
			player.gold-=1;
			player.wood-=2;
			player.stone-=2;
			po[3][4]=true;//Посольство,в конце уражая +1 победное очко+
		}else
		if (pos[3][4]==true && player.gold>=2 && player.wood>=2 && player.stone>=2 && po[3][4]==false){
			player.win+=4;
			player.gold-=2;
			player.wood-=2;
			player.stone-=2;
			po[3][4]=true;//Посольство,в конце уражая +1 победное очко+
		}


	}
	private void writeObject(java.io.ObjectOutputStream out)
			throws IOException,ClassNotFoundException {
		for (int i=0;i<5;i++){
			for (int j=0;j<6;j++){
				out.writeBoolean(po[i][j]);
			}
		}
		for (int i=0;i<5;i++){
			for (int j=0;j<6;j++){
				out.writeBoolean(sosbut[i][j]);
			}
		}
		for (int i=0;i<5;i++){
			for (int j=0;j<6;j++){
				out.writeBoolean(pos[i][j]);
			}
		}
	}

	private void readObject(java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		boolean[][] p=new boolean[5][6];
		for (int i=0;i<5;i++){
			for (int j=0;j<6;j++){
				p[i][j]=in.readBoolean();
			}
		}
		po=p;
		boolean[][] x=new boolean[5][6];
		for (int i=0;i<5;i++){
			for (int j=0;j<6;j++){
				x[i][j]=in.readBoolean();
			}
		}
		sosbut=x;
		boolean[][] l=new boolean[5][6];
		for (int i=0;i<5;i++){
			for (int j=0;j<6;j++){
				l[i][j]=in.readBoolean();
			}
		}
		pos=l;
		
		
	}

	//эффекты строений
	public void prepare(Player player,int phase){
		if (phase==5 && po[0][1]==true) player.plus++;
		if ((phase==1 || phase==3 ||phase== 5) && po[3][1]==true) player.gold++;
		if ((phase==2 || phase==4 || phase==6)&& po[3][3]==true) player.win++;
		
		
		
		//Мешались :)
		//Log.d("LOG",phase+"");
	}

}