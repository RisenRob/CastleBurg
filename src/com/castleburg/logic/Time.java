package com.castleburg.logic;


import java.io.IOException;
import java.io.Serializable;

import android.util.Log;

public class Time implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int year;
	public int phase;
	
	public Time(){
		year=1;
		phase=1;
	}
	
	public Time(int myear,int mphase){
		year=myear;
		phase=mphase;
		
	}
	
	//следующая фаза
	public void next(Player player,arPlayer ar){
		//эффекты строений
		ar.prepare(player,phase);
		phase++;
		if (phase>8) {year++;phase=1;}
		//Очень важная чать я искал эту оишбку очень долго
		if (phase%2==1 && phase!=8) ar.refresh();
		Log.d("LOG", phase+"");
	}
	
	//преобразовние времени в строку
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append(year+" Год ");
		if (phase%8==0) sb.append("8 фаза"); else sb.append(phase%8+" фаза");
		return sb.toString();
	}
	
	//сериализация
	private void writeObject(java.io.ObjectOutputStream out)
			throws IOException {
		out.writeInt(year);
		out.writeInt(phase);
	}
	
	//десериализация
	private void readObject(java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		year=in.readInt();
		phase=in.readInt();
	}
	
}
