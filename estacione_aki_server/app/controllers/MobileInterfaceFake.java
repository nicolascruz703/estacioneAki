package controllers;

import play.mvc.Controller;

import java.util.ArrayList;

import models.*;

import com.thoughtworks.xstream.XStream;

public class MobileInterfaceFake extends Controller {

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
		// Se positivo: registrar a reserva para o cpf correspondente
		// 				retornar ao celular a posição de todos os estacionamentos
		// Se negativo: retornar mensagem de Indisponibilidade
		
		if (cnpj.contentEquals("630255300008801")) {

			// situação em que não há vaga livre
			Retorno retReservaVaga = new Retorno();
			
			retReservaVaga.mensagem = "Nos desculpem... Estacionamento sem vaga disponível no momento.";
				
			XStream xstream = new XStream();
			xstream.alias("retorno", models.Retorno.class);

			renderXml(retReservaVaga, xstream);
		}
		else
			listaEstacionamento();
	}
	
	public static void cancelaReservaVaga(String cpf) {

		// procurar o CNPJ do estacionamento reservado pelo CPF informado 
		// Cancelar a reserva da vaga
		// retornar ao celular a posição de vagas livres atualizada
		
		listaEstacionamento();
		
	}
}

