package com.imagenescopadas;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;

public class VerImagenes extends Activity {

	HttpResponse response;
	String resultado;
	private AdView adView;
	String[] Imagenes;
	Context context;
	int loader = R.drawable.loader;
	int CantImg = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ver_imagenes);

	    context = getApplicationContext();
		
		// Cargamos la publicidad de AdMob
	    AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
	    
	    //Cargamos listado de url
	    new MyAsyncTask().execute("");
	}

	//Boton ir atras
	public void IrAtras(View view){
		CantImg--;
		if(CantImg<0) CantImg=0; else LoadImg();
	}

	//Boton ir adelante
	public void IrAdelante(View view){
		CantImg++;
		if(CantImg>(Imagenes.length-1)) CantImg--; else LoadImg();
	}	

	//Mostrar imagen en pantalla
	public void LoadImg(){
		Log.e("CantImg:",""+CantImg);
		ImageView image = (ImageView) findViewById(R.id.imageView1);
        String image_url = Imagenes[CantImg];
         
        ImageLoader imgLoader = new ImageLoader(context);    	         
        imgLoader.DisplayImage(image_url, loader, image);
        
        TextView txt = (TextView) findViewById(R.id.textView1);
        txt.setText((CantImg+1) + " de " + Imagenes.length);
        
	}
	
	//Cargar listado de imagenes
	private class MyAsyncTask extends AsyncTask<String, Integer, Double>{
		@Override
		protected Double doInBackground(String... params) {
			// TODO Auto-generated method stub
			postData();
			return null;
		}
 
		protected void onPostExecute(Double result){
			//Toast.makeText(getApplicationContext(), "Cargando...", Toast.LENGTH_LONG).show();
			Log.e("Termino:","1");
		}
		
		protected void onProgressUpdate(Integer... progress){
			//Progreso
		}
 
		public void postData() {
			// Creamos un nuevo cliente htttp y leemos nuestra url
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httppost = new HttpGet("http://192.168.0.100/testandroid.php");

			try {
				// Ejecutamos la accion y obtenemos el resultado
				response = httpclient.execute(httppost);
				HttpEntity httpEntity = response.getEntity();
                resultado = EntityUtils.toString(httpEntity);
        	    Log.e("Resultado: ",""+resultado);
        	    
        	    Imagenes = resultado.split("#-#");
    			Log.e("Imagen:",""+ Imagenes[0]);
    			
    			runOnUiThread(new Runnable() {
    			     @Override
    			     public void run() {
    						ImageView image = (ImageView) findViewById(R.id.imageView1);
    				        String image_url = Imagenes[0];
    				         
    				        ImageLoader imgLoader = new ImageLoader(context);    	         
    				        imgLoader.DisplayImage(image_url, loader, image);
    				        
    				        TextView txt = (TextView) findViewById(R.id.textView1);
    				        txt.setText((CantImg+1) + " de " + Imagenes.length);
    			     }
    			});
			} catch (ClientProtocolException e) {
				// 	TODO Auto-generated catch block
			} catch (IOException e) {
				// 	TODO Auto-generated catch block
			}
		}
	}
	
	@Override
	  public void onResume() {
	    super.onResume();
	    if (adView != null) {
	      adView.resume();
	    }
	  }

	  @Override
	  public void onPause() {
	    if (adView != null) {
	      adView.pause();
	    }
	    super.onPause();
	  }

	  /** Called before the activity is destroyed. */
	  @Override
	  public void onDestroy() {
	    // Destroy the AdView.
	    if (adView != null) {
	      adView.destroy();
	    }
	    super.onDestroy();
	  }
	
}
