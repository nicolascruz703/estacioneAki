package estacioneAki.servico;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import estacioneAki.util.Retorno;
import android.os.AsyncTask;
import android.util.Log;

public class getAutorizacaoLogin extends AsyncTask<String, Void , String>{        
	private String MsgParaTela = null;    	 	
	private Retorno mensagem = null;
	
	@Override 
    protected String doInBackground(String... UrlLoginMotorista) { 

 		try {
 			URL myURL = new URL(UrlLoginMotorista[0]); 	 				 		 	
			myURL = new URL(UrlLoginMotorista[0]);
			URLConnection ucon = myURL.openConnection();   	 			  	 			 
 			InputStream is = ucon.getInputStream();
			Serializer serializer = new Persister();
			
			mensagem = serializer.read(Retorno.class, is);
			Log.v("Clase getAutorizacaoLogin", "msg: "+mensagem.getMensagem());
			MsgParaTela = mensagem.getMensagem(); 			
		}
		catch (Exception e){
			e.printStackTrace();
		    Log.v("Clase getAutorizacaoLogin", "exceptionlogin "+mensagem.getMensagem());
		} 			
	 
        return MsgParaTela;   
     } 
     
     protected void onPostExecute(String msgParaTela) {     	 
    	 Log.v("Clase getAutorizacaoLogin-posexecute", mensagem.getMensagem());     	 
     } 
} 
