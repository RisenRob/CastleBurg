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

	//����������������� ������
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
	
	//����� � ��������
	public void fight(Monstr monstr){
		//����� ����� ���-�� ���� ������������ � ��� � ����� ������������ ������� �������� ��� �� ��� :)
		if (monstr.name=="�����" && build.po[0][3]==true) war++;
		if (monstr.name=="�������" && build.po[0][4]==true) war++;
		if (monstr.name=="������" && build.po[2][0]==true) war++;
		if (war>monstr.war) win(monstr);
		if (war<monstr.war) lose(monstr);
		if (build.po[2][3]==true && war==monstr.war) win(monstr); 
	}
	//����� �������
	private void win(Monstr monstr){
		gold+=monstr.wgold;
		win+=monstr.wwin;
		wood+=monstr.wwood;
		stone+=monstr.wstone;
		if (build.po[3][3]==true) win++;
	}
	
	//����� ��������
	private void lose(Monstr monstr){
		gold-=monstr.wgold;
		win-=monstr.wwin;
		wood-=monstr.wwood;
		stone-=monstr.wstone;
	}
	
	//���������� �� ����������� ����� �� �������
	public int compareTo(Object arg0) {
		Player two=(Player)arg0;
		if (this.tess.toStringSum()>two.tess.toStringSum()) return -1;
		if (this.tess.toStringSum()<two.tess.toStringSum()) return 1;
		return 0;
	}
	
	
	//������� ���������� ������
	public void count(){
		if (plus>0) tess.plus=true;
		tess.count();
	}
	
	//������� ����������
	public void del(int sum,int n){
		tess.del(sum, n);
		if (tess.plus==false && plus>0) {
			plus--;
			tess.plus=true;
		}
		count();
	}
	
	//������������� �������
	public void refresh(int n){
		tess.refresh(n);
		//������� if (build.po[1][0]==true && tess.toStringSum()<=7) tess.refresh(n);
	    //������ if (build.po[0][0]==true && tess.check() && n!=0) tess.reftess();
		if (build.po[1][1]==true) tess.market=true;
		if (build.po[2][1]==true && n!=0) {
			tess.addtess();
			win--;
		}
		
		count();
	}
	//������� ��������
		public void prepare(int phase){
			build.prepare(phase);
			if (phase==5 && build.po[0][1]==true) plus++;
			if ((phase==1 || phase==3 ||phase== 5) && build.po[3][1]==true) gold++;
			if ((phase==2 || phase==4 || phase==6)&& build.po[3][3]==true) win++;
		}
		//����� ���� ����� �����
		//public void Chasovna(){			
	//if (tess.toStringSum()<=7) ;
	//	}

	//������������
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

		
	//��������������
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