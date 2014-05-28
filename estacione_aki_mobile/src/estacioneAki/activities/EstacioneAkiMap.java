package estacioneAki.activities;

import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import estacioneAki.servico.ConexaoServidor;
import estacioneAki.servico.getListEstacionamentosFromXML;
import estacioneAki.util.Estacionamento;
import estacioneAki.util.EstacionamentoList;
import estacioneAki.util.Retorno;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class EstacioneAkiMap extends Activity implements OnMarkerClickListener {

	private GoogleMap mMap;
	final Context context = this;
	
	void plotaEstacionamentosNoMapa(Iterator<Estacionamento> iterList){
	    while(iterList.hasNext()){
	    	Estacionamento e = (Estacionamento) iterList.next();
	    	MarkerOptions marker = new MarkerOptions();
	    	LatLng position = new LatLng(new Double(e.getLatitude()), new Double(e.getLongitude()));
	        marker.position(position);
	        marker.title(e.getNome());
	        marker.snippet("Preço/hora: R$ "+e.getPrecoHora()+". "+e.getEndereco()+"- "+e.getCnpj());
	        mMap.addMarker(marker);
	    } 
	}
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_estacione_aki_map);
       mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
       mMap.setOnMarkerClickListener(this);
       ConexaoServidor Conexao = new ConexaoServidor();
       try {
    	   
    	   Iterator<Estacionamento> iterList = Conexao.listaEstacionamentos().estaciomentoList.iterator();
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
		String[] snippet2 = marker.getSnippet().split("- ");
		final String cnpj = snippet2[1];
		alertDialogBuilder.setTitle(marker.getTitle()+"\nPreço/hora "+precoHora);
		alertDialogBuilder
				.setMessage("Deseja reservar ou cancelar um vaga?")
				.setCancelable(false)
				.setPositiveButton("voltar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int id) {
								ConexaoServidor Conexao = new ConexaoServidor();
								try {
									EstacionamentoList ListaEstacionamentos = Conexao.listaEstacionamentos();									
									//teste
									for (Iterator it = ListaEstacionamentos.estaciomentoList.iterator(); it.hasNext(); ) {  
										Estacionamento obj = (Estacionamento) it.next();
										Log.v("ANA: clase actididad voltar",obj.getId());
									} 										
								} catch (Exception e) {								
									e.printStackTrace();
								}
							}
						})
				.setNegativeButton("reservar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int id) {								
								ConexaoServidor Conexao = new ConexaoServidor();								
								try {									
									Log.v("ANA: clase actididad Reserva","antes da chamada");
									String RespostaWBReserva = Conexao.reservarVaga(cnpj, "12345678901");									
									Toast.makeText(getApplicationContext(), RespostaWBReserva, Toast.LENGTH_LONG).show();									
									Log.v("ANA: clase actididad Reservar",RespostaWBReserva.toString());									 									
								} catch (Exception e) {								
									e.printStackTrace();
								}
							}
						})
				.setNeutralButton("cancelar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int id) {
								Log.v("ANA: clase actididad cancelar Reservar","metodo Reserva");
								ConexaoServidor Conexao = new ConexaoServidor();								
								try {
									Log.v("ANA: clase actididad CancelarReserva","antes da chamada");
									String RespostaWBCancelaReserva = Conexao.cancelarVaga("12345678901");	
									Toast.makeText(getApplicationContext(), RespostaWBCancelaReserva, Toast.LENGTH_LONG).show();
									//teste
									Log.v("ANA: clase actididad CancelaReservar",RespostaWBCancelaReserva.toString());									 									
								} catch (Exception e) {								
									e.printStackTrace();
								}
							}					
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		return false;
	}
}