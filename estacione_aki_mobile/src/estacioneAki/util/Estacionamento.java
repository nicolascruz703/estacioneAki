package estacioneAki.util;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="estacionamento")
public class Estacionamento {
	@Element
    public String nome;
	@Element
    public String endereco;
	@Element
    public String cnpj;
	@Element
    public String latitude;
	@Element
    public String longitude;
	@Element
    public String numeroDeVagas;
	@Element
    public String precoHora; 
}
