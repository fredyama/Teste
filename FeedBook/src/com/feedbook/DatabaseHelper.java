package com.feedbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String BANCO_DADOS = "feedBook";
	private static final int VERSAO = 1;

	public DatabaseHelper(Context context) {
		super(context, BANCO_DADOS, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE usuario(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				" email_usuario TEXT NOT NULL, apelido TEXT NOT NULL, senha TEXT NOT NULL);");
		
		
		
		
	
		db.execSQL("CREATE TABLE feed(_id INTEGER PRIMARY KEY AUTOINCREMENT, titulo_feed TEXT NOT NULL, " +
				" detalhe_feed TEXT, id_grupo INTEGER NOT NULL);");
				
		db.execSQL("CREATE TABLE feed_usuario (id_feed INTEGER NOT NULL, id_usuario INTEGER NOT NULL, status TEXT NOT NULL, " +
				" PRIMARY KEY(id_feed, id_usuario),FOREIGN KEY (id_feed) REFERENCES feed(_id),FOREIGN KEY (id_usuario) REFERENCES usuario(_id));");
		
		
		
		db.execSQL("CREATE TABLE grupo (_id INTEGER PRIMARY KEY AUTOINCREMENT, nome_grupo TEXT NOT NULL, " +
				" descricao_grupo TEXT, chave_grupo TEXT NOT NULL);");
		
		db.execSQL("CREATE TABLE grupo_usuario(id_grupo INTEGER NOT NULL, id_usuario INTEGER NOT NULL, status TEXT NOT NULL," +
				" PRIMARY KEY(id_grupo, id_usuario),FOREIGN KEY (id_grupo) REFERENCES grupo(_id),FOREIGN KEY (id_usuario) REFERENCES usuario(_id));");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

		
	}

}
