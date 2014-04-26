package controllers;

import play.mvc.Controller;
import java.util.ArrayList;

import models.Estacionamento;

public class MobileInterface extends Controller {

	public static void estacionamento_list() {
		
		Estacionamento estacionamento1 = new Estacionamento();
		Estacionamento estacionamento2 = new Estacionamento();
	
		estacionamento1.nome = "Estacionamento da EACH";
		estacionamento1.endereco = "Rua Arlindo Bettio, 1000";
		estacionamento1.cnpj = "63025530000104";
		estacionamento1.precoHora = 1;
		estacionamento1.numeroDeVagas = 100;
		estacionamento1.latitude = "-23.484468";
		estacionamento1.longitude = "-46.499891";
	
		estacionamento2.nome = "Estacionamento da Poli";
		estacionamento2.endereco = "Avenida Prof Luciano Gualberto, 380";
		estacionamento2.cnpj = "630255300008801";
		estacionamento2.precoHora = 2;
		estacionamento2.numeroDeVagas = 20;
		estacionamento2.latitude = "-23.559570";
		estacionamento2.longitude = "-46.731741";
		
		ArrayList <Estacionamento> estacionamento_list = new ArrayList <Estacionamento>();
		estacionamento_list.add (estacionamento1);
		estacionamento_list.add (estacionamento2);

		renderXml(estacionamento_list);
		
	}
}
