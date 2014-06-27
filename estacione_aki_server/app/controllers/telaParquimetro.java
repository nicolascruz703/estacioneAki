package controllers;

import java.util.*;
import java.lang.*;

import play.mvc.Controller;
import models.*;
import play.db.jpa.JPA;

import javax.persistence.*;

public class telaParquimetro extends Controller {
	
    public static void index() {
	
	    Query query = JPA.em().createNativeQuery("select idVaga, endereco, latitude, longitude, precoHora, statusVaga, dataHoraOcupacao, placaVeiculoOcupacao, usuarioEstacioneAki, tempoOcupacao, valorAPagar, valorPago from parquimetro");
	    List <Object[]> listaParquimetro = query.getResultList();
    	
	    Object arrayParquimetro[] = listaParquimetro.get(0);

	    Parquimetro oParquimetro = new Parquimetro();
	    oParquimetro.idVaga = (Integer) arrayParquimetro[0];
	    oParquimetro.endereco = (String) arrayParquimetro[1];
	    oParquimetro.latitude = (String) arrayParquimetro[2];
	    oParquimetro.longitude = (String) arrayParquimetro[3];
	    oParquimetro.precoHora = (Integer) arrayParquimetro[4];
	    oParquimetro.statusVaga = (String) arrayParquimetro[5];
	    oParquimetro.dataHoraOcupacao = (String) arrayParquimetro[6];
	    oParquimetro.placaVeiculoOcupacao = (String) arrayParquimetro[7];
	    oParquimetro.usuarioEstacioneAki = (String) arrayParquimetro[8];
	    oParquimetro.tempoOcupacao = (String) arrayParquimetro[9];
	    oParquimetro.valorAPagar = (String) arrayParquimetro[10];
	    oParquimetro.valorPago = (String) arrayParquimetro[11];
	    	    
	    System.out.println("oParquimetro.valorAPagar:" + oParquimetro.valorAPagar);
	    System.out.println("oParquimetro.valorPago:" + oParquimetro.valorPago);
	    
	    render(oParquimetro);
    	
    }
	
    public static void executaParquimetro(String strplacaVeiculoOcupacao, Integer intstatusParquimetro, String strvalorAPagar, String strvalorPago) {
	
     System.out.println("Executa Parquimetro");

     System.out.println("Status Parquimetro: " + intstatusParquimetro);
     System.out.println("Placa do veiculo: " + strplacaVeiculoOcupacao);
	 System.out.println("Valor a pagar: " + strvalorAPagar);
	 System.out.println("Valor pago: " + strvalorPago);
		 
	 Query query;
	 Object result;
	 Integer intvalorAPagar = 0;
	 
	 if (intstatusParquimetro == 0) {  // Vaga Livre
	 
		 query = JPA.em().createNativeQuery("update parquimetro set statusVaga = '', dataHoraOcupacao='', placaVeiculoOcupacao='', usuarioEstacioneAki=null, valorAPagar=null, valorPago=null, tempoocupacao=null where idVaga = 1");  
		 result = query.executeUpdate();
		 
	 } else if (intstatusParquimetro == 1) {  // Vaga ocupada por um veículo
		 query = JPA.em().createNativeQuery("select count(*) from motorista where placaVeiculo='" + strplacaVeiculoOcupacao +"'");
		 result = query.getSingleResult();
		    
		 System.out.println("Motorista estacione aki: " + result);
		 query = JPA.em().createNativeQuery("update parquimetro set statusVaga = '1', dataHoraOcupacao=date_format(current_timestamp, '%d/%m/%Y %k:%i:%s'), placaVeiculoOcupacao='" + strplacaVeiculoOcupacao +"', usuarioEstacioneAki='"+ result +"' where idVaga = 1");  
		 result = query.executeUpdate();
			
	 } else if (intstatusParquimetro == 2) {  // Vaga em pagamento
		 
		 intvalorAPagar = calculaValorAPagar();
		 
		 query = JPA.em().createNativeQuery("update parquimetro set statusVaga = '2', valorAPagar='" + intvalorAPagar + "' where idVaga = 1");  
		 result = query.executeUpdate();
	 } else if (intstatusParquimetro == 3) {  // Vaga em desocupação
		 if (strvalorAPagar==null) {
			 intvalorAPagar = calculaValorAPagar();
			 strvalorPago = "0";
		 } else intvalorAPagar = Integer.parseInt(strvalorAPagar);
			 
		 query = JPA.em().createNativeQuery("update parquimetro set statusVaga = '3', valorAPagar='" + intvalorAPagar + "', valorPago='" + strvalorPago + "'  where idVaga = 1");  
		 result = query.executeUpdate(); 
	 }

     telaParquimetro.index();
    }
    
    private static Integer calculaValorAPagar () {

    	// Registra no BD o tempo de ocupação -> Timestamp - DataHora Ocupação
    	Query query = JPA.em().createNativeQuery("update parquimetro set tempoOcupacao=timediff(current_timestamp, str_to_date(dataHoraOcupacao,'%d/%m/%Y %k:%i:%s')), valorAPagar = '0' where idVaga = 1");  
		Object result = query.executeUpdate();
    	
    	query = JPA.em().createNativeQuery("select tempoOcupacao, precohora from parquimetro where idVaga=1");
	    List <Object[]> registroParquimetro = query.getResultList();
    	
	    Object arrayregistroParquimetro[] = registroParquimetro.get(0);

	    String strtempoOcupacao = (String) arrayregistroParquimetro[0];
    	Integer intprecoHora = (Integer)  arrayregistroParquimetro[1]; 
 
	    System.out.println("calculaValorAPagar - Tempo Estacionado: " + strtempoOcupacao);
	    System.out.println("calculaValorAPagar - Preço Hora: " + intprecoHora);
		 
		 Integer intvalorAPagar = Integer.parseInt(strtempoOcupacao.substring(0, 2))* intprecoHora;
		 
		 if (Integer.parseInt(strtempoOcupacao.substring(3,5)) + Integer.parseInt(strtempoOcupacao.substring(6)) > 0) intvalorAPagar = intvalorAPagar + intprecoHora;  
		 
		 return intvalorAPagar;
    }
    
    
}