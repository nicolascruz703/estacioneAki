package controllers;

import java.util.*;
import java.lang.*;

import play.mvc.Controller;
import models.*;
import play.db.jpa.JPA;
import javax.persistence.*;

public class telaParquimetro extends Controller {
	
    public static void index() {

 //   	Parquimetro oParquimetro = new Parquimetro();
 //   	Parquimetro oParquimetro = Parquimetro.findById(1);
    		
 //   	oParquimetro.idVaga = 1;
 //   	oParquimetro.endereco = "Rua Eugene Carriere no 30";
 //   	oParquimetro.latitude = "-34.123";
 //   	oParquimetro.longitude = "-23.1231";
 //  	oParquimetro.precoHora = 3;
    	
 //   	System.out.println("Latitude:" + oParquimetro.latitude);
    	
 //       render(oParquimetro);
    	
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
	
}