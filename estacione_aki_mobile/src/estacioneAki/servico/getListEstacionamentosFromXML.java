package estacioneAki.servico;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import estacioneAki.util.EstacionamentoList;
import estacioneAki.util.Estacionamento;
import android.os.AsyncTask;
import android.util.Log;

public class getListEstacionamentosFromXML extends AsyncTask<String, Void , EstacionamentoList>{        
	private String Id = null;    	 	
	private EstacionamentoList ListaDeEstacionamentos = null; 
	
	@Override 
    protected EstacionamentoList doInBackground(String... UrlListaEstacionamentos) { 

 		try {     	 
 			URL myURL = new URL(UrlListaEstacionamentos[0]); 	 			
	 		URLConnection ucon = myURL.openConnection();   	 			 
 			 
 			InputStream is = ucon.getInputStream(); 
 			Serializer serializer = new Persister(); 
 			ListaDeEstacionamentos = serializer.read(EstacionamentoList.class, is);  			
 		    
 			//Teste
	        Id = ListaDeEstacionamentos.estaciomentoList.get(0).toString();
 			Log.v("Clase getListEstacionamentosFromXML", "Ultimo Id d estacionam "+Id);
 		 } catch (Exception e) { 
 			e.printStackTrace(); 
 		 } 
          
         return ListaDeEstacionamentos;   
     } 
     
     protected void onPostExecute(EstacionamentoList listaEst) {     	 
    	 Log.v("Clase getListEstacionamentosFromXML-posexecute", "ok"); 
     } 
} 
