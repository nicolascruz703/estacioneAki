package controllers;

import javax.persistence.Query;

import models.*;
import play.db.jpa.JPA;

public class Security extends Secure.Security {

    static boolean authentify(String email, String password) {
        return User.connect(email, password) != null;
    }
    
    static boolean check(String profile) {

    	String struserType = User.find("byEmail", connected()).<User>first().userType;
    	session.put("idUser", User.find("byEmail", connected()).<User>first().id);
        //if(profile.equals(User.find("byEmail", connected()).<User>first().userType)) {
    	
    	if(profile.equals(struserType)) {

        	// Armazena o CPF ou CNPJ em cookie
        	session.put("cpfcnpj", User.find("byEmail", connected()).<User>first().cpfcnpj);
        	//System.out.println("CPFCNPJ user connected:" + session.get("cpfcnpj"));
        	
        	// Recupera o id do motorista ou do estacionamento
        	if (struserType.equals("M")) {
        	    Query query = JPA.em().createNativeQuery("select id from motorista where cpf = '" + session.get("cpfcnpj") +"'");
        	    Object result = query.getSingleResult();
        	    session.put("idMotorista", result);
        	    //System.out.println("id Motorista connected:" + session.get("idMotorista"));
        	}
        	else if  (struserType.equals("E")) {
        	    Query query = JPA.em().createNativeQuery("select id from estacionamento where cnpj = '" + session.get("cpfcnpj") +"'");
        	    Object result = query.getSingleResult();
        	    session.put("idEstacionamento", result);
        	    //System.out.println("id Estacionamento connected:" + session.get("idEstacionamento"));        		
        	}
        	else if  (struserType.equals("A")) {
        		session.put("cpfcnpj", null);
        		session.put("idEstacionamento", null);
        		session.put("idMotorista", null);
        	}
        	
            return true;
        }        
        return false;
    }
    
    static void onDisconnected() {
        Application.index();
    }
    
    static void onAuthenticated() {
        Admin.index();
    }
    
}

