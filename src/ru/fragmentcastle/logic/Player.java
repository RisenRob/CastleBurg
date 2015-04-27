package ru.fragmentcastle.logic;
import java.io.IOException;
import java.io.Serializable;

public class Player implements Serializable,Comparable<Object>{

	private static final long serialVersionUID = 1L;
	public int num;
	public int war;
	public int win;
	public int wood;
	public int gold;
	public int stone;
	public int plus;
	public boolean market;
	public Build build;
	public Tessera tess;

	//инизциализировние игрока
	public Player(int mwar,int mwin,int mwood,int mgold,int mstone,int mplus,int mnum){
		war=mwar;
		win=mwin;
		wood=mwood;
		gold=mgold;
		stone=mstone;
		num=mnum;
		plus=mplus;
		tess=new Tessera();
		build=new Build();
		count();
	}
	
	public byte[] getBytes(){
		int cur=8;
		byte[] bytes=new byte[8+90+tess.size()];
		bytes[0]=(byte) num;
		bytes[1]=(byte) war;
		bytes[2]=(byte) win;
		bytes[3]=(byte) wood;
		bytes[4]=(byte) gold;
		bytes[5]=(byte) stone;
		bytes[6]=(byte) plus;
		if (market) bytes[7]=1; else bytes[7]=0;
		for (int i=0;i<5;i++){
			for (int j=0;j<6;j++){
				if (build.po[i][j]) bytes[cur]=1; else bytes[cur]=0;
				cur++;
			}
		}
		for (int i=0;i<5;i++){
			for (int j=0;j<6;j++){
				if (build.pos[i][j]) bytes[cur]=1; else bytes[cur]=0;
				cur++;
			}
		}
		for (int i=0;i<5;i++){
			for (int j=0;j<6;j++){
				if (build.sosbut[i][j]) bytes[cur]=1; else bytes[cur]=0;
				cur++;
			}
		}
		for (int i=0;i<tess.size();i++){
			bytes[cur]=(byte)(tess.get(i)-'0');
			cur++;
		}
		
		return bytes;
	}
	
	public Player(byte[] bytes){
		tess=new Tessera();
		build=new Build();
		int cur=8;
		num=bytes[0];
		war=bytes[1];
		win=bytes[2];
		wood=bytes[3];
		gold=bytes[4];
		stone=bytes[5];
		plus=bytes[6];
		if (bytes[7]==1) market=true; else market=false;
		for (int i=0;i<5;i++){
			for (int j=0;j<6;j++){
				if (bytes[cur]==1) build.po[i][j]=true; else build.po[i][j]=false;
				cur++;
			}
		}
		for (int i=0;i<5;i++){
			for (int j=0;j<6;j++){
				if (bytes[cur]==1) build.pos[i][j]=true; else build.pos[i][j]=false;
				cur++;
			}
		}
		for (int i=0;i<5;i++){
			for (int j=0;j<6;j++){
				if (bytes[cur]==1) build.sosbut[i][j]=true; else build.sosbut[i][j]=false;
				cur++;
			}
		}
		tess.clear();
		for (int i=cur;i<bytes.length;i++){
			tess.add((char)(bytes[i]+'0'));
		}
		count();
	}
	
	//борьа с монстром
	public void fight(Monstr monstr){
		//здесь будет что-то типо пригтовиться к бою и будет накидываться эффеткы строений как то так :)
		if (monstr.name=="Зомби" && build.po[0][3]==true) war++;
		if (monstr.name=="Гоблины" && build.po[0][4]==true) war++;
		if (monstr.name=="Демоны" && build.po[2][0]==true) war++;
		if (war>monstr.war) win(monstr);
		if (war<monstr.war) lose(monstr);
		if (build.po[2][3]==true && war==monstr.war) win(monstr); 
	}
	//игрок выиграл
	private void win(Monstr monstr){
		gold+=monstr.wgold;
		win+=monstr.wwin;
		wood+=monstr.wwood;
		stone+=monstr.wstone;
		if (build.po[3][3]==true) win++;
	}
	
	//игрок проиграл
	private void lose(Monstr monstr){
		gold-=monstr.lgold;
		win-=monstr.lwin;
		wood-=monstr.lwood;
		stone-=monstr.lstone;
	}
	
	//сортировка по возрастанию чисел на кубиках
	public int compareTo(Object arg0) {
		Player two=(Player)arg0;
		if (this.tess.toStringSum()>two.tess.toStringSum()) return -1;
		if (this.tess.toStringSum()<two.tess.toStringSum()) return 1;
		return 0;
	}
	
	
	//подсчёт комбинаций игрока
	public void count(){
		if (plus>0) tess.plus=true;
		tess.count();
	}
	
	//удалние комбинации
	public void del(int sum,int n){
		tess.del(sum, n);
		if (tess.plus==false && plus>0) {
			plus--;
			tess.plus=true;
		}
		count();
	}
	
	//перекидывание кубиков
	public void refresh(int n){
		tess.refresh(n);
		//Часовня if (build.po[1][0]==true && tess.toStringSum()<=7) tess.refresh(n);
	    //Статуя if (build.po[0][0]==true && tess.check() && n!=0) tess.reftess();
		if (build.po[1][1]==true) tess.market=true;
		if (build.po[2][1]==true && n!=0) {
			tess.addtess();
			win--;
		}
		
		count();
	}
	//эффекты строений
		public void prepare(int phase,int year){
			build.prepare(phase);
			if (phase==5 && build.po[0][1]==true) plus++;
			if ((phase==1 || phase==3 ||phase== 5) && build.po[3][1]==true) gold++;
			if ((phase==2 || phase==4 || phase==6)&& build.po[3][3]==true) win++;
			if (phase==7 && year==5 && build.po[3][0]==true) win+=(gold+wood+stone)/2;
			if (phase==1) {
				war=0;
				war=build.kwar();
			}
			
			
		}
		//Пусть пока будет здесь
		//public void Chasovna(){			
	//if (tess.toStringSum()<=7) ;
	//	}

	//сериализация
	private void writeObject(java.io.ObjectOutputStream out)
			throws IOException {
		out.writeInt(win);
		out.writeInt(war);
		out.writeInt(wood);
		out.writeInt(gold);
		out.writeInt(stone);
		out.writeInt(num);
		out.writeInt(plus);
		out.writeUTF(tess.toString());
		out.writeObject(build);
	}

		
	//десериализация
	private void readObject(java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		win=in.readInt();
		war=in.readInt();
		wood=in.readInt();
		gold=in.readInt();
		stone=in.readInt();
		num=in.readInt();
		plus=in.readInt();
		tess=new Tessera(in.readUTF());
		build=(Build)in.readObject();
		count();
	}
	
	
}
