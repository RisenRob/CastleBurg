package ru.fragmentcastle.logic;


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
			ar[i]=new Player(0,0,0,0,0,0,i);
		}
		//сортировка по возрастанию чисел на кубиках
		Arrays.sort(ar);
		cur=7;
		
	}
	
	public byte[] getBytes(){
		byte[][] a;
		a=new byte[ar.length][];
		int size=ar.length+6,cur=6;
		for (int i=0;i<ar.length;i++){
			a[i]=ar[i].getBytes();
			size+=a[i].length;
		}
		byte[] arpl=new byte[size];
		size-=4;
		arpl[0]=101;
		arpl[1]=(byte) (size/100);
		arpl[2]=(byte) (size/10%10);
		arpl[3]=(byte) (size%10);
		arpl[4]=(byte) this.cur;
		arpl[5]=(byte) ar.length;
		for (int i=0;i<ar.length;i++){
			arpl[cur]=(byte) a[i].length;
			cur++;
			for (int j=0;j<a[i].length;j++){
				arpl[cur]=a[i][j];
				cur++;
			}
		}
		return arpl;
	}
	
	public arPlayer(byte[] buf){
		int cur=2;
		this.cur=buf[0];
		byte[][] a=new byte[buf[1]][];
		for (int i=0;i<a.length;i++){
			a[i]=new byte[buf[cur]];
			cur++;
			for (int j=0;j<a[i].length;j++){
				a[i][j]=buf[cur];
				cur++;
			}
		}
		ar=new Player[a.length];
		for (int i=0;i<a.length;i++){
			ar[i]=new Player(a[i]);
		}
	}
	
	//перекидывание кубиков у всех игроков
	//отфиксить
	public void refresh(){
		for (int i=0;i<ar.length;i++) ar[i].refresh(3);
		
		Arrays.sort(ar);
	}
	
	public void fight(Monstr monstr){
		for (int i=0;i<ar.length;i++) ar[i].fight(monstr);
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
		cur=7;
	}
	
	//следующий игрок
	public Player next(){
		cur++;
		if (cur>=ar.length) cur=0;
		return ar[cur];
	}
	
	//эффекты строений
	public void prepare(int phase,int year){
		for (int i=0;i<ar.length;i++){
			ar[i].prepare(phase, year);
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
	
	public String oqueue(){
		StringBuilder sb=new StringBuilder();
		for (int i=0;i<ar.length;i++){
			sb.append((char)(i+'0'));
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
		out.writeInt(cur);
		for (int i=0;i<ar.length;i++) out.writeObject(ar[i]);
	}
	
	//десериализация
	private void readObject(java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		ar=new Player[in.readInt()];
		cur=in.readInt();
		for (int i=0;i<ar.length;i++) ar[i]=(Player)in.readObject();
		
	}
	

}
