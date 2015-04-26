package ru.fragmentcastle.bluetooth;

import ru.fragmentcastle.R;
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
import android.widget.ListView;
import android.widget.TextView;

public class BlFind extends Activity {

	BluetoothAdapter bluetooth;
	TextView status;
	ListView list;
	boolean game=false;
	BLDAdapter bldadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find);
		status=(TextView)findViewById(R.id.status);
		list=(ListView)findViewById(R.id.listView1);
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
			String kol=intent.getStringExtra("kol"),sstatus=intent.getStringExtra("status");
			if (status!=null) status.setText(sstatus);
			if (kol!=null) status.setText(sstatus+" "+kol);
			arPlayer arplayer=(arPlayer)intent.getSerializableExtra("arplayer");
			if (arplayer!=null && game==false){
				Intent start = new Intent(BlFind.this,BLGameActivity.class);
				start.putExtra("arplayer", arplayer);
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
		arPlayer arplayer=new arPlayer(3);
		Intent intent = new Intent(this, BlService.class);
		intent.putExtra("command",6);
		intent.putExtra("arplayer",arplayer);
		startService(intent);
	}

}