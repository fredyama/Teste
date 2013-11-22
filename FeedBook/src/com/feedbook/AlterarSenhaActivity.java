package com.feedbook;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AlterarSenhaActivity extends Activity {

	private DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alterar_senha);
		dbHelper = new DatabaseHelper(this);
	}

	public void salvarUsuario(View view) {
		EditText edtSenha = (EditText) findViewById(R.id.senhaUsuario);
		EditText edtConfima = (EditText) findViewById(R.id.confirmarSenha);
		String senha = edtSenha.getText().toString();
		String confirma = edtConfima.getText().toString();

		if (senha.length() == 0) {
			ToastManager.show(this, getString(R.string.msg_digite_senha), ToastManager.ERROR);
		} else if (senha.length() < 6) {
			ToastManager.show(this, getString(R.string.msg_senha_seis_digitos), ToastManager.WARNING);
		} else if (confirma.length() == 0) {
			ToastManager.show(this, getString(R.string.msg_digite_confirmacao), ToastManager.ERROR);
		} else if (!confirma.equals(senha)) {
			ToastManager.show(this, getString(R.string.msg_confirmacao_incorreta), ToastManager.ERROR);
		} else {
			
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("email_usuario", getIntent().getStringExtra("email"));
			values.put("apelido", getIntent().getStringExtra("apelido"));
			values.put("senha", senha);
			
			long resultado = db.insert("usuario", null, values);
			
			if (resultado != -1) {
				ToastManager.show(this, getString(R.string.msg_usuario_cadastrado_sucesso), ToastManager.SUCCESS);
				startActivity(new Intent(this, FeedActivity.class));
			}
		}		
	}
	
	protected void onDestroy() {
		dbHelper.close();
		super.onDestroy();
	}

}
