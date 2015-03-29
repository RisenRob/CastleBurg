package ru.fragmentcastle.logic;

public class Main {

	public static void main(String[] args) {
		tessera();
	}
	
	public static void tessera(){
		Tessera test=new Tessera("235");
		for (int i=0;i<test.size();i++)	System.out.println(test.get(i));
		for (int i=0;i<40;i++){
			System.out.println(i+":"+test.steps[i]);
		}
		System.out.println("------------------------------------"+test.toStringSum());
		test.del(8, 0);
		for (int i=0;i<test.size();i++)	System.out.println(test.get(i));
		for (int i=0;i<40;i++){
			System.out.println(i+":"+test.steps[i]);
		}
		
		//System.out.print(test.toComb(new StringBuilder("12")));
	}

}
