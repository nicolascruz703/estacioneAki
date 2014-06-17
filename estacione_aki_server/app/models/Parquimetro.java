package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import controllers.CRUD.Hidden;

import java.util.*;

import play.data.validation.*;

@Entity
@Table(name = "parquimetro")
public class Parquimetro extends Model {
	
	   @Required
	   @Column(name = "idVaga")
	   public Integer idVaga;
		
	   @Required
	   @MaxSize(100)
	   @Column(name = "endereco")
	   public String endereco;   
	   
	   @Required
	   @MaxSize(10)
	   @Column(name = "latitude")
	   public String latitude;
		
	   @Required
	   @MaxSize(10)
	   @Column(name = "longitude")
	   public String longitude;
	   
	   @Required
	   @Column(name = "precoHora")
	   public Integer precoHora;

	   @Hidden	   
	   @Column(name = "statusVaga")
	   public String statusVaga;
	   
	   @Hidden
	   @Column(name = "dataHoraOcupacao")
	   public String dataHoraOcupacao;

	   @Hidden
	   @Column(name = "placaVeiculoOcupacao")
	   public String placaVeiculoOcupacao;

	   @Hidden
	   @Column(name = "usuarioEstacioneAki")
	   public String usuarioEstacioneAki;

	   @Hidden
	   @Column(name = "tempoOcupacao")
	   public String tempoOcupacao;
	   
	   @Hidden
	   @Column(name = "valorAPagar")
	   public String valorAPagar;
	   
	   @Hidden
	   @Column(name = "valorPago")
	   public String valorPago;	   
	   
	   public static List<Parquimetro> findAll(final String orderBy) {
			final List<Parquimetro> lstResult = find("ORDER BY " + orderBy).fetch();
			return lstResult;
		}


}
