package ru.fragmentcastle.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import ru.fragmentcastle.logic.Player;
import ru.fragmentcastle.logic.arPlayer;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BlService extends Service {

	private ArrayList<BluetoothSocket> sockets=new ArrayList<BluetoothSocket>();
	private ArrayList<BlinStream> blins=new ArrayList<BlinStream>();
	private BluetoothAdapter bluetooth=BluetoothAdapter.getDefaultAdapter();
	private String id="a277e372-1ff2-4320-84b3-3f67315455d3";
	public Finder find=null;
	private Client client=null;
	int next;


	public void onCreate() {
		super.onCreate();
		Log.d("LOG", "onCreate");
	}

	public void onDestroy() {
		super.onDestroy();
		Log.d("LOG", "onDestroy");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		int command=intent.getIntExtra("command", -1);
		int monstr;
		Intent mes;
		Intent rec;
		arPlayer arplayer=null;
		int[] sov=null;
		Log.d("LOG",command+"");
		switch (command){
		case 1:
			if (find==null){
				find=new Finder();
				find.start();
				rec = new Intent("ru.castleburg.bluetooth");
				rec.putExtra("status", "Сервер запущен");
				sendBroadcast(rec);
			}
			break;
		case 2:
			for (int i=0;i<sockets.size();i++){
				blins.add(new BlinStream(sockets.get(i)));
			}
			break;
		case 3:
			String s=null;
			s=intent.getStringExtra("message");
			if (client!=null && s!=null) client.write(s); 
			for (int i=0;i<blins.size();i++){
				if (client==null && s!=null) blins.get(i).write(s);
			}
			if (find!=null){
				mes = new Intent("ru.castleburg.bluetooth");
				mes.putExtra("text", s);
				sendBroadcast(mes);
			}
			break;
		case 4:

			BluetoothDevice device=null;
			device=intent.getParcelableExtra("device");
			if (device!=null && client==null){
				client=new Client(device);
			} 
			rec = new Intent("ru.castleburg.bluetooth");
			rec.putExtra("status", "Клиент подключен");
			sendBroadcast(rec);

			break;	
		case 5:
			Player player=null;
			player=(Player)intent.getSerializableExtra("player");
			if (client!=null && player!=null) client.write(player); 
			for (int i=0;i<blins.size();i++){
				if (client==null && player!=null) blins.get(i).write(player);
			}
			if (find!=null){
				mes = new Intent("ru.castleburg.bluetooth");
				mes.putExtra("player", player);
				sendBroadcast(mes);
			}
			break;
		case 6:
			arplayer=(arPlayer)intent.getSerializableExtra("arplayer");
			monstr=intent.getIntExtra("monstr", -1);
			for (int i=0;i<blins.size();i++){
				blins.get(i).start_game(i+1,monstr,arplayer);
			}
			mes = new Intent("ru.castleburg.bluetooth");
			mes.putExtra("arplayer", arplayer);
			mes.putExtra("monstr", monstr);
			mes.putExtra("id", 0);
			sendBroadcast(mes);
			break;
		case 7:
			find.interrupt();
			break;
		case 8:
			arplayer=(arPlayer)intent.getSerializableExtra("arplayer");
			sov=intent.getIntArrayExtra("field");
			next=intent.getIntExtra("next", -1);
			if (arplayer!=null && sov!=null){ 
				if (client==null){
					for (int i=0;i<blins.size();i++){
						blins.get(i).write_next(next);
						blins.get(i).write_game(arplayer, sov);
					}
				} else {
					client.write_next(next);
					client.write_game(arplayer, sov);
				}

				mes = new Intent("ru.castleburg.bluetooth");
				mes.putExtra("arplayer", arplayer);
				mes.putExtra("field", sov);
				mes.putExtra("next", next);
				sendBroadcast(mes);
			}
			break;
		}

		return super.onStartCommand(intent, flags, startId);
	}


	class Finder extends Thread{
		private final BluetoothServerSocket ServerSocket;
		public Finder(){
			BluetoothServerSocket tmp = null;
			try {
				tmp = bluetooth.listenUsingRfcommWithServiceRecord("Risen", UUID.fromString(id));
			} catch (IOException e) { }
			ServerSocket = tmp;
		}

		public void run(){
			while (!isInterrupted()){
				BluetoothSocket socket=null;
				try {
					socket=ServerSocket.accept(1000);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (socket!=null) {
					sockets.add(socket);
					blins.add(new BlinStream(sockets.get(sockets.size()-1)));
					Intent intent = new Intent("ru.castleburg.bluetooth");
					intent.putExtra("status", "Сервер запущен");
					intent.putExtra("kol", blins.size());
					sendBroadcast(intent);
				}
			}
		}//run()



	}

	class BlinStream {
		InputStream in;
		OutputStream out;
		Blinput blin;
		public BlinStream(BluetoothSocket socket){
			try {
				in=socket.getInputStream();
				out=socket.getOutputStream();
				blin=new Blinput(in);
				blin.start();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		public void write(String s){
			try {
				byte[] sbuf=s.getBytes();
				byte[] buf=new byte[1+sbuf.length];
				buf[0]=1;
				for (int i=0;i<sbuf.length;i++){
					buf[i+1]=sbuf[i];
				}
				out.write(buf);
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		public void write(Player player){
			try {
				out.write(player.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void write(arPlayer arplayer){
			try {
				out.write(arplayer.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		public void write_next(int mnext){
			byte[] buf=new byte[]{2,(byte)(next)};
			try {
				out.write(buf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void write_sov(int[] sov){
			byte[] buf=new byte[sov.length+1];
			buf[0]=102;
			for (int i=0;i<sov.length;i++){
				buf[i+1]=(byte) sov[i];
			}
			try {
				out.write(buf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		public void write_id(int id){
			byte[] buf=new byte[]{3,(byte)(id)};
			try {
				out.write(buf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void write_game(arPlayer ar,int[] sov){
			byte[] bar=ar.getBytes();
			byte[] buf=new byte[sov.length+bar.length];
			bar[0]=103;
			for (int i=0;i<bar.length;i++){
				buf[i]=bar[i];
			}
			for (int i=bar.length;i<buf.length;i++){
				buf[i]=(byte) sov[i-bar.length];
			}
			try {
				out.write(buf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		public void start_game(int id,int monstr,arPlayer ar){
			byte[] bar=ar.getBytes();
			byte[] buf=new byte[bar.length+3];
			buf[0]=3;
			buf[1]=(byte) id;
			buf[2]=(byte) monstr;
			for (int i=0;i<bar.length;i++){
				buf[i+3]=bar[i];
			}
			try {
				out.write(buf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



	}

	class Blinput extends Thread{
		InputStream in;

		public Blinput(InputStream in){
			this.in=in;
		}

		@Override
		public void run() {
			while (true){
				try {
					arPlayer arplayer;
					int bit,size;
					Intent intent;
					int[] sov;
					byte[] buf;
					bit=in.read();
					switch (bit){
					case 1: //Чтение строки
						size=in.available();
						buf=new byte[size];
						in.read(buf);
						String s=new String(buf);
						intent = new Intent("ru.castleburg.bluetooth");
						if (client==null && find!=null)
							for (int i=0;i<blins.size();i++){
								blins.get(i).write(s);
							}
						intent.putExtra("text", s);
						sendBroadcast(intent);
						break;
					case 2:
						int mnext=in.read();
						next=mnext;
						if (client==null && find!=null)
							for (int i=0;i<blins.size();i++){
								blins.get(i).write_next(mnext);
							}
						break;
					case 3:
						int id=in.read(),monstr=in.read();
						in.read();
						size=0;
						size=in.read();
						size=size*10+in.read();
						size=size*10+in.read();
						buf=new byte[size];
						in.read(buf);
						arplayer=new arPlayer(buf);
						intent = new Intent("ru.castleburg.bluetooth");
						intent.putExtra("arplayer", arplayer);
						intent.putExtra("monstr", monstr);
						intent.putExtra("id", id);
						sendBroadcast(intent);
						break;
					case 100:
						size=in.available();
						buf=new byte[size];
						in.read(buf);
						Player player=new Player(buf);
						intent = new Intent("ru.castleburg.bluetooth");
						if (client==null && find!=null)
							for (int i=0;i<blins.size();i++){
								blins.get(i).write(player);
							}
						intent.putExtra("player", player);
						sendBroadcast(intent);
						break;
					case 101:
						size=0;
						size=in.read();
						size=size*10+in.read();
						size=size*10+in.read();
						buf=new byte[size];
						in.read(buf);
						arplayer=new arPlayer(buf);
						intent = new Intent("ru.castleburg.bluetooth");
						if (client==null && find!=null)
							for (int i=0;i<blins.size();i++){
								blins.get(i).write(arplayer);
							}
						intent.putExtra("arplayer", arplayer);
						intent.putExtra("next", next);
						sendBroadcast(intent);
						break;
					case 102:
						buf=new byte[19];
						in.read(buf);
						sov=new int[buf.length];
						for (int i=0;i<buf.length;i++){
							sov[i]=buf[i];
						}
						if (client==null && find!=null)
							for (int i=0;i<blins.size();i++){
								blins.get(i).write_sov(sov);
							}
						intent = new Intent("ru.castleburg.bluetooth");
						intent.putExtra("field", sov);
						sendBroadcast(intent);
						break;
					case 103:
						size=0;
						size=in.read();
						size=size*10+in.read();
						size=size*10+in.read();
						buf=new byte[size];
						in.read(buf);
						arPlayer ar=new arPlayer(buf);
						if (client==null)
							for (int i=0;i<blins.size();i++){
								blins.get(i).write(ar);
							}
						buf=new byte[19];
						in.read(buf);
						sov=new int[buf.length];
						for (int i=0;i<buf.length;i++){
							sov[i]=buf[i];
						}
						if (client==null)
							for (int i=0;i<blins.size();i++){
								blins.get(i).write_sov(sov);
							}
						intent = new Intent("ru.castleburg.bluetooth");
						intent.putExtra("arplayer", ar);
						intent.putExtra("field", sov);
						intent.putExtra("next", next);
						sendBroadcast(intent);
						break;
					}

				} catch (IOException e) {
					break;
				}
			}//true

		}//run()
	}

	class Client {
		private final BluetoothSocket mmSocket;
		private OutputStream out;
		Blinput blin;
		InputStream in;

		public Client(BluetoothDevice device) {
			BluetoothSocket tmp = null;
			try {
				tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(id));
			} catch (IOException e) { }
			mmSocket = tmp;
			try {
				mmSocket.connect();
				out=mmSocket.getOutputStream();
				in=mmSocket.getInputStream();
				blin=new Blinput(in);
				blin.start();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}//контруктор


		public void write(String s){
			try {
				byte[] sbuf=s.getBytes();
				byte[] buf=new byte[1+sbuf.length];
				buf[0]=1;
				for (int i=0;i<sbuf.length;i++){
					buf[i+1]=sbuf[i];
				}
				out.write(buf);
				Log.d("LOg",s+"");
			} catch (IOException e) {

				e.printStackTrace();
			}
		}//write

		public void write(Player player){
			try {
				out.write(player.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void write(arPlayer arplayer){
			try {
				out.write(arplayer.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void write_next(int mnext){
			byte[] buf=new byte[]{2,(byte)(next)};
			try {
				out.write(buf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		public void write_sov(int[] sov){
			byte[] buf=new byte[sov.length+1];
			buf[0]=102;
			for (int i=0;i<sov.length;i++){
				buf[i+1]=(byte) sov[i];
			}
			try {
				out.write(buf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void write_game(arPlayer ar,int[] sov){
			byte[] bar=ar.getBytes();
			byte[] buf=new byte[sov.length+bar.length];
			bar[0]=103;
			for (int i=0;i<bar.length;i++){
				buf[i]=bar[i];
			}
			for (int i=bar.length;i<buf.length;i++){
				buf[i]=(byte) sov[i-bar.length];
			}
			try {
				out.write(buf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}//Client
}
