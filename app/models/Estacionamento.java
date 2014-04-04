
package models;

import play.*;
import play.db.jpa.*;
 
import javax.persistence.*;
import java.util.*;
 
import play.data.validation.*;
 
@Entity
public class Estacionamento extends Model {

   @Required
   @MaxSize(100)
   private String nome;
	
   @Required
   @MaxSize(500)
   private String endereco;   
   
   @Required
   @MaxSize(14)
   private String cnpj;
	
   @Required
   private String latitude;
	
   @Required
   private String longitude;
   
   @Required
   private Integer numeroDeVagas;

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public Integer getNumeroDeVagas() {
		return numeroDeVagas;
	}
	
	public void setNumeroDeVagas(Integer numeroDeVagas) {
		this.numeroDeVagas = numeroDeVagas;
	}
	
	public String toString() {
		return nome;
	}
}