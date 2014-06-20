package estacioneAki.activities;

import java.io.InputStream;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.google.android.gms.maps.CameraUpdateFactory;
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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class EstacioneAkiMap extends Activity implements OnMarkerClickListener {

	private GoogleMap mMap;
	final Context context = this;
	static String CPF = "0";
	EstacionamentoList estacionamentos; 
	String cnpjEstacionamentoReservado;
	private LatLng posicaoAtual;
	private LocationManager locationManager;
	private LocationListener locationListener;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters	
    private static final long MIN_TIME_BW_UPDATES = 20000;//minimum time between updates in milliseconds
    

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

       //recebe a intent do login
       Intent intent = getIntent();
       CPF = intent.getStringExtra("cpf_usuario");
           
       ConexaoServidor conexao = new ConexaoServidor();
       try {
    	 //plotar estacionamentos
    	   estacionamentos = conexao.listaEstacionamentos();
    	   Iterator<Estacionamento> iterList = estacionamentos.estacionamentoList.iterator();
    	   cnpjEstacionamentoReservado = conexao.verificaReserva(CPF);
    	   plotaEstacionamentosNoMapa(iterList, cnpjEstacionamentoReservado);
    	   
    	   // ----- habilitar GPS
           mMap.setMyLocationEnabled(true);             
           posicaoAtual = new LatLng(-23.559411, -46.731476);
           locationListener = new MyLocationListener();
           locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);           
           locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
           mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicaoAtual,15));

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
		final String cnpjEstacionamentoClicado = snippet2[1];
		Estacionamento estacionamentoReservado = estacionamentos.getPorCnpj(cnpjEstacionamentoReservado);
		Boolean fezReserva = carregaReserva();
		if(fezReserva){
			alertDialogBuilder.setTitle("Você precisa cancelar uma reserva realizada.");
			alertDialogBuilder
			.setMessage("Deseja cancelar uma vaga no "+estacionamentoReservado.getNome()+"?")
			.setCancelable(false)
			.setPositiveButton("voltar",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,	int id) {
							Log.v("botão voltar clicado", "v");
						}
					})
			.setNeutralButton("cancelar", 
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,	int id) {
							ConexaoServidor Conexao = new ConexaoServidor();								
							try {
								String respostaWBCancelaReserva = Conexao.cancelarVaga(CPF);	
								Toast.makeText(getApplicationContext(), respostaWBCancelaReserva, Toast.LENGTH_LONG).show();
								Log.v("respostaCancelaReserva", respostaWBCancelaReserva);
								if(respostaWBCancelaReserva.equals("Cancelamento realizado com sucesso!!!")){
									salvaReserva(false, "sem_reserva");
								}
							} catch (Exception e) {								
								e.printStackTrace();
							}
							for(int i = 0; i < 5000000; i++);
							Intent i = new Intent(context, EstacioneAkiMap.class);  
							startActivity(i);
						}					
			});			
		}else{
			alertDialogBuilder.setTitle(marker.getTitle()+"\nPreço/hora "+precoHora);
			alertDialogBuilder
			.setMessage("Deseja reservar uma vaga?")
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
							String respostaWBReserva = null;
							try {
								respostaWBReserva = conexao.reservarVaga(cnpjEstacionamentoClicado, CPF);
								Toast.makeText(getApplicationContext(), respostaWBReserva, Toast.LENGTH_LONG).show();
								if(respostaWBReserva.equals("Reserva realizada com sucesso!!!")){
									salvaReserva(true, cnpjEstacionamentoClicado);
								}
								for(int i = 0; i < 5000000; i++);
								Intent i = new Intent(context, EstacioneAkiMap.class);  
								startActivity(i);
							}catch (Exception e) {								
									e.printStackTrace();
							}
						}
					});
		}
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		return false;
	}
	
	public void salvaReserva(Boolean fezReserva, String cnpjEstacionamento){
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("fez_reserva", fezReserva);
		editor.putString("estacionamento_reservado", cnpjEstacionamento);
		editor.commit();
	}
	
	public Boolean carregaReserva(){
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		//1a chamada
		ConexaoServidor c = new ConexaoServidor();
		String valorPadrao = null; //usado na primeira execução do aplicativo
		Boolean fezReserva = null;
		try {
			valorPadrao = c.verificaReserva(CPF);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(valorPadrao.equals("nao_encontrado")){	
			fezReserva = sharedPreferences.getBoolean("fez_reserva", false);
		}else{
			fezReserva = sharedPreferences.getBoolean("fez_reserva", true);
		}
		return fezReserva;
	}
	
	//ANA
		public class MyLocationListener implements LocationListener
		{
			public LatLng posicaoAtual;
			
			@Override
			public void onLocationChanged(Location loc)
			{
				loc.getLatitude();
				loc.getLongitude();
				String Text = "Minha ubicação atual é: " +
				"Latitud = " + loc.getLatitude() +
				"Longitud = " + loc.getLongitude();
				//Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();	
				
				posicaoAtual = new LatLng(new Double(loc.getLatitude()), new Double(loc.getLongitude()));
				
				
			}
		 
			@Override
			public void onProviderDisabled(String provider)
			{	Toast.makeText( getApplicationContext(),"GPS Desligado",Toast.LENGTH_SHORT ).show();}
			 
			@Override
			public void onProviderEnabled(String provider)
			{	Toast.makeText( getApplicationContext(),"GPS Ligado",Toast.LENGTH_SHORT).show();}
			 
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras)
			{	}	 
		}    
	    //-------------
}


