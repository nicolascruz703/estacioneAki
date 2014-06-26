package estacioneAki.util;

import java.util.Iterator;
import java.util.List;

import org.simpleframework.xml.ElementList;

public class ParquimetroList {
	@ElementList(entry = "parquimetro", inline = true)
	public List<Parquimetro> parquimetroList;
}
