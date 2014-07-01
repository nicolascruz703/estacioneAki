package controllers;

import play.mvc.Controller;

//import java.util.ArrayList;
import java.util.*;

import models.*;

import com.sun.media.sound.ModelSource;
import com.thoughtworks.xstream.XStream;

import play.db.jpa.JPA;

import javax.persistence.*;

public class MobileInterface extends Controller {

	public static void listaEstacionamento() {
		
		List<Estacionamento> list = Estacionamento.findAll("nome");
		
		XStream xstream = new XStream();
		xstream.alias("estacionamento", models.Estacionamento.class);
		
		renderXml(list, xstream);		
	}
	
	public static void reservaVaga(String cnpj, String cpf) {
	// confirma se há vaga livre e se o motorista já efetuou uma reserva
	// Se positivo: registrar a reserva para o cpf correspondente
	// 				retornar ao celular a posição de todos os estacionamentos
	// Se negativo: retornar mensagem de Erro
		
	  try {	
		  
		// Verifica se estacionamento existe na base e quantas vagas estão disponíveis
	    Query query = JPA.em().createNativeQuery("select numeroDeVagas from estacionamento where cnpj = '" + cnpj +"'");
	    Object result = query.getSingleResult();
	    System.out.println("Quantidade de Vagas em estacionamento para reserva:" + result);    
	    int intNumeroDeVagas = (Integer) result;
	    
	    //Verifica se o Motorista existe na base e se a já há reserva registrada
	    query = JPA.em().createNativeQuery("select reservaCNPJ from motorista where cpf = '" + cpf +"'");
	    result = query.getSingleResult();
	    
	    String strCNPJ = result.toString();
	       
	    if (!strCNPJ.isEmpty()) {
	    	// O sistema não permite que o motorista que solicite uma segunda reserva se já efetuou uma reserva anteriormente   	
	    	msgRetorno ("Não é possivel efetuar mais de uma reserva.");
	    }
	    else {
	    	if (intNumeroDeVagas <= 0) {
	    		// situação em que não há vaga livre
	    		msgRetorno ("Nos desculpem... Estacionamento selecionado sem vaga disponível no momento.");
	    		}
	    	else {	
	    	
	    		// Subtrai o n.o de vagas de 1 unidade para este cnpj
	    		query = JPA.em().createNativeQuery("update estacionamento set numeroDeVagas = numeroDeVagas - 1 where cnpj = '" + cnpj +"'");  
	    		result = query.executeUpdate();
	    		System.out.println("Estacionamento reservado:" + result);
		
	    		// atualiza o registro de motorista com o cnpj do estacionamento
	    		query = JPA.em().createNativeQuery("update motorista set reservaCNPJ = '" + cnpj +"', dataHoraReserva = date_format(current_timestamp, '%d/%m/%Y %k:%i:%s') where cpf ='" + cpf +"'");  
	    		result = query.executeUpdate();
	    			    		
	    		query = JPA.em().createNativeQuery("update motorista set ReservaEstacionamento = (select nome from estacionamento where cnpj = '" + cnpj +"') where cpf ='" + cpf +"'");
	    		result = query.executeUpdate();		
	    		
	    		System.out.println("Reserva Motorista registrada:" + result);
		    
	    		//listaEstacionamento();
	    		msgRetorno ("Reserva realizada com sucesso!!!");
	    	}
		}
	    
	  }
	  catch (NoResultException e) {
		msgRetorno ("CNPJ ou CPF não encontrado na base de dados");
	   }   
	}
	
	public static void cancelaReservaVaga(String cpf) {
	// procurar o CNPJ do estacionamento reservado pelo CPF informado 
	// Cancelar a reserva da vaga
	// retornar ao celular a posição de vagas livres atualizada
		
	  try {	
		    //Verifica se o Motorista existe na base e se a já há reserva registrada
		    Query query = JPA.em().createNativeQuery("select reservaCNPJ from motorista where cpf = '" + cpf +"'");
		    Object result = query.getSingleResult();
		    System.out.println("CNPJ Estacionamento reservado:" + result);
			 
		    String strReservaCNPJ = result.toString();
		    if (strReservaCNPJ.isEmpty()) {
		    	// Nenhuma reserva efetuada para este motorista
		    	msgRetorno ("Não há reserva registrada para o cpf " + cpf);
		    }
		    else
		    {
	    		// Soma o n.o de vagas de 1 unidade para este cnpj
	    		query = JPA.em().createNativeQuery("update estacionamento set numeroDeVagas = numeroDeVagas + 1 where cnpj = '" + strReservaCNPJ +"'");  
	    		result = query.executeUpdate();
	    		System.out.println("Cancelada Reserva Estacionamento:" + strReservaCNPJ);
		    	
		    	// limpa o registro de reserva de motorista 
	    		query = JPA.em().createNativeQuery("update motorista set reservaCNPJ = '', reservaEstacionamento = '', dataHoraReserva = '' where cpf ='" + cpf +"'");  
	    		result = query.executeUpdate();
	    		System.out.println("Cancelado reserva Motorista:" + cpf);
		    	
		    	//listaEstacionamento();
	    		msgRetorno ("Cancelamento realizado com sucesso!!!");
		    }
	   	  }
		  catch (NoResultException e) {
			  msgRetorno ("CPF não encontrado na base de dados");
		   }   
	  
	}

	public static void listaParquimetro() {
		
		List<Parquimetro> list = Parquimetro.findAll("id");
		
		XStream xstream = new XStream();
		xstream.alias("parquimetro", models.Parquimetro.class);
		
		renderXml(list, xstream);		
	}
	
	public static void loginMotorista (String cpf, String senha) {
		  try {	
			    //Verifica se o Motorista existe na base e se a já há reserva registrada
			    Query query = JPA.em().createNativeQuery("select senha from motorista where cpf = '" + cpf +"'");
			    Object result = query.getSingleResult();
			 
			    String strSenha = result.toString();
			    if (strSenha.contentEquals(senha)) {
			    	// Login aprovado
			    	msgRetorno ("1:Login autorizado para o cpf " + cpf);
			    }
			    else {
			    	msgRetorno ("0:Login negado para o cpf " + cpf);
			    }
		  	  }
		  catch (NoResultException e) {
			  msgRetorno ("0:CPF não encontrado na base de dados");
		  }   
	}
	
	public static void verificaReserva (String cpf) {
		  try {	
			    //Verifica se o Motorista existe na base e se a já há reserva registrada
			    Query query = JPA.em().createNativeQuery("select reservaCNPJ from motorista where cpf = '" + cpf +"'");
			    Object result = query.getSingleResult();
			    
			    String strCNPJ = result.toString();
			    System.out.println("x" + strCNPJ+ "x");
			    
			    if (result.toString().isEmpty()) msgRetorno ("nao_encontrado");
			    else  msgRetorno (result.toString());
		      }
		   catch (NoResultException e) {
			  msgRetorno ("nao_encontrado");
		  }   
	}
	
	private static void msgRetorno (String msg) {
		Retorno retReservaVaga = new Retorno();				
    	retReservaVaga.mensagem = msg;
    					
    	XStream xstream = new XStream();
    	xstream.alias("retorno", models.Retorno.class);

    	renderXml(retReservaVaga, xstream);	
	}
}
	