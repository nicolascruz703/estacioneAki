package controllers;

import play.mvc.Controller;

import java.util.ArrayList;

import models.Estacionamento;

import com.thoughtworks.xstream.XStream;

public class MobileInterface extends Controller {

	
	public static void listaEstacionamento() {
		
		Estacionamento estacionamento1 = new Estacionamento();
		Estacionamento estacionamento2 = new Estacionamento();
	
		estacionamento1.nome = "Estacionamento do IME";
		estacionamento1.endereco = "Rua do Matão, 1010";
		estacionamento1.cnpj = "63025530000104";
		estacionamento1.precoHora = 1;
		estacionamento1.numeroDeVagas = 1;
		estacionamento1.latitude = "-23.558836";
		estacionamento1.longitude = "-46.731498";
		
		estacionamento2.nome = "Estacionamento da Poli";
		estacionamento2.endereco = "Avenida Prof Luciano Gualberto, 380";
		estacionamento2.cnpj = "630255300008801";
		estacionamento2.precoHora = 2;
		estacionamento2.numeroDeVagas = 0;
		estacionamento2.latitude = "-23.559570";
		estacionamento2.longitude = "-46.731741";
		
		ArrayList <Estacionamento> listaEstacionamento = new ArrayList <Estacionamento>();
		listaEstacionamento.add (estacionamento1);
		listaEstacionamento.add (estacionamento2);
		
		XStream xstream = new XStream();
		xstream.alias("estacionamento", models.Estacionamento.class);
		
		renderXml(listaEstacionamento, xstream);
		
	}
	
	public static void reservaVaga(String cnpj, String cpf) {
		
		// procurar o estacionamento na base de dados correspondente ao CNPJ informado 
		// confirma se há vaga livre
		// registrar a reserva para o cpf correspondente
		// retornar ao celular a posição de vagas livres atualizada
				
		Estacionamento estacionamentoReservado = new Estacionamento();

		estacionamentoReservado.nome = "Estacionamento do IME";
		estacionamentoReservado.endereco = "Rua do Matão, 1010";
		estacionamentoReservado.cnpj = "63025530000104";
		estacionamentoReservado.precoHora = 1;
		estacionamentoReservado.numeroDeVagas = 0;
		estacionamentoReservado.latitude = "-23.558836";
		estacionamentoReservado.longitude = "-46.731498";

		XStream xstream = new XStream();
		xstream.alias("estacionamento", models.Estacionamento.class);
		
		renderXml(estacionamentoReservado, xstream);
	}
	
	public static void cancelaReservaVaga(String cpf) {

		// procurar o CNPJ do estacionamento reservado pelo CPF informado 
		// Cancelar a reserva da vaga
		// retornar ao celular a posição de vagas livres atualizada
		
		Estacionamento estacionamentoCancelado = new Estacionamento();

		estacionamentoCancelado.nome = "Estacionamento do IME";
		estacionamentoCancelado.endereco = "Rua do Matão, 1010";
		estacionamentoCancelado.cnpj = "63025530000104";
		estacionamentoCancelado.precoHora = 1;
		estacionamentoCancelado.numeroDeVagas = 1;
		estacionamentoCancelado.latitude = "-23.558836";
		estacionamentoCancelado.longitude = "-46.731498";		
		
		XStream xstream = new XStream();
		xstream.alias("estacionamento", models.Estacionamento.class);
		
		renderXml(estacionamentoCancelado, xstream);
	}
}

