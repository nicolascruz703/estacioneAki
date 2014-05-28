package estacioneAki.util;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="retorno")
public class Retorno {
	@Element(name = "mensagem")
    private String mensagem;
	
	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
