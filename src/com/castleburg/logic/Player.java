package com.castleburg.logic;
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
	}
	
	//борьа с монстром
	public void fight(Monstr monstr){
		//здесь будет что-то типо пригтовиться к бою и будет накидываться эффеткы строений как то так :)
		if (war>=monstr.war) win(monstr);
		if (war<monstr.war) lose(monstr);
	}
	
	//игрок выиграл
	private void win(Monstr monstr){
		gold+=monstr.wgold;
		win+=monstr.wwin;
		wood+=monstr.wwood;
		stone+=monstr.wstone;
	}
	
	//игрок проиграл
	private void lose(Monstr monstr){
		gold-=monstr.wgold;
		win-=monstr.wwin;
		wood-=monstr.wwood;
		stone-=monstr.wstone;
	}

	//сериализация
	private void writeObject(java.io.ObjectOutputStream out)
			throws IOException {
		out.writeInt(win);
		out.writeInt(war);
		out.writeInt(wood);
		out.writeInt(gold);
		out.writeInt(stone);
		out.writeInt(num);
		out.writeUTF(tess.toString());
		out.writeObject(build);
	}

	
	//эффекты строений
	public void prepare(int phase){
		build.prepare(phase);
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
		tess=new Tessera(in.readUTF());
		build=(Build)in.readObject();
	}
	
	//сортировка по возрастанию чисел на кубиках
	public int compareTo(Object arg0) {
		Player two=(Player)arg0;
		if (this.tess.toStringSum()>two.tess.toStringSum()) return -1;
		if (this.tess.toStringSum()<two.tess.toStringSum()) return 1;
		return 0;
	}
	
}
