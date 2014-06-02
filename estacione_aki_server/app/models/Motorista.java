package models;

import play.*;
import play.db.jpa.*;
 
import javax.persistence.*;
import java.util.*;
 
import play.data.validation.*;
import controllers.CRUD.Hidden;

@Entity
public class Motorista extends Model {

	   @Required
	   @MaxSize(50)
	   public String nomeMotorista;
		
	   @Required
	   @Unique
	   @Column(unique = true)
	   @MaxSize(11)
	   @MinSize(11)
	   public String cpf;

	   @Password
	   @MaxSize(8)
	   public String senha;
	   
	   @Required
	   @MaxSize(20)
	   public String marcaVeiculo;
	   
	   @Required
	   @MaxSize(20)
	   public String modeloVeiculo;
	   
	   @Required
	   @MaxSize(8)
	   public String placaVeiculo;

	   @Hidden
	   public String reservaCNPJ;
	   
	   @Hidden
	   public String reservaEstacionamento;
	   
	   @Hidden
	   public String dataHoraReserva;	 
}

