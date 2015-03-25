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
			//������ ,���� �� ��� ������ ���������� �� ���� ����� ����������
		}
		if (pos[0][1]==true && player.gold>=1 && player.wood>=1 && po[0][1]==false){
			player.gold--;
			player.wood--;
			po[0][1]=true;
			//������� � 2 � ������ � ����� ���� +
		}
		if (pos[0][2]==true && player.gold>=1 && player.stone>=1 && po[0][2]==false) {
			player.war++;
			player.win++;
			player.gold--;
			player.stone--;
			po[0][2]=true;//���� +1 � �����+
		}
		if(pos[0][3]==true && player.wood>=2 && po[0][3]==false){
			 player.wood-=2;
			 player.war++;
			 po[0][3]=true;//������� +1 � ����� (+ 2 ���� �����)+
		}
		if(pos[0][4]==true && player.wood>=1 && po[0][4]==false){
			player.wood--;
			po[0][4]=true;
			//�������� +1 � ����� ������ ��������+
		}
		if (pos[1][0]==true && player.gold>=3 && player.stone>=1 && po[1][0]==false){
			player.gold-=3;
			player.stone--;
			player.win+=5;
			po[1][0]=true;
			//������� ���� ����� �� ������� <= 7 �� ����� �����������
		}
if (pos[1][1]==true && player.wood>=2 && player.gold>=2 && po[1][1]==false){
	player.wood-=2;
	player.wood-=2;
	player.win++;
	po[1][1]=true;//�����
}
		if (pos[1][2]==true && player.gold>=1 && player.wood>=2 && po[1][2]==false) {
			player.war++;
			player.win+=2;
			player.gold--;
			player.wood-=2;
			po[1][2]=true;//������� +1 � �����+
		}
		if (pos[1][3]==true && player.gold>=1 && player.wood>=1 && player.stone>=1 && po[1][3]==false ){
			player.gold--;	
			player.wood--;
			player.stone--;
			player.win+=2;
			po[1][3]=true;
			//������� ���� �������� �� �������� ��������� �� +1 � ��� �������������+
		}
		if (pos[1][4]==true && player.wood>=1 && player.stone>=1 && po[1][4]==false ){
			player.wood--;
			player.stone--;
			player.win++;
			po[1][4]=true;
			//��������� ,�� ���� ������ ������ ��� �������� �� 2 � 3 ������
		}
				if (pos[2][0]==true && player.gold>=2 && player.wood>=1 && player.stone>=2 &&po[2][0]==false && po[1][4]==true){
					player.gold-=2;
					player.wood--;
					player.stone-=2;
					player.win+=7;
					po[2][0]=true;//������� ��� ����� ������ ������� +1 � �����+
				} else
		if (pos[2][0]==true && player.gold>=3 && player.wood>=1 && player.stone>=2 &&po[2][0]==false){
			player.gold-=3;
			player.wood--;
			player.stone-=2;
			player.win+=7;
			po[2][0]=true;//������� ��� ����� ������ ������� +1 � �����+
		}
				if (pos[2][1]==true && player.gold>=1 && player.wood>=3 && player.stone>=1 &&po[2][1]==false && po[1][4]==true){
					player.gold-=1;
					player.wood-=3;
					player.stone--;	
					player.win+=2;
					po[2][1]=true;//����� � ������ ���� +1 ����� �� -1 � �����
				}else
		if (pos[2][1]==true && player.gold>=2 && player.wood>=3 && player.stone>=1 &&po[2][1]==false){
			player.gold-=2;
			player.wood-=3;
			player.stone--;	
			player.win+=2;
			po[2][1]=true;//����� � ������ ���� +1 ����� �� -1 � �����
		}
		if (pos[2][2]==true && player.gold>=1 && player.wood>=2 && player.stone>=1 &&po[2][2]==false && po[1][4]==true){
			player.gold-=1;
			player.wood-=2;
			player.stone--;	
			player.win+=4;
			po[2][2]=true;//������� � ���� ���� ����� ������� ����� 1 �����
		}else
		if (pos[2][2]==true && player.gold>=2 && player.wood>=2 && player.stone>=1 &&po[2][2]==false){
			player.gold-=2;
			player.wood-=2;
			player.stone--;	
			player.win+=4;
		    po[2][2]=true;//������� � ���� ���� ����� ������� ����� 1 �����
		}
		if (pos[2][3]==true && player.gold>=1 &&  player.stone>=2 &&po[2][3]==false && po[1][4]==true){
			player.gold-=1;	
			player.stone-=2;
			player.war++;
			player.win+=2;
			po[2][3]=true;//�������� ����� ,+1 � �����, ������ ��� ��������� ���+
		}else
		if (pos[2][3]==true && player.gold>=2 &&  player.stone>=2 &&po[2][3]==false){
			player.gold-=2;	
			player.stone-=2;
			player.war++;
			player.win+=2;
			po[2][3]=true;//�������� ����� ,+1 � �����, ������ ��� ��������� ���+
		}
		if (pos[2][4]==true && player.gold>=1 && player.wood>=1 && player.stone>=1 &&po[2][4]==false && po[1][4]==true){
			player.gold-=1;	
			player.stone--;	
			player.wood--;	
			player.win+=2;
			po[2][4]=true;//������,����� ������ +2 � ������ ��� 1 ����� ��� �� �������� �������� ����
		}else
		if (pos[2][4]==true && player.gold>=2 && player.wood>=1 && player.stone>=1 &&po[2][4]==false){
			player.gold-=2;	
			player.stone--;	
			player.wood--;	
			player.win+=2;
			po[2][4]=true;//������,����� ������ +2 � ������ ��� 1 ����� ��� �� �������� �������� ����
		}
		if (pos[3][0]==true && player.gold>=4 &&  player.stone>=3 &&po[3][0]==false && po[1][4]==true){
			player.gold-=4;	
			player.stone-=3;
			player.win+=9;
			po[3][0]=true;//�����,� ������ ���� 1 �������� ���� �� ������ 2 ������
		}else 
		if (pos[3][0]==true && player.gold>=5 &&  player.stone>=3 &&po[3][0]==false){
			player.gold-=5;	
			player.stone-=3;
			player.win+=9;
			po[3][0]=true;//�����,� ������ ���� 1 �������� ���� �� ������ 2 ������
		}
		if (pos[3][1]==true && player.gold>=2 && player.wood>=1 && player.stone>=2 &&po[3][1]==false && po[1][4]==true){
			player.gold-=2;	
			player.stone--;	
			player.wood-=2;	
			player.win+=4;
			po[3][1]=true;//�������� �������,+1 ������ � ������ ������� ������+
		}else
		if (pos[3][1]==true && player.gold>=3 && player.wood>=1 && player.stone>=2 &&po[3][1]==false){
			player.gold-=3;	
			player.stone--;	
			player.wood-=2;	
			player.win+=4;
			po[3][1]=true;//�������� �������,+1 ������ � ������ ������� ������+
		}
		if (pos[3][2]==true && player.gold>=2 && player.wood>=2 && player.stone>=2 && po[3][2]==false && po[1][4]==true){
			player.war+=2;
			player.win+=6;
			player.gold-=2;
			player.wood-=2;
			player.stone-=2;
			po[3][2]=true;//������� ����� +2 � �����+
		}else
		if (pos[3][2]==true && player.gold>=3 && player.wood>=2 && player.stone>=2 && po[3][2]==false){
			player.war+=2;
			player.win+=6;
			player.gold-=3;
			player.wood-=2;
			player.stone-=2;
			po[3][2]=true;//������� ����� +2 � �����+
		}
		if (pos[3][3]==true && player.gold>=2 &&  player.stone>=2 &&po[3][3]==false && po[1][4]==true){
			player.gold-=2;	
			player.stone-=2;
			player.win+=4;
			player.war++;
			po[3][3]=true;//��������,+1 � �����,��� ������ � ����� +1 ������� ���� �������������+
		}else
		if (pos[3][3]==true && player.gold>=3 &&  player.stone>=2 &&po[3][3]==false){
			player.gold-=3;	
			player.stone-=2;
			player.win+=4;
			player.war++;
			po[3][3]=true;//��������,+1 � �����,��� ������ � ����� +1 ������� ���� �������������+
		}
		if (pos[3][4]==true && player.gold>=1 && player.wood>=2 && player.stone>=2 && po[3][4]==false && po[1][4]==true){
			player.win+=4;
			player.gold-=1;
			player.wood-=2;
			player.stone-=2;
			po[3][4]=true;//����������,� ����� ������ +1 �������� ����+
		}else
		if (pos[3][4]==true && player.gold>=2 && player.wood>=2 && player.stone>=2 && po[3][4]==false){
			player.win+=4;
			player.gold-=2;
			player.wood-=2;
			player.stone-=2;
			po[3][4]=true;//����������,� ����� ������ +1 �������� ����+
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

	//������� ��������
	public void prepare(Player player,int phase){
		if (phase==5 && po[0][1]==true) player.plus++;
		if ((phase==1 || phase==3 ||phase== 5) && po[3][1]==true) player.gold++;
		if ((phase==2 || phase==4 || phase==6)&& po[3][3]==true) player.win++;
		
		
		
		//�������� :)
		//Log.d("LOG",phase+"");
	}

}