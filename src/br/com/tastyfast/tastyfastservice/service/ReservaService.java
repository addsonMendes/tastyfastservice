package br.com.tastyfast.tastyfastservice.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

import br.com.tastyfast.tastyfastservice.dao.ClienteDao;
import br.com.tastyfast.tastyfastservice.dao.ReservaDao;
import br.com.tastyfast.tastyfastservice.firebase.EnviaNotificacaoPush;
import br.com.tastyfast.tastyfastservice.model.Cliente;
import br.com.tastyfast.tastyfastservice.model.Reserva;
import br.com.tastyfast.tastyfastservice.model.Restaurante;

@Path("/reserva")
public class ReservaService {

	// Token note 8
	//private final String userDeviceIdKey = "eVU2UMPfndA:APA91bGwHmi91Xh0M5GmyPinlo-6Ledy4hBtZujLEgHPtg5liItaAvsLoMWxGHJemD5PcxOBWSOUl0JujpstZCx7K24McIR2z-aMJhpPcBxsNaSPLxm_U7nqRbP2eRUVwxV36oWQdoVq";

	//Token Emulador 6.0
	//private final String userDeviceIdKey = "dvvilcvEiDM:APA91bHzgmQAgmvXhEFy_bAIRFVXVJr0hItoCb5gmmBUTHc5xQ_LQL716_MOoh3kV2WmkudUko02Y5pyFBoSHA4Li62tbfTrTXcrYOVRbJGuPFXx6gy1GQZ4Ym3I7Mu8IeE13jey8XJX";
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public String cadastrar(Reserva reserva) throws Exception{
		try{
			new ReservaDao().salvar(reserva);
			return new Gson().toJson(reserva);
		} catch(Exception ex){
			System.out.println("problemas ao gravar...\n" + ex.getMessage());
			return "Problemas ao gravar reserva...\n" + ex.getMessage();
		}
	}
	
	@GET
	@Produces("application/json")
	public String listar() throws Exception{
		try{
			List<Reserva> reservas = new ArrayList<>();
			reservas = new ReservaDao().findAll();
			return new Gson().toJson(reservas);
		} catch(Exception ex){
			return "Problemas ao recuperar via webservice...\n" + ex.getMessage();
		}
	}
	
	@GET
	@Path("/{idRestaurante}")
	@Produces("application/json")
	public String findByCode(@PathParam("idRestaurante") String idRestaurante) throws Exception{
		try{
			Restaurante restaurante = new Restaurante();
			restaurante.setIdRestaurante(new Integer(idRestaurante));
			List<Reserva> reservas = new ArrayList<>();
			reservas = new ReservaDao().findReservaByRestauranteCode(restaurante);
			return new Gson().toJson(reservas);
		} catch(Exception ex){
			return "Problemas ao listar reservas...\n" + ex.getMessage();
		}
	}
	
	@GET
	@Path("/historico/{idCliente}")
	@Produces("application/json")
	public String findreservasCliente(@PathParam("idCliente") String idCliente) throws Exception{
		try{
			Cliente cliente = new Cliente();
			cliente.setIdCliente(new Integer(idCliente));
			List<Reserva> reservas = new ArrayList<>();
			reservas = new ReservaDao().findReservaByClienteCode(cliente);
			return new Gson().toJson(reservas);
		} catch(Exception ex){
			ex.printStackTrace();
			return "Problemas ao gerar histórico...\n" + ex.getMessage();
		}
	}
	
	
	@PUT
	@Consumes("application/json")
	@Produces("text/plain")
	public String alterar(Reserva reserva) throws Exception{
		try{
			new ReservaDao().alterar(reserva);
			switch(reserva.getStatus()){
				case Confirmada:
					EnviaNotificacaoPush.pushFCMNotification("A sua reserva no restaurante " + reserva.getRestaurante().getNome() + " foi confirmada!", reserva.getCliente().getTokenAparelho());
					break;
				case Cancelada:
					EnviaNotificacaoPush.pushFCMNotification("A sua reserva no restaurante " + reserva.getRestaurante().getNome() + " foi cancelada! Entre em contato com o restaurante para maiores detalhes!", reserva.getCliente().getTokenAparelho());
					break;
				default:
					return "Status inválido para alteração!";
			}
			return "Status alterado com sucesso!";
		} catch(Exception ex){
			System.out.println("Problemas ao alterar:\n" + ex.getMessage());
			return "Problemas ao alterar dados!\n" + ex.getMessage();
		}
	}
	
	@GET
	@Path("/notificacoes/{idRestaurante}")
	@Consumes("application/json")
	@Produces("application/json")
	public String emiteNotificacoesEmMassa(@PathParam("idRestaurante") String idRestaurante, String mensagem) throws Exception{
		try{
			List<Cliente> clientes = new ClienteDao().findListagemNotificacao(idRestaurante);
			for(Cliente c: clientes){
				EnviaNotificacaoPush.pushFCMNotification(mensagem, c.getTokenAparelho());
			}
			return "Notificações enviadas com sucesso!";
		} catch(Exception ex){
			ex.printStackTrace();
			return "Problemas ao enviar notificações...\n" + ex.getMessage();
		}
	}
	
}
