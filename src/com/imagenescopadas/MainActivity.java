package com.imagenescopadas;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	//Funcion encargada de cargar el layout para ver las imagenes!
	public void CargarImagenes(View view){
		Intent intent = new Intent(this, VerImagenes.class);
		startActivity(intent);
	}
}
