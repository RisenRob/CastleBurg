package com.castleburg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.castleburg.logic.Build;
import com.castleburg.logic.Player;

public class SovChose extends Activity {
	
	private ListView list;
	private Intent intent;
	private Player player;
	private int cur_count;
	private Build build;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sov_chose);
		list=(ListView)findViewById(R.id.listView1);
		intent=getIntent();
		player=(Player)intent.getSerializableExtra("player");
		cur_count=intent.getIntExtra("count", -1);
		TessAdapter adapter=new TessAdapter(this,player.tess,cur_count);
		list.setAdapter(adapter);
		list.setOnItemClickListener(click);
		}

	OnItemClickListener click=new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			intent=new Intent();
			player.tess.del(cur_count, position);
			switch (cur_count){
			case 1 :
				player.win++;
				break;
			case 2:
				player.gold++;
				break;
			case 3:
				player.wood++;
				break;
			case 4:
				//Дерево или золото
				player.wood++;
				player.gold++;
				break;
			case 5:
				player.war++;
				if (build.po[1][3]==true) player.war++;
				break;
			case 6:
				player.wood--;
				player.gold++;
				player.stone++;
				//Вот когда расскажешь как варианты выбирать тогда и сделаю а так то
				//res[1]--;res[2]++;res[3]++;
				//||
				//res[2]--;res[1]++;res[3]++;
				//||
				//res[3]--;res[2]++;res[1]++;
				break;
			case 7:
				//Ресурс на ВЫБОР
				//plus2++;
				break;
			case 8:
				player.gold+=2;
				break;
			case 9:
				//Выбор
				player.wood++;
				player.gold++;
			break;
				//||
				//res[1]++;res[3]++;
			case 10:
				player.war+=2;
				
				if (build.po[1][3]==true) player.war++;
				break;
				//Ещё монстра подглядеть
			case 11:
				//Выбор
				player.gold++;
				player.stone++;
				break;
					//||
					//res[1]++;res[3]++;
			case 12:
				//2 Ресурса на ВЫБОР
				//plus2++;
				break;
			case 13:
				player.stone+=3;
				break;
			case 14:
				player.win--;
				break;
				//3 Ресурса на ВЫБОР
			case 15:
				player.wood++;
				player.gold++;
				player.stone++;
				break;
			case 16:
				player.gold+=4;
				break;
			case 17:
				player.win+=3;
				break;
				//2 Ресурса на ВЫБОР
				//подглядеть монстра
			case 18:
				player.wood++;
				player.gold++;
				player.stone++;
				player.war++;
				if (build.po[1][3]==true) player.war++;
				break;
			}
			intent.putExtra("player", player);
			setResult(RESULT_OK, intent);
			finish();
		}
		
	};
	
		
}
