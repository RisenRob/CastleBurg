package com.castleburg;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.castleburg.logic.Monstr;
import com.castleburg.logic.arPlayer;

public class MonstrActivity extends Activity {
	
	private arPlayer arplayer;
	private int year;
	Intent intent;
	private DataBaseHelper myDbHelper;
    private SQLiteDatabase db;
    Monstr monstr;
    private ListView list_player;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monstr);
		intent=getIntent();
		arplayer=(arPlayer)intent.getSerializableExtra("arplayer");
		year=intent.getIntExtra("year", -1);
		list_player=(ListView)findViewById(R.id.listView1);
		
		myDbHelper = new DataBaseHelper(this);

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
		refplayer();
	}
	
	public void fight(View v){
		Click(v);
		for (int i=0;i<arplayer.ar.length;i++){
			arplayer.ar[i].fight(monstr);
		}
		refplayer();
	}
	
	public void goMain(View v){
		intent =new Intent();
		intent.putExtra("arplayer", arplayer);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	public void Click(View v){
		Cursor c;
		String selection ="level==?";
		String[] arg=new String[]{year+""};
		c = db.query("Monstr", null, selection, arg, null, null,null);
		
		if (c.moveToFirst()) monstr=toMonstr(c);
	}
	
	public Monstr toMonstr(Cursor c){
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
		int war=c.getInt(warColIndex);
		String name=c.getString(nameColIndex);
		int wwin=c.getInt(wwinColIndex);
		int wwood=c.getInt(wwoodColIndex);
		int wgold=c.getInt(wgoldColIndex);
		int wstone=c.getInt(wstoneColIndex);
		int lwin=c.getInt(lwinColIndex);
		int lwood=c.getInt(lwoodColIndex);
		int lgold=c.getInt(lgoldColIndex);
		int lstone=c.getInt(lstoneColIndex);
		
		return new Monstr(name,war, wwin, wwood, wgold, wstone, lwin, lwood, lgold, lstone);
	}
	
	
	public void refplayer(){
		PlayerAdapter adapter=new PlayerAdapter(this,arplayer.ar.clone());
		list_player.setAdapter(adapter);
		
	}
}
