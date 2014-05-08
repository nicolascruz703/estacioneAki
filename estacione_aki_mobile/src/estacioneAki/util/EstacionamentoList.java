package estacioneAki.util;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class EstacionamentoList {
	@ElementList(entry = "estacionamento", inline = true)
	public List<Estacionamento> estaciomentoList;
	

	
	
}