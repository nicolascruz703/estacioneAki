package estacioneAki.servico;

public interface URLs {
	
	public String LISTA_ESTACIONAMENTOS = "http://54.207.83.104:8080/MobileInterface/listaEstacionamento";
	public String RESERVAR_VAGA = "http://54.207.83.104:8080/mobileinterface/reservaVaga?";
	public String CANCELAR_VAGA = "http://54.207.83.104:8080/mobileinterface/cancelaReservaVaga?";
	public String VERIFICA_RESERVA = "http://54.207.83.104:8080/mobileinterface/verificaReserva?cpf=";
	public String LOGIN_MOTORISTA = "http://54.207.83.104:8080/mobileinterface/loginMotorista?cpf=";

}
