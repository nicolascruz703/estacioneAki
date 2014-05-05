package estacioneAki.activities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import estacioneAki.util.EstacionamentoList;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import estacioneAki.util.*;

public class EstacioneAkiMap extends Activity {

	private GoogleMap mMap;
	
	void readLocalXML() throws Exception{
		
		AssetManager assetMan = getAssets();
		InputStream is = assetMan.open("listaEstacionamento.xml");
		Serializer serializer = new Persister();
        EstacionamentoList estList = serializer.read(EstacionamentoList.class, is);
        Iterator<Estacionamento> iterList = estList.estaciomentoList.iterator();
        while(iterList.hasNext()){
        	Estacionamento e = iterList.next();
        	Log.v("estacionameto", e.nome+" "+e.cnpj+"\n");
        }
	}
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacione_aki_map);
        
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Hello world"));
       try {
			readLocalXML();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}