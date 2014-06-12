package controllers;

import java.util.*;
import java.lang.*;

import play.mvc.Controller;
import models.*;
import play.db.jpa.JPA;

import javax.persistence.*;

public class telaParquimetro extends Controller {
	
    public static void index() {
	
	    Query query = JPA.em().createNativeQuery("select idVaga, endereco, latitude, longitude, precoHora from parquimetro");
	    List <Object[]> listaParquimetro = query.getResultList();
    	
	    Object arrayParquimetro[] = listaParquimetro.get(0);

	    Parquimetro oParquimetro = new Parquimetro();
	    oParquimetro.idVaga = (Integer) arrayParquimetro[0];
	    oParquimetro.endereco = (String) arrayParquimetro[1];
	    oParquimetro.latitude = (String) arrayParquimetro[2];
	    oParquimetro.longitude = (String) arrayParquimetro[3];
	    oParquimetro.precoHora = (Integer) arrayParquimetro[4];
	    	    
	    System.out.println("Latitude:" + oParquimetro.latitude);
	    
	    render(oParquimetro);
    	
    }
	
    public static void executaParquimetro(String txtPlaca, Integer statusParquimetro) {

	 System.out.println("Placa do veiculo: " + txtPlaca);
	 System.out.println("Status Parquimetro: " + statusParquimetro);
	 try {
		 
		 if (statusParquimetro == 1) {  // Vaga ocupada por um veículo

		    Query query = JPA.em().createNativeQuery("select nomeMotorista from motorista where placaVeiculo='" + txtPlaca +"'");
		    Object result = query.getSingleResult();
		    System.out.println("Nome do motorista na vaga:" + result);    
		    //String strNomeMotorista  = (String) result;
			 
		 }

	  }
	  catch (NoResultException e) {
		  System.out.println("Não é usuário Estacione Aki");
		  }  
    }
    
}