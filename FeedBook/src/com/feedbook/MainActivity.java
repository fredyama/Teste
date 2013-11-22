package com.feedbook;

import com.feedbook.MainActivity;
import com.feedbook.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;

public class MainActivity extends Activity /* implements OnClickListener */{

	private DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Button btnEntra = (Button) findViewById(R.id.btnEntra);
		final EditText edtxtPwd = (EditText) findViewById(R.id.edtxtPwd);
		final EditText edtxtUser = (EditText) findViewById(R.id.edtxtUser);
		final TextView edtxCadastrar = (TextView) findViewById(R.id.cadastrar);

		dbHelper = new DatabaseHelper(this);

		btnEntra.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String strLogin = edtxtUser.getText().toString();
				String strSenha = edtxtPwd.getText().toString();

				if (v.getId() == R.id.btnEntra) {

					if (strLogin.length() == 0) {
						Toast.makeText(MainActivity.this, "Falta o usuário",
								Toast.LENGTH_LONG).show();
					} else if (strSenha.length() == 0) {
						Toast.makeText(MainActivity.this, "Falta a senha",
								Toast.LENGTH_LONG).show();
					} else {
						SQLiteDatabase db = dbHelper.getReadableDatabase();
						Cursor cursor = db
								.rawQuery(
										"SELECT _id, email_usuario, apelido, "
												+ "senha FROM usuario WHERE email_usuario = ? AND senha = ? ",
										new String[] { strLogin, strSenha });

						if (cursor.moveToFirst()) {

							Toast.makeText(MainActivity.this,
									"Login com Sucesso!!!", Toast.LENGTH_SHORT)
									.show();

							Intent intent = new Intent(MainActivity.this, FeedActivity.class);
							intent.putExtra("idUsuario", cursor.getInt(0));
							startActivity(intent);
							finish();

						} else {
							
							Toast.makeText(
									MainActivity.this,
									"Erro de login, usuário ou senha inválidos",
									Toast.LENGTH_SHORT).show();
						}
						
						cursor.close();
						
					}
					
				}
			}
		});

		edtxCadastrar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this,
						CadastroUsuarioActivity.class));
			}

		});

		// final de oncreate

	}

	protected void onDestroy() {
		dbHelper.close();
		super.onDestroy();
	}

}// final main activity

