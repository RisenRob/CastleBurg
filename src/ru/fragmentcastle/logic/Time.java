package ru.fragmentcastle.logic;


import java.io.IOException;
import java.io.Serializable;

public class Time implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int year;
	public static int phase;
	
	public Time(){
		year=1;
		phase=1;
	}
	
	public Time(int myear,int mphase){
		year=myear;
		phase=mphase;
		
	}
	
	public void next(){
		phase++;
		if (phase>7) {year++;phase=1;}
	}
	
	//следующа€ фаза
	public void next(arPlayer ar){
		//эффекты строений
		ar.prepare(phase,year);
		phase++;
		if (phase>7) {year++;phase=1;}
		if (phase==1 || phase==3 || phase==5) ar.refresh(); 
		if (phase==2 || phase==4 || phase==6) ar.sort();
	}
	
	//преобразовние времени в строку
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append(year+" √од ");
		if (phase%8==0) sb.append("8 фаза"); else sb.append(phase%8+" фаза");
		return sb.toString();
	}
	
	//сериализаци€
	private void writeObject(java.io.ObjectOutputStream out)
			throws IOException {
		out.writeInt(year);
		out.writeInt(phase);
	}
	
	//десериализаци€
	private void readObject(java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		year=in.readInt();
		phase=in.readInt();
	}
	
}
