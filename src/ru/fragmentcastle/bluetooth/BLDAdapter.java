package ru.fragmentcastle.bluetooth;

import java.util.ArrayList;

import ru.fragmentcastle.R;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BLDAdapter extends BaseAdapter {
	public ArrayList<BluetoothDevice> devices;
	Context ctx;
	LayoutInflater inflater;
	
	public BLDAdapter(Context mctx){
		ctx=mctx;
		devices=new ArrayList<BluetoothDevice>();
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return devices.size();
	}

	@Override
	public BluetoothDevice getItem(int position) {
		return devices.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.bld, parent, false);
			((TextView)view.findViewById(R.id.mes)).setText(devices.get(position).getName());;
		}
		return view;
	}

}
