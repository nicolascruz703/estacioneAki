package estacioneAki.servico;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import estacioneAki.util.ParquimetroList;
import android.os.AsyncTask;
import android.util.Log;

public class getListParquimetrosFromXML extends AsyncTask<String, Void , ParquimetroList>{        
	private String Id = null;    	 	
	private ParquimetroList parquimetroList = null; 
	
	@Override 
    protected ParquimetroList doInBackground(String... UrlListaEstacionamentos) { 

 		try {     	 
 			URL myURL = new URL(UrlListaEstacionamentos[0]); 	 			
	 		URLConnection ucon = myURL.openConnection();   	 			 
 			 
 			InputStream is = ucon.getInputStream(); 
 			Serializer serializer = new Persister(); 
 			parquimetroList = serializer.read(ParquimetroList.class, is);  			
 		    
 			//Teste
	        Id = parquimetroList.parquimetroList.get(0).toString();
 			Log.v("Clase getListEstacionamentosFromXML", "Ultimo Id d estacionam "+Id);
 		 } catch (Exception e) { 
 			e.printStackTrace(); 
 		 } 
          
         return parquimetroList;   
     } 
     
     protected void onPostExecute(ParquimetroList parquimetroListt) {     	 
    	 Log.v("Clase getParquimetrosFromXML-posexecute", "ok"); 
     } 
}





/*
	try {     	 
		URL myURL = new URL(UrlListaEstacionamentos[0]); 	 			
		URLConnection ucon = myURL.openConnection();   	 			 
		 
		InputStream is = ucon.getInputStream(); 
		Serializer serializer = new Persister(); 
		ParquimetroList parqList = serializer.read(ParquimetroList.class, is);  			
	    
		//Teste
    Id = parqList.parquimetroList.get(0).toString();
		Log.v("Clase getListParquimetrosFromXML", "Ultimo Id d estacionam "+Id);
	 } catch (Exception e) { 
		e.printStackTrace(); 
	 } 
  
 return parqList;  */