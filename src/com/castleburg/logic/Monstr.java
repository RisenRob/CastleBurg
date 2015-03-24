package com.castleburg.logic;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.castleburg.DataBaseHelper;

public class Monstr {
	public String name;
	public int war, wwin, wwood, wgold, wstone, lwin, lwood, lgold, lstone;
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

		if (c.moveToFirst()){
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
		//Toast.makeText(ctx, name+"",Toast.LENGTH_SHORT ).show();
	}


}