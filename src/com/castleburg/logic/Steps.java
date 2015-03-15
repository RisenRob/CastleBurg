package com.castleburg.logic;
import java.util.ArrayList;


public class Steps extends ArrayList<StringBuilder>{
	
	private static final long serialVersionUID = 1L;
	
	private StringBuilder check;
	
	//конструтор
	public Steps(){
		super();
		check=new StringBuilder();
	}
	
	//инициализирование массива
	public static Steps[] arSteps(int n){
		Steps[] steps=new Steps[n];
		for (int i=0;i<n;i++) steps[i]=new Steps();
		return steps;
	}
	
	//доабвлние элемента
	public boolean add(StringBuilder step,String comb){
		if (check.indexOf(comb)==-1) {
			super.add(step);
			check.append(comb+" ");
			return true;
		} else return false;

	}
	
}