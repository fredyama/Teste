package com.feedbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ConfirmacaoCadastroActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirmacao_cadastro);
	}
	
	public void continuar(View view){
		EditText edtCodigo = (EditText) findViewById(R.id.codigoEmail);
		String codigo = edtCodigo.getText().toString();
		if (codigo.length() == 0){
			ToastManager.show(this, getString(R.string.msg_insira_codigo_email), ToastManager.ERROR);
		} else if (!codigo.equals("FEED")){
			ToastManager.show(this, getString(R.string.msg_codigo_incorreto), ToastManager.ERROR);
		} else {
			Intent intent = new Intent(this, AlterarSenhaActivity.class);
			intent.putExtra("email", getIntent().getStringExtra("email"));
			intent.putExtra("apelido", getIntent().getStringExtra("apelido"));
			startActivity(intent);			
		}
	}
}
