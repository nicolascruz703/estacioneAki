
package models;

import play.*;
import play.db.jpa.*;
 
import javax.persistence.*;
import java.util.*;
 
import play.data.validation.*;
 
@Entity
@Table(name = "estacionamento")
public class Estacionamento extends Model {

   @Required
   @MaxSize(100)
   @Column(name = "nome")
   public String nome;
	
   @Required
   @MaxSize(500)
   @Column(name = "endereco")
   public String endereco;   
   
   @Required
   @MaxSize(14)
   @Column(name = "cnpj")
   public String cnpj;
	
   @Required
   @Column(name = "latitude")
   public String latitude;
	
   @Required
   @Column(name = "longitude")
   public String longitude;
   
   @Required
   @Column(name = "numeroDeVagas")
   public Integer numeroDeVagas;

   @Required
   @Column(name = "precoHora")
   public Integer precoHora;

   
   public static List<Estacionamento> findAll(final String orderBy) {
		final List<Estacionamento> lstResult = find("ORDER BY " + orderBy).fetch();
		return lstResult;
	}
   
	public String getNome() {
		return this.nome;
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

	public Integer getPrecoHora() {
		return precoHora;
	}
	
	public void setPrecoHora(Integer precoHora) {
		this.precoHora = precoHora;
	}
	
	public String toString() {
		return nome;
	}
}