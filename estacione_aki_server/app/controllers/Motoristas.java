package controllers;

import java.util.List;

import play.db.Model;

public class Motoristas extends CRUD {
	
public static void listaReserva(int page, final String search, final String searchFields, final String orderBy, final String order) {
	  final ObjectType type = ObjectType.get(getControllerClass());
	  notFoundIfNull(type);
	  if (page < 1) {
	   page = 1;
	  }
	  
	  String strCNPJ = session.get("cpfcnpj");
	  
	  if (strCNPJ!=null) { 
		  strCNPJ = " and reservacnpj='" + session.get("cpfcnpj") + "'"; 
	  	} else {
          strCNPJ = "";
	  	}
  
	  final Object where = "reservaCNPJ <>''" + strCNPJ;
	  final List<Model> objects = type.findPage(page, search, searchFields, orderBy, order, (String) where);
	  final Long count = type.count(search, searchFields, (String) where);
	  final Long totalCount = type.count(null, null, (String) where);
	  render(type, objects, count, totalCount, page, orderBy, order);
	}
}