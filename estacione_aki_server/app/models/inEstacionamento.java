package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import controllers.CRUD.Hidden;

import java.util.*;

import play.data.validation.*;

@Entity
@Table(name = "inEstacionamento")
public class inEstacionamento  extends Model {

	@Column(name = "cnpj")
	public String cnpj;
	
	@MaxSize(8)
	@Column(name = "placaVeiculoOcupacao")
	public String placaVeiculoOcupacao;
    	
}

