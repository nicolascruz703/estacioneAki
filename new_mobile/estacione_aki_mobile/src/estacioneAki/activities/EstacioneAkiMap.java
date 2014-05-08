package estacioneAki.activities;

import java.io.InputStream;
import java.util.Iterator;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import estacioneAki.util.Estacionamento;
import estacioneAki.util.EstacionamentoList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Button;

public class EstacioneAkiMap extends Activity implements OnMarkerClickListener {

	private GoogleMap mMap;
	final Context context = this;
	
	Iterator<Estacionamento> leXMLLocal() throws Exception{
		
		AssetManager assetMan = getAssets();
		InputStream is = assetMan.open("listaEstacionamento.xml");
		Serializer serializer = new Persister();
        EstacionamentoList estList = serializer.read(EstacionamentoList.class, is);
        Iterator<Estacionamento> iterList = estList.estaciomentoList.iterator();
        return iterList;
 	}	

	void plotaEstacionamentosNoMapa(Iterator<Estacionamento> iterList){
	    while(iterList.hasNext()){
	    	Estacionamento e = (Estacionamento) iterList.next();
	    	MarkerOptions marker = new MarkerOptions();
	    	LatLng position = new LatLng(new Double(e.getLatitude()).doubleValue(), new Double(e.getLongitude()).doubleValue());
	        marker.position(position);
	        marker.title(e.getNome());
	        marker.snippet("Preço/hora: R$ "+e.getPrecoHora()+". "+e.getEndereco());
	        mMap.addMarker(marker);
	    } 
	}
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_estacione_aki_map);
       mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
       mMap.setOnMarkerClickListener(this);
       try {
    	   Iterator<Estacionamento> iterList = leXMLLocal();
    	   plotaEstacionamentosNoMapa(iterList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	public boolean onMarkerClick(Marker marker) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		String[] snippet = marker.getSnippet().split(" ");
		String precoHora = snippet[1]+" "+snippet[2];
		alertDialogBuilder.setTitle(marker.getTitle()+"\npreço/hora "+precoHora);
		alertDialogBuilder
				.setMessage("Deseja reservar ou cancelar um vaga?")
				.setCancelable(false)
				.setPositiveButton("voltar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}

						})
				.setNegativeButton("reservar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int id) {
								dialog.cancel(); //chamada do serviï¿½o de reserva
							}
						})
				.setNeutralButton("cancelar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int id) {
						dialog.cancel(); //chamada do serviï¿½o de cancelamento
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		return false;
	}
}