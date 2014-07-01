package controllers;

import java.util.List;

import javax.persistence.Query;

import models.logEstacionamento;
import play.data.binding.Binder;
import play.db.Model;
import play.db.jpa.JPA;
import play.exceptions.TemplateNotFoundException;
import play.i18n.Messages;
import play.mvc.With;

@With(Secure.class)
public class logEstacionamentos extends CRUD {

	 public static void listaSaida(int page, final String search, final String searchFields, final String orderBy, final String order) {
		  final ObjectType type = ObjectType.get(getControllerClass());
		  notFoundIfNull(type);
		  if (page < 1) {
		   page = 1;
		  }
		  
		  String strCNPJ = session.get("cpfcnpj");
		  
		  if (strCNPJ!=null) { 
			  strCNPJ = " and cnpj='" + session.get("cpfcnpj") + "'"; 
		  	} else {
              strCNPJ = "";
		  	}
	  
		  final String where = "(ocupado IS TRUE OR ocupado = 1)" + strCNPJ;
		  final List<Model> objects = type.findPage(page, search, searchFields, orderBy, order, (String) where);
		  final Long count = type.count(search, searchFields, (String) where);
		  final Long totalCount = type.count(null, null, (String) where);
		  render(type, objects, count, totalCount, page, orderBy, order);
		 }

	 public static void list(int page, final String search, final String searchFields, final String orderBy, final String order) {
		  final ObjectType type = ObjectType.get(getControllerClass());
		  notFoundIfNull(type);
		  if (page < 1) {
		   page = 1;
		  }

		  String strCNPJ = session.get("cpfcnpj");
		  
		  if (strCNPJ!=null) { 
			  strCNPJ = " and cnpj='" + session.get("cpfcnpj") + "'"; 
		  	} else {
              strCNPJ = "";
		  	}

		  System.out.println("Filtro CNPJ:" + strCNPJ);
		  
		  final String where = "(ocupado IS FALSE OR ocupado = 0)" + strCNPJ;
		  final List<Model> objects = type.findPage(page, search, searchFields, orderBy, order, (String) where);
		  final Long count = type.count(search, searchFields, (String) where);
		  final Long totalCount = type.count(null, null, (String) where);
		  render(type, objects, count, totalCount, page, orderBy, order);
		 }

	 
	 public static void save(String id) throws Exception {
	        final ObjectType type = ObjectType.get(getControllerClass());
	        notFoundIfNull(type);
	        final logEstacionamento object = (logEstacionamento) type.findById(id);
	        notFoundIfNull(object);
	        Binder.bindBean(params.getRootParamNode(), "object", object);
	        validation.valid(object);
	        if (validation.hasErrors()) {
				renderArgs.put("error", Messages.get("crud.hasErrors"));
				renderPage(type, object);
	        }
	        
			// Redisponibilizar vaga ocupada();
    		// Soma o n.o de vagas de 1 unidade para este cnpj
    		Query query = JPA.em().createNativeQuery("update estacionamento set numeroDeVagas = numeroDeVagas + 1 where cnpj = '" + session.get("cpfcnpj") +"'");  
    		Object result = query.executeUpdate();
    		System.out.println("Retorno vaga Estacionamento:" + session.get("cpfcnpj"));
	        
	        object._save();
	        flash.success(play.i18n.Messages.get("crud.saved", type.modelName));
	        if (params.get("_save") != null) {
	            redirect(request.controller + ".list");
	        }
	        redirect(request.controller + ".show", object._key());
	    }
	 
		private static void renderPage(final ObjectType type,
				final logEstacionamento object) {
			try {
				render(request.controller.replace(".", "/") + "/blank.html", type,
						object);
			} catch (final TemplateNotFoundException e) {
				render("CRUD/blank.html", type, object);
			}
		}
}
