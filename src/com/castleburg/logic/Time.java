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
	
	//��������� ����
	public void next(Player player,arPlayer ar){
		//������� ��������
		ar.prepare(player,phase);
		phase++;
		if (phase>8) {year++;phase=1;}
		//����� ������ ���� � ����� ��� ������ ����� �����
		if (phase%2==1 && phase!=8) ar.refresh();
		Log.d("LOG", phase+"");
	}
	
	//������������� ������� � ������
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append(year+" ��� ");
		if (phase%8==0) sb.append("8 ����"); else sb.append(phase%8+" ����");
		return sb.toString();
	}
	
	//������������
	private void writeObject(java.io.ObjectOutputStream out)
			throws IOException {
		out.writeInt(year);
		out.writeInt(phase);
	}
	
	//��������������
	private void readObject(java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		year=in.readInt();
		phase=in.readInt();
	}
	
}
