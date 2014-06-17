package estacioneAki.util;

import java.util.Iterator;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class EstacionamentoList {
	@ElementList(entry = "estacionamento", inline = true)
	public List<Estacionamento> estacionamentoList;
	
	public Estacionamento getPorCnpj(String cnpj){
		Iterator<Estacionamento> i = estacionamentoList.iterator();
				
		while(i.hasNext()){
			Estacionamento e = (Estacionamento) i.next();
			if(e.getCnpj().equals(cnpj)){
				return e;
			}
		}
		return null;
	}
	

	
	
}