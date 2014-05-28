package estacioneAki.servico;

import java.util.concurrent.ExecutionException;

import estacioneAki.util.EstacionamentoList; 

public class ConexaoServidor {
  	
	//chamada do servi�o de Reserva	Vaga
	public String reservarVaga(String cnpj,String cpf)throws InterruptedException, ExecutionException{
		String UrlWSReserva = URLs.RESERVAR_VAGA;
		UrlWSReserva = UrlWSReserva+"cnpj="+cnpj+"&cpf="+cpf;
		
		return new getRespostaFromWSReserva().execute(UrlWSReserva).get();
	}
	
	//chamada do servi�o de Cancelar Reserva vaga
	public String cancelarVaga(String cpf)throws InterruptedException, ExecutionException{
		String UrlWSCancelar = URLs.CANCELAR_VAGA;
		UrlWSCancelar = UrlWSCancelar+"cpf="+cpf;
		return new getRespostaFromWSCancelaReserva().execute(UrlWSCancelar).get();
	}
	
	//chamada do servi�o de listaEstacionamentos
	public EstacionamentoList listaEstacionamentos() throws InterruptedException, ExecutionException{		
		String UrlListaestacionamentos = URLs.LISTA_ESTACIONAMENTOS;		
		return new getListEstacionamentosFromXML().execute(UrlListaestacionamentos).get();		
	}

}

