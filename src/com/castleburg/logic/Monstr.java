package com.castleburg.logic;
public class Monstr {
	String name;
	int war, wwin, wwood, wgold, wstone, lwin, lwood, lgold, lstone;
	
	public Monstr(String mname,int mwar,int mwwin,int mwwood,int mwgold,
			int mwstone,int mlwin,int mlwood,int mlgold,int mlstone){
		war=mwar;
		name=mname;
		wwin=mwwin;
		wwood=mwwood;
		wgold=mwgold;
		wstone=mwstone;
		lwin=mlwin;
		lwood=mlwood;
		lgold=mlgold;
		lstone=mlstone;
	}
	
	
}