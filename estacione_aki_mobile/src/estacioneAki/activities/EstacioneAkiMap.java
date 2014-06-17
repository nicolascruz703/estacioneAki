package estacioneAki.activities;

import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class EstacioneAkiMap extends Activity implements OnMarkerClickListener {

	private GoogleMap mMap;
	final Context context = this;
	
	void plotaEstacionamentosNoMapa(Iterator<Estacionamento> iterList, String estacionamentoReservado){
	    
		while(iterList.hasNext()){
	    	Estacionamento e = (Estacionamento) iterList.next();
	    	int precoHora = (new Integer(e.getPrecoHora()).intValue());
	    	MarkerOptions marker = new MarkerOptions();
	    	LatLng position = new LatLng(new Double(e.getLatitude()), new Double(e.getLongitude()));
	        marker.position(position);
	        marker.title(e.getNome());
	        marker.snippet("Preço/hora: R$ "+e.getPrecoHora()+". "+e.getEndereco()+"- "+e.getCnpj());
	        if(precoHora > 10){
		        	//escolher icone padrao
		    }else{
	        	switch (precoHora){
        		case 1:
        	        if(estacionamentoReservado.equals(e.getCnpj())){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_01_ve));
        	        } else if(!e.temVagas()){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_01_c));
        	        }
        	        else{
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_01_v));
        	        }
        			break;
        		case 2:
        	        if(estacionamentoReservado.equals(e.getCnpj())){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_02_ve));
        	        }else if(!e.temVagas()){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_02_c));
        	        }
        	        else{
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_02_v));
        	        }
        			break;
        		case 3:
        	        if(estacionamentoReservado.equals(e.getCnpj())){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_03_ve));
        	        }else if(!e.temVagas()){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_03_c));
           	        }
        	        else{
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_03_v));
        	        }
        			break;
        		case 4:
        	        if(estacionamentoReservado.equals(e.getCnpj())){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_04_ve));
        	        }else if(!e.temVagas()){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_04_c));
        	        }
        	        else{
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_04_v));
        	        }
        			break;
        		case 5:
        	        if(estacionamentoReservado.equals(e.getCnpj())){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_05_ve));
        	        } else if(!e.temVagas()){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_05_c));
        	        }else{
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_05_v));
        	        }
        			break;
        		case 6:
        	        if(estacionamentoReservado.equals(e.getCnpj())){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_06_ve));
        	        }else if(!e.temVagas()){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_06_c));
        	        }else{
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_06_v));
        	        }
        			break;
        		case 7:
        	        if(estacionamentoReservado.equals(e.getCnpj())){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_07_ve));
        	        }else if(!e.temVagas()){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_07_c));
        	        }else{
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_07_v));
        	        }
        			break;
        		case 8:
        	        if(estacionamentoReservado.equals(e.getCnpj())){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_08_ve));
        	        }else if(!e.temVagas()){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_08_c));
        	        }else{
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_08_v));
        	        }
        			break;
        		case 9:
        	        if(estacionamentoReservado.equals(e.getCnpj())){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_09_ve));
        	        }else if(!e.temVagas()){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_09_c));
           	        }else{
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_09_v));
        	        }
        			break;
        		case 10:
        	        if(estacionamentoReservado.equals(e.getCnpj())){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_10_ve));
        	        }else if(!e.temVagas()){
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_10_c));
        	        }else{
        	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rd_10_v));
        	        }
        			break;
		        	
		        }
	        }
        	mMap.addMarker(marker);
		} 
	}
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_estacione_aki_map);
       mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
       mMap.setOnMarkerClickListener(this);
       ConexaoServidor conexao = new ConexaoServidor();
       try {
    	   
    	   Iterator<Estacionamento> iterList = conexao.listaEstacionamentos().estaciomentoList.iterator();
    	   String cpf = "12345678901";
    	   String estacionamentoReservado = conexao.verificaReserva(cpf);
    	   plotaEstacionamentosNoMapa(iterList, estacionamentoReservado);
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
								Log.v("botão voltar clicado", "v");
							}
						})
				.setNegativeButton("reservar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int id) {
								ConexaoServidor conexao = new ConexaoServidor();
								String RespostaWBReserva = null;
								try {
									RespostaWBReserva = conexao.reservarVaga(cnpj, "12345678901");
									Toast.makeText(getApplicationContext(), RespostaWBReserva, Toast.LENGTH_LONG).show();	
									for(int i = 0; i < 5000000; i++);
								}catch (Exception e) {								
										e.printStackTrace();
								}
								Intent i = new Intent(context, EstacioneAkiMap.class);  
								startActivity(i);
																

							}
						})
				.setNeutralButton("cancelar", 
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int id) {
								ConexaoServidor Conexao = new ConexaoServidor();								
								try {
									String respostaWBCancelaReserva = Conexao.cancelarVaga("12345678901");	
									Toast.makeText(getApplicationContext(), respostaWBCancelaReserva, Toast.LENGTH_LONG).show();
									Log.v("respostaCancelaReserva", respostaWBCancelaReserva);
								} catch (Exception e) {								
									e.printStackTrace();
								}
								for(int i = 0; i < 5000000; i++);
								Intent i = new Intent(context, EstacioneAkiMap.class);  
								startActivity(i);
							}					
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		return false;
	}
}