package com.castleburg.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Tessera extends ArrayList<Character>{

	private static final long serialVersionUID = 1L;
	
	public Steps[] steps;
	public boolean market;
	public boolean plus;
	
	//конструктор
	public Tessera(){
		new ArrayList<Character>();
		for (int i=0;i<4;i++) add((char)(Math.random()*6+49));
		sort();
	}
	
	//конструктор через строку
	public Tessera(String s){
		new ArrayList<Character>();
		for (int i=0;i<s.length();i++) add(s.charAt(i));
		sort();
	}
	
	//перекидывание кубиков
	public void refresh(int n){
		clear();
		for (int i=0;i<n;i++) add((char)(Math.random()*6+49));
		sort();
	}
	
	//сортиврока
	private void sort(){
		
		Collections.sort(this,new Comparator<Character>() {
            @Override
            public int compare(Character a,Character b) {
                if (a>b) return 1;
                if (a<b) return -1;
                return 0;
            }
        });
	}
	
	//подсчёт вариантов
	public void count(){
		steps=Steps.arSteps(40);
		for (int i=0;i<size();i++){
			count_rec(i,0,new StringBuilder());
		}		
	}
	
	private void count_rec(int n,int sum,StringBuilder step){
		if (n<size()){
			step.append(n);
			sum+=get(n)-48;
			steps[sum].add(step,toComb(step));
			for (int i=1;i<size();i++) count_rec(n+i,sum,new StringBuilder(step));
			if (market){
				StringBuilder sbmarket=new StringBuilder(step);
				sbmarket.append('p');
				steps[sum+1].add(sbmarket,toComb(sbmarket));
				steps[sum+1].add(sbmarket,toComb(sbmarket));
			}
			if (plus) {
				StringBuilder sbplus=new StringBuilder(step);
				sbplus.append('l');
				steps[sum+2].add(sbplus,toComb(sbplus));
			}
		}
		
	}
	
	//преобразвоние из step в комбинацию
	private String toComb(StringBuilder step){
		StringBuilder comb=new StringBuilder();
		int num;
		for (int i=0;i<step.length();i++){
			if (step.charAt(i)!='p' && step.charAt(i)!='l') {
				num=Integer.parseInt(step.charAt(i)+"");
				comb.append(get(num).charValue());
			} else comb.append(step.charAt(i));
		}
		return comb.toString();
	}
	
	public String toComb(int num,int posistion){
		return toComb(steps[num].get(posistion));
	}
	
	//преобразование в строку
	public String toString(){
		StringBuilder sb=new StringBuilder();
		for (int i=0;i<size();i++) sb.append(get(i));
		return sb.toString();
		
	}

	//удаление комбинации
	public void del(int sum,int n){
		StringBuilder step=steps[sum].get(n);
		ArrayList<Character> test=new ArrayList<Character>();
		for (int i=0;i<step.length();i++) {
			if (step.charAt(i)!='p' && step.charAt(i)!='l'){
				test.add(get((int)step.charAt(i)-48));	
			} else if (step.charAt(i)=='p') market=false; else plus=false;
		}
		removeAll(test);
		sort();
	}
	//исправить рынок(Сделано, вроде)
	//подсчёт суммы чисел на кубиках
	public int toStringSum(){
		int sum=0;
		char c;
		for (int i=0;i<size();i++){
			c=get(i);
			if (c!='p' && c!='l') sum+=(int)get(i)-'0';
		}
		return sum;
	}
	
}
