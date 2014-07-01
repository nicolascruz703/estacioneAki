package controllers;

import models.inEstacionamento;
import play.data.binding.Binder;
import play.data.validation.Validation;
import play.db.jpa.JPA;
import play.exceptions.TemplateNotFoundException;
import play.i18n.Messages;
import play.mvc.With;

import java.lang.*;

import javax.persistence.*;

@With(Secure.class)
public class inEstacionamentos extends CRUD {

	public static void create() throws Exception {
		
		final ObjectType type = ObjectType.get(getControllerClass());
		notFoundIfNull(type);
		final inEstacionamento object = new inEstacionamento();
		Binder.bindBean(params.getRootParamNode(), "object", object);
		validation.valid(object);
		if (Validation.hasErrors()) {
			renderArgs.put("error", Messages.get("crud.hasErrors"));
			renderPage(type, object);
		}

		// validateParkingSpot();
		String strRetorno = verificaVaga(object.placaVeiculoOcupacao);
		Integer intverificaVaga = Integer.parseInt(strRetorno.substring(0, 1));
		strRetorno = strRetorno.substring(1);
		System.out.println("Situacao da Vaga:" + intverificaVaga + " - " + strRetorno);
		if (intverificaVaga < 3) {
			flash.error(strRetorno);
			renderPage(type, object);
		}
				
		object.save();
		flash.success(Messages.get("crud.created", type.modelName));
		if (params.get("_save") != null) {
			redirect(request.controller + ".list");
		}
		if (params.get("_saveAndAddAnother") != null) {
			redirect(request.controller + ".blank");
		}
		redirect(request.controller + ".show", object._key());
	}

	private static void renderPage(final ObjectType type,
			final inEstacionamento object) {
		try {
			render(request.controller.replace(".", "/") + "/blank.html", type,
					object);
		} catch (final TemplateNotFoundException e) {
			render("CRUD/blank.html", type, object);
		}
	}
	
    private static String verificaVaga (String strPlaca) {
    // Retorno = 0 -> (recusa) Estacionamento sem vaga
    // Retorno = 1 -> (recusa) Usuário não pertence ao programa estacione Aki
    // Retorno = 2 -> (recusa) Usuário com vaga pré-reservada em outro estacionamento 
    // Retorno = 3 -> (aceite) Usuário estacione Aki com vaga disponível - sem vaga pré-reservada
  	// Retorno = 4 -> (aceite) Usuário com vaga pré-reservada no estacionamento correto - convertida em vaga ocupada

     try {	

   	 // verfica quantidade de vagas
   	 Query query = JPA.em().createNativeQuery("select numeroDeVagas from estacionamento where cnpj='" +  session.get("cpfcnpj") +"'");
   	 Object result = query.getSingleResult();
	 Integer intnumeroDeVagas = (Integer) result;

	 if (intnumeroDeVagas < 1) { 
		 return "0Nos desculpem mas não há vagas!"; // sem vagas
	 } else {
	   	 query = JPA.em().createNativeQuery("select reservaCNPJ from motorista where placaVeiculo='" +  strPlaca +"'");
	   	 result = query.getSingleResult();
		 String strReservaCNPJ = (String) result;
	   	 
		 if (strReservaCNPJ.isEmpty()) {
	    	// Subtrai o n.o de vagas de 1 unidade para este cnpj
	    	query = JPA.em().createNativeQuery("update estacionamento set numeroDeVagas = numeroDeVagas - 1 where cnpj = '" + session.get("cpfcnpj") +"'");  
	    	result = query.executeUpdate();
			 
			return "3"; // vaga disponível para motorista sem reserva
		 } else if (strReservaCNPJ.equals(session.get("cpfcnpj"))) {
	   		//cancela Reserva
	   		 
    		query = JPA.em().createNativeQuery("update motorista set reservaCNPJ = '', reservaEstacionamento = '', dataHoraReserva = '' where placaVeiculo='" +  strPlaca +"'");  
    		result = query.executeUpdate();
			 
	   		return "4";  // vaga disponível para motorista reservadp
	   	 	}
	   		else {
	   			return "2Entrada recusada pelo sistema, veículo reservado em outro estacionamento Estacione Aki!"; // vaga recusada, apesar de vaga disponível, devido a reserva em outro estacionamento
	   		}
	   	 }
    }
		 
	catch (NoResultException e) {
		return "1Entrada recusada pelo sistema, veículo não cadastrado no programa Estacione Aki!"; // usuário não estacione aki;
	} 
  }
}
