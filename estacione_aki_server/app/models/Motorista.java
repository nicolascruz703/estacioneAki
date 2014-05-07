package models;

import play.*;
import play.db.jpa.*;
 
import javax.persistence.*;
import java.util.*;
 
import play.data.validation.*;

@Entity
public class Motorista extends Model {

	   @Required
	   @MaxSize(50)
	   public String nomeMotorista;
		
	   @Required
	   @MaxSize(11)
	   public String cpf;

	   @Required
	   @MaxSize(15)
	   public String marcaVeiculo;
	   
	   @Required
	   @MaxSize(15)
	   public String modeloVeiculo;
	   
	   @Required
	   @MaxSize(8)
	   public String placaVeiculo;
}
