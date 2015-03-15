package com.castleburg.logic;


import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

public class arPlayer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public Player[] ar;
	public int cur;
	
	//инизиалиировние игроков
	public arPlayer(int n){
		ar=new Player[n];
		for(int i=0;i<n;i++) {
			ar[i]=new Player(100,100,100,100,100,100,i);
		}
		//сортировка по возрастанию чисел на кубиках
		Arrays.sort(ar);
		cur=7;
		
	}
	
	//перекидывание кубиков у всех игроков
	public void refresh(){
		for (int i=0;i<ar.length;i++) ar[i].tess.refresh(3);
		Arrays.sort(ar);
	}
	
	//сортировка по номеру
	public void sort(){
		Arrays.sort(ar, new Comparator<Player>() {
            @Override
            public int compare(Player a,Player b) {
                if (a.num>b.num) return 1;
                if (a.num<b.num) return -1;
                return 0;
            }
        });
	}
	
	//следующий игрок
	public Player next(){
		cur++;
		if (cur>=ar.length) cur=0;
		return ar[cur];
	}
	
	//эффекты строений
	public void prepare(int phase){
		for (int i=0;i<ar.length;i++){
			ar[i].prepare(phase);
		}
	}
	
	//имитация очереди
	public String queue(){
		StringBuilder sb=new StringBuilder();
		for (int i=cur;i<ar.length;i++){
			sb.append(ar[i].num);
		}
		for (int i=0;i<cur;i++){
			sb.append(ar[i].num);
		}
		return sb.toString();
	}
	
	//проверка есть ли хоть у одного иигрока кубики
	public boolean empty(){
		int sum=0;
		for (int i=0;i<ar.length;i++) sum+=ar[i].tess.toStringSum();
		if (sum==0) return false; else return true;
	}
	
	
	//сериализация
	private void writeObject(java.io.ObjectOutputStream out)
			throws IOException {
		out.writeInt(ar.length);
		for (int i=0;i<ar.length;i++) out.writeObject(ar[i]);
	}
	
	//десериализация
	private void readObject(java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		ar=new Player[in.readInt()];
		for (int i=0;i<ar.length;i++) ar[i]=(Player)in.readObject();
		cur=0;
	}
	

}
