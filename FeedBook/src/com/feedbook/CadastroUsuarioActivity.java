package com.feedbook;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CadastroUsuarioActivity extends Activity{
	
	private DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_usuario);
		dbHelper = new DatabaseHelper(this);
	}

	public void continuar(View view){
		EditText edtEmail = (EditText) findViewById(R.id.emailUsuario);
		EditText edtApelido = (EditText) findViewById(R.id.apelidoUsuario);
		String email = edtEmail.getText().toString();
		String apelido = edtApelido.getText().toString();
        
		if (email.length() == 0){
			ToastManager.show(this, getString(R.string.msg_digite_email_valido), ToastManager.ERROR);
		} else { 
		
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor cursor = db
					.rawQuery(
							"SELECT _id, email_usuario, apelido, "
									+ "senha FROM usuario WHERE email_usuario = ? ",
							new String[] {email});

			if (cursor.moveToFirst()) {
				ToastManager.show(this, getString(R.string.msg_existe_usuario_cadastrado_email), ToastManager.ERROR);
			} else if (apelido.length() == 0){
				ToastManager.show(this, getString(R.string.msg_digite_apelido), ToastManager.ERROR);
			} else {
				ToastManager.show(this, getString(R.string.msg_insira_codigo_email), ToastManager.INFORMATION);
				Intent intent = new Intent(this, ConfirmacaoCadastroActivity.class); 
				intent.putExtra("email", email);
				intent.putExtra("apelido", apelido);
				startActivity(intent);
			}
		}
	}

	protected void onDestroy() {
		dbHelper.close();
		super.onDestroy();
	}
	
}
