package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;
import controllers.CRUD.Hidden;

@Entity
@Table(name = "logParquimetro")
public class logParquimetro  extends Model {
		
	   @Hidden
	   @Column(name = "dataHoraOcupacao")
	   public String dataHoraOcupacao;

	   @Required
	   @Column(name = "idParquimetro")
	   public Integer idParquimetro;
	   
	   @Required
	   @Column(name = "idVaga")
	   public Integer idVaga;
	   
	   @Hidden
	   @Column(name = "placaVeiculoOcupacao")
	   public String placaVeiculoOcupacao;

	   @Hidden
	   @Column(name = "tempoOcupacao")
	   public String tempoOcupacao;
	   
	   @Hidden
	   @Column(name = "valorAPagar")
	   public String valorAPagar;
	   
	   @Hidden
	   @Column(name = "valorDevido")
	   public String valorDevido;	  
}
