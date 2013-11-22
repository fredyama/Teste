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
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class GrupoActivity extends Activity {

	private List<Map<String, Object>> grupoList;

	private ListView listView;

	private DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.lista_grupo);
		
		dbHelper = new DatabaseHelper(this);
		
		listView = (ListView) findViewById(R.id.listaGrupo);
		
		String[] de = { "nomeGrupo" };
		
		int[] para = { R.id.nomeGrupo };
		
		SimpleAdapter adapter = new SimpleAdapter(this, listarGrupos(),	R.layout.grupo, de, para);
		
		listView.setAdapter(adapter);
		
		registerForContextMenu(listView);
		
	}

	public List<Map<String, Object>> listarGrupos() {
		grupoList = new ArrayList<Map<String,Object>>();
		Map<String,Object> grupo;
		int id = getIntent().getIntExtra("idUsuario", 0);
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		
		Cursor cursor = db.rawQuery("SELECT _id, nome_grupo, descricao_grupo, " +
		"chave_grupo, status FROM grupo g LEFT OUTER JOIN grupo_usuario gu ON g._id = gu.id_grupo WHERE gu.id_usuario = ? ",
		new String[]{String.valueOf(id)});
		
		
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {
			grupo = new HashMap<String, Object>();
			grupo.put("nomeGrupo", cursor.getString(1));
			grupoList.add(grupo);
			cursor.moveToNext();
		}
		cursor.close();
		
		return grupoList;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_grupo, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.novoFeed) {
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();

			startActivity(new Intent(this, NovoFeedActivity.class));
			return true;
		}
		if (item.getItemId() == R.id.feedsGrupo) {
			Toast.makeText(this, "Feeds do Grupo", Toast.LENGTH_SHORT).show();
			return true;
		}
		if (item.getItemId() == R.id.excluirGrupo) {
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			grupoList.remove(info.position);
			listView.invalidateViews();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	public void novoGrupo(View view) {
		Intent intent = new Intent(this, NovoGrupoActivity.class);
		intent.putExtra("idUsuario", getIntent().getIntExtra("idUsuario", 0));
		startActivity(intent);
	}
	
	protected void onDestroy() {
		dbHelper.close();
		super.onDestroy();
	}

}
