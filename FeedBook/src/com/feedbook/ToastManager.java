package com.feedbook;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToastManager {

	public static final int INFORMATION = 0;
	public static final int WARNING = 1;
	public static final int ERROR = 2;
	public static final int SUCCESS = 3;

	public static void show(Context context, String text, int toastType) {

		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewToast = layoutInflater.inflate(R.layout.toast, null);
		TextView txvMensagem = (TextView) viewToast.findViewById(R.id.mensagem);
		txvMensagem.setText(text);
		LinearLayout toastLayout = (LinearLayout) viewToast
				.findViewById(R.id.toast_layout_root);
		Drawable img;
		int bg;
		switch (toastType) {
		case WARNING:
			img = context.getResources().getDrawable(
					R.drawable.warning);
			bg = R.drawable.toast_background_yellow;			
			break;
		case ERROR:
			img = context.getResources().getDrawable(
					R.drawable.error_1);
			bg = R.drawable.toast_background_red;			
			break;
		case SUCCESS:
			img = context.getResources().getDrawable(
					R.drawable.pass);
			bg = R.drawable.toast_background_green;
			break;
		default:
			img = context.getResources().getDrawable(
					R.drawable.system_information);
			bg = R.drawable.toast_background_blue;			
			break;
		}
		
		txvMensagem.setCompoundDrawablesWithIntrinsicBounds(img, null, null,
				null);
		toastLayout.setBackgroundResource(bg);

		Toast toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setView(viewToast);
		toast.show();

	}
}
