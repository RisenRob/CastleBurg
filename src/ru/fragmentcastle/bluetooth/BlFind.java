package ru.fragmentcastle.bluetooth;

import ru.fragmentcastle.R;
import ru.fragmentcastle.logic.Monstr;
import ru.fragmentcastle.logic.arPlayer;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class BlFind extends Activity {

	BluetoothAdapter bluetooth;
	Button btn1,btn2,btn3;
	TextView status;
	ListView list;
	boolean game=false;
	BLDAdapter bldadapter;
	int kol;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find);
		status=(TextView)findViewById(R.id.status);
		list=(ListView)findViewById(R.id.listView1);
		btn1=(Button)findViewById(R.id.button1);
		btn2=(Button)findViewById(R.id.button2);
		btn3=(Button)findViewById(R.id.button3);
		bldadapter= new BLDAdapter(this);
		list.setAdapter(bldadapter);
		list.setOnItemClickListener(list_click);
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(receiver, filter);
		bluetooth=BluetoothAdapter.getDefaultAdapter();
		IntentFilter blFilt = new IntentFilter("ru.castleburg.bluetooth");
		registerReceiver(blinreceiver, blFilt);
	}

	protected void onDestroy(){
		Intent intent = new Intent(BlFind.this, BlService.class);
		if (game==false) stopService(intent);
		unregisterReceiver(receiver);
		unregisterReceiver(blinreceiver);
		super.onDestroy();

	}

	public void start_server(View v){
		if (bluetooth.isEnabled()){
			Intent intent = new Intent(this, BlService.class).putExtra("command",1);
			startService(intent);
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
			btn3.setEnabled(true);
		}else {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivity(enableBtIntent);
		}
	}


	public void start_client(View v){
		if (bluetooth.isEnabled()){
			bluetooth.startDiscovery();
		} else {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivity(enableBtIntent);
		}
	}

	private final BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (!bldadapter.devices.contains(device)) bldadapter.devices.add(device);
				list.requestLayout();
			}
		}
	};
	
		
	private final BroadcastReceiver blinreceiver=new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			kol=intent.getIntExtra("kol",-1);
			String sstatus=intent.getStringExtra("status");
			if (status!=null) status.setText(sstatus);
			if (kol!=-1) status.setText(sstatus+" "+kol);
			arPlayer arplayer=(arPlayer)intent.getSerializableExtra("arplayer");
			int id=intent.getIntExtra("id", -1),monstr=intent.getIntExtra("monstr", -1);
			if (arplayer!=null && id!=-1 && monstr!=-1){
				Intent start = new Intent(BlFind.this,BLGameActivity.class);
				start.putExtra("arplayer", arplayer);
				start.putExtra("monstr",monstr);
				start.putExtra("id",id);
				startActivity(start);
				game=true;
			}
		}

	};

	AdapterView.OnItemClickListener list_click=new AdapterView.OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
			Intent intent = new Intent(BlFind.this, BlService.class);
			intent.putExtra("command",4);
			intent.putExtra("device", bldadapter.devices.get(position));
			startService(intent);
		}
	};

	public void start_game(View v){
		arPlayer arplayer=new arPlayer(kol+1);
		Monstr monstr=new Monstr(1,this);
		Intent intent = new Intent(this, BlService.class);
		intent.putExtra("command",6);
		intent.putExtra("arplayer",arplayer);
		intent.putExtra("monstr",monstr.id);
		startService(intent);
	}

}
