package estacioneAki.util;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="parquimetro")
public class Parquimetro {
	
	@Element
	private String id;
	@Element
	private String idVaga;
	@Element
	private String endereco;
	@Element
	private String latitude;
	@Element
	private String longitude;
	@Element
	private String precoHora;
	@Element(required = false)
	private String statusVaga;
	@Element(required = false)
	private String dataHoraOcupacao;
	@Element(required = false)
	private String placaVeiculoOcupacao;
	@Element(required = false)
	private String usuarioEstacioneAki;
	@Element(required = false)
	private String tempoOcupacao;
	@Element(required = false)
	private String valorAPagar;
	@Element(required = false)
	private String valorPago;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdVaga() {
		return idVaga;
	}
	public void setIdVaga(String idVaga) {
		this.idVaga = idVaga;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
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
	public String getPrecoHora() {
		return precoHora;
	}
	public void setPrecoHora(String precoHora) {
		this.precoHora = precoHora;
	}
	public String getStatusVaga() {
		return statusVaga;
	}
	public void setStatusVaga(String statusVaga) {
		this.statusVaga = statusVaga;
	}
	public String getDataHoraOcupacao() {
		return dataHoraOcupacao;
	}
	public void setDataHoraOcupacao(String dataHoraOcupacao) {
		this.dataHoraOcupacao = dataHoraOcupacao;
	}
	public String getPlacaVeiculoOcupacao() {
		return placaVeiculoOcupacao;
	}
	public void setPlacaVeiculoOcupacao(String placaVeiculoOcupacao) {
		this.placaVeiculoOcupacao = placaVeiculoOcupacao;
	}
	
	
}
