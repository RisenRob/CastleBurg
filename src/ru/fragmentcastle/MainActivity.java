package ru.fragmentcastle;

import ru.fragmentcastle.bluetooth.BlFind;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	public static boolean sta=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void click(View v){
		sta=false;
		StartGame start=new StartGame();
		start.show(getFragmentManager(), "start");
	}

	public void bluetooth(View v){
		sta=false;
		Intent intent=new Intent(this,BlFind.class);
		startActivity(intent);
	}

	public void click1(View v){
		sta=true;
		StartGame start=new StartGame();
		start.show(getFragmentManager(), "start");
	}
}
