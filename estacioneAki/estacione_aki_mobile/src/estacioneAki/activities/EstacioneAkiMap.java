package estacioneAki.activities;

import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Position;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint("UseValueOf")
public class EstacioneAkiMap extends Activity implements OnMarkerClickListener, LocationListener{

	private GoogleMap mMap;
	final Context context = this;	
	private LatLng posicaoAtual;
	private LocationManager locationManager;	
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters	
    private static final long MIN_TIME_BW_UPDATES = 20000;//minimum time between updates in milliseconds
	
    private Intent intent;
    private String Login;
    
	void plotaEstacionamentosNoMapa(Iterator<Estacionamento> iterList){
	    while(iterList.hasNext()){
	    	Estacionamento e = (Estacionamento) iterList.next();
	    	int precoHora = (new Integer(e.getPrecoHora()).intValue());
	    	MarkerOptions marker = new MarkerOptions();
	    	LatLng position = new LatLng(new Double(e.getLatitude()), new Double(e.getLongitude()));
	        marker.position(position);
	        marker.title(e.getNome());
	        marker.snippet("Preço/hora: R$ "+e.getPrecoHora()+". "+e.getEndereco()+"- "+e.getCnpj());
	        if(!e.temVagas()){
	        	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.sem_vagas));
	        }else{
		        if(precoHora > 5){
		        	//escolher icone padrao
		        	
		        }else{
		        	switch (precoHora){
		        		case 1:
		        			marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rsz_1real));
		        			break;
		        		case 2:
		        			marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rsz_2reais));
		        			break;
		        		case 3:
		        			marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rsz_3reais));
		        			break;
		        		case 4:
		        			marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rsz_4reais));
		        			break;
		        		case 5:
		        			marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rsz_5reais));
		        			break;
		        	}
		        }
	        }
	        mMap.addMarker(marker);
	    } 
	}
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_estacione_aki_map);
       
       //recebe a intent enviada
       intent = getIntent();
       Log.v("inicio de sesion ok","new intent");
       //armazena os números digitados
       Login = intent.getStringExtra("Login");
     
       if(Login=="ok")
       {
    	   Log.v("inicio de sesion ok","true");
    	   mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();	       
	       mMap.setOnMarkerClickListener(this);       
	       mMap.setMyLocationEnabled(true);
	       
	       // ----- habilitar GPS
	       locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);       
	       locationManager = (LocationManager) this.context.getSystemService(LOCATION_SERVICE);
	       if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){    	   
	           locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
	                   MIN_TIME_BW_UPDATES,
	                   MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	           Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); 	           	                      
	           if(location!=null)
	           {     
	        	   Log.v("location",location.getLatitude() + " log "+ location.getLongitude());
	        	   onLocationChanged(location);        	   
	           }else{
	               Toast.makeText(getBaseContext(), "Não pode se encontrar a sua ubicação", Toast.LENGTH_SHORT).show();
	   		   }
		    
	       }else{
	           Toast.makeText(getBaseContext(), "Não existe proveedor de GPS", Toast.LENGTH_SHORT).show();
	       }
	       mMap.moveCamera(CameraUpdateFactory.newLatLng(posicaoAtual));		
	       mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
	       
	       //---- tela login
	       //Log.v("pasar para loginactivity", "antes");
	       //Intent intent = new Intent(this, LoginActivity.class);       
	       //startActivityForResult(intent, 1);
	       
	       //----- plotar estacionamentos
	       ConexaoServidor Conexao = new ConexaoServidor();
	       try {
	    	   
	    	   Iterator<Estacionamento> iterList = Conexao.listaEstacionamentos().estaciomentoList.iterator();
	    	   plotaEstacionamentosNoMapa(iterList);    	 
	       } catch (Exception e) {
				e.printStackTrace();
	       }
       }
       else
       {
    	   Log.v("errorinicio de sesion","false");
       }
    }

	//protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

	  //  if (requestCode == 1) {
	    //    if(resultCode == RESULT_OK){	        	
	      //      String result=intent.getStringExtra("cpf_usuario");
	        //    Log.v("login act ok", result);
	        //}
	        //if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        	//Log.v("login act fallo", "-");
	        //}
	    //}
	//}
	
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

	@Override
	public void onLocationChanged(Location location) {	     
		posicaoAtual = new LatLng(new Double(location.getLatitude()), new Double(location.getLongitude()));		
		Log.v("location",location.getLatitude() + " log "+ location.getLongitude());
	}
 
	@Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
	@Override
	public void onProviderEnabled(String provider) {
    	Log.v("gps activo", "ok");
    }
	@Override
    public void onProviderDisabled(String provider) {
    	Log.v("gps activo", "falso");
    }

}