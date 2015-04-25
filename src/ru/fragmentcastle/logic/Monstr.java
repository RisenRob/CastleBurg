package ru.fragmentcastle.logic;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Monstr implements Serializable {

	private static final long serialVersionUID = 1L;
	public String name;
	public int war, wwin, wwood, wgold, wstone, lwin, lwood, lgold, lstone,id;
	public boolean destroy;
	private DataBaseHelper myDbHelper;
	private SQLiteDatabase db;

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


	public Monstr(int year,Context ctx){
		myDbHelper = new DataBaseHelper(ctx);

		try {
			myDbHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			myDbHelper.openDataBase();
		}catch(SQLException sqle){
			throw sqle;
		}

		db = myDbHelper.getWritableDatabase();
		Cursor c;
		String selection ="level==?";
		String[] arg=new String[]{year+""};
		c = db.query("Monstr", null, selection, arg, null, null,null);
		id=(int)(Math.random()*5);
		if  (c.moveToPosition(id)) toMonstr(c);
		//Log.d("LOG","Монстр:"+name);
		
	}
	
	public Monstr(int year,int id,Context ctx){
		this.id=id;
		myDbHelper = new DataBaseHelper(ctx);

		try {
			myDbHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			myDbHelper.openDataBase();
		}catch(SQLException sqle){
			throw sqle;
		}

		db = myDbHelper.getWritableDatabase();
		Cursor c;
		String selection ="level==?";
		String[] arg=new String[]{year+""};
		c = db.query("Monstr", null, selection, arg, null, null,null);
		if  (c.moveToPosition(this.id)) toMonstr(c);
		
	}
	
	public String[] win_monstr(){
		ArrayList<String> res=new ArrayList<String>();
		if (wwin!=0) res.add(wwin+"p");
		if (wwood!=0) res.add(wwood+"w");
		if (wgold!=0) res.add(wgold+"g");
		if (wstone!=0) res.add(wstone+"s");
		return res.toArray(new String[res.size()]);
	}
	
	public String[] lose_monstr(){
		ArrayList<String> res=new ArrayList<String>();
		if (lwin!=0) res.add(lwin+"p");
		if (lwood!=0) res.add(lwood+"w");
		if (lgold!=0) res.add(lgold+"g");
		if (lstone!=0) res.add(lstone+"s");
		return res.toArray(new String[res.size()]);
	}
	
	
	private void toMonstr(Cursor c){
		int nameColIndex = c.getColumnIndex("name");
		int warColIndex = c.getColumnIndex("war");
		int wwinColIndex = c.getColumnIndex("wwin");
		int wwoodColIndex = c.getColumnIndex("wwood");
		int wgoldColIndex = c.getColumnIndex("wgold");
		int wstoneColIndex = c.getColumnIndex("wstone");
		int lwinColIndex = c.getColumnIndex("lwin");
		int lwoodColIndex = c.getColumnIndex("lwood");
		int lgoldColIndex = c.getColumnIndex("lgold");
		int lstoneColIndex = c.getColumnIndex("lstone");
		war=c.getInt(warColIndex);
		name=c.getString(nameColIndex);
		wwin=c.getInt(wwinColIndex);
		wwood=c.getInt(wwoodColIndex);
		wgold=c.getInt(wgoldColIndex);
		wstone=c.getInt(wstoneColIndex);
		lwin=c.getInt(lwinColIndex);
		lwood=c.getInt(lwoodColIndex);
		lgold=c.getInt(lgoldColIndex);
		lstone=c.getInt(lstoneColIndex);
	}
	
	

}