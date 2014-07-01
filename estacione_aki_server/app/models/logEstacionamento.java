package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import controllers.CRUD.Hidden;

import java.util.*;

import play.data.validation.*;

@Entity
@Table(name = "logEstacionamento")
public class logEstacionamento  extends Model {

	@Column(name = "cnpj")
	public String cnpj;
	
	@Column(name = "dataHoraEntrada")
    public String dataHoraEntrada;
	
	@Column(name = "placaVeiculoOcupacao")
	public String placaVeiculoOcupacao;
    
	@Column(name = "tempoOcupacao")
	public String tempoOcupacao;
	
	@Column(name = "ocupado")
    public boolean ocupado; 
	
}
