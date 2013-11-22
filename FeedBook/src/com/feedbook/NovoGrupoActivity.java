package com.feedbook;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NovoGrupoActivity extends Activity {
	
	private DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.novo_grupo);
		dbHelper = new DatabaseHelper(this);
	}
	
	public void salvarGrupo(View view){
		EditText edtNome = (EditText) findViewById(R.id.nomeGrupo);
		EditText edtDescr = (EditText) findViewById(R.id.descricaoGrupo);
		String nome = edtNome.getText().toString();
		String descr = edtDescr.getText().toString();
		
		if(nome.length() == 0){
			ToastManager.show(this, getString(R.string.msg_digite_nome_grupo), ToastManager.ERROR);
		} else {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("nome_grupo", nome);
			values.put("descricao_grupo", descr);
			values.put("chave_grupo", 1234);
			
			long resultGrupo = db.insert("grupo", null, values);
			
			if (resultGrupo != -1) {
				
				Cursor cursor = db.rawQuery("SELECT LAST_INSERT_ROWID() AS ID", null);
				
				cursor.moveToFirst();
				
				long idGrupo = cursor.getLong(0);
				
				values = new ContentValues();
				values.put("id_grupo", idGrupo); 
				values.put("id_usuario", getIntent().getIntExtra("idUsuario", 0)); 
				values.put("status", ConstantesUtil.STATUS_MODERADOR);
				
				long resultGrupoUsu = db.insert("grupo_usuario", null, values);
				
				if (resultGrupoUsu != -1) {
					ToastManager.show(this, getString(R.string.msg_grupo_cadastrado_sucesso_chave) + "1234", ToastManager.SUCCESS);
					startActivity(new Intent(this, GrupoActivity.class));
				}
				
				cursor.close();
				
			}
		}
		
	}
	
	protected void onDestroy() {
		dbHelper.close();
		super.onDestroy();
	}
	
}
