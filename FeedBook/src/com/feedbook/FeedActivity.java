package com.feedbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
//import android.view.View.OnClickListener;
import android.view.WindowManager;
//import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;



public class FeedActivity extends Activity  {
	
	
	
	
	
	private List<Map<String,Object>> feedList;
	
	private ListView listView;
	
	private DatabaseHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		
		setContentView(R.layout.lista_feed);
		
		
		
		
		dbHelper = new DatabaseHelper(this);
		
		listView = (ListView) findViewById(R.id.listaFeed);
		
		String[] de = {/*"nomeGrupo",*/"titulo_Feed","detalhe_feed"};
		
		int[] para = {/*R.id.nomeGrupo,*/ R.id.tituloFeed, R.id.detalheFeed};
		
		SimpleAdapter adapter = new SimpleAdapter(this, listarFeeds(), R.layout.feed, de, para);
		
		listView.setAdapter(adapter);
		
		registerForContextMenu(listView);
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	public List<Map<String, Object>> listarFeeds() {
		feedList = new ArrayList<Map<String,Object>>();
		Map<String,Object> feed;
		int id = getIntent().getIntExtra("id_usuario", 0);
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT _id, titulo_feed, " +
		"detalhe_feed ,status FROM feed g LEFT OUTER JOIN feed_usuario gu ON g._id = gu.id_feed WHERE gu.id_usuario = ? ",
		new String[]{String.valueOf(id)});
	
		
		
		
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {
			feed = new HashMap<String, Object>();
			feed.put("titulo_feed", cursor.getString(0));
			feed.put("descricao_feed", cursor.getString(1));
			
			
			feedList.add(feed);
			cursor.moveToNext();
		}
		cursor.close();
		
		return feedList;
	}
		  

			
	protected void onDestroy() {
		dbHelper.close();
		super.onDestroy();
	}


	
	public void listarGrupos(View view){
		Intent intent = new Intent(this, GrupoActivity.class);
		intent.putExtra("idUsuario", getIntent().getIntExtra("idUsuario", 0));
		startActivity(intent);
	}

	
	public void onItemClick(android.widget.AdapterView<?> adapter, View view, int position, long id) {
		Map<String, Object> map = feedList.get(position);
		String destino = (String) map.get("titulo_feed");
		String mensagem = "Titulo: "+ destino;
		Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
	

			
	
}
}
