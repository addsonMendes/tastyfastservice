package br.com.tastyfast.tastyfastservice.config;

import javax.persistence.Persistence;

import br.com.tastyfast.tastyfastservice.dao.ClienteDao;
import br.com.tastyfast.tastyfastservice.dao.ReservaDao;
import br.com.tastyfast.tastyfastservice.dao.RestauranteDao;
import br.com.tastyfast.tastyfastservice.model.Cardapio;
import br.com.tastyfast.tastyfastservice.model.Cliente;
import br.com.tastyfast.tastyfastservice.model.Endereco;
import br.com.tastyfast.tastyfastservice.model.Horario;
import br.com.tastyfast.tastyfastservice.model.Mesa;
import br.com.tastyfast.tastyfastservice.model.Reserva;
import br.com.tastyfast.tastyfastservice.model.Restaurante;

public class Main {

	public static void main(String[] args) {
		Persistence.createEntityManagerFactory("tastyfastservice");
		System.out.println("Tabelas Criadas!");
		
		Main principal = new Main();
	
		//principal.preencheTabela();
		//principal.fazLogin();
	}
	
	public void fazLogin(){
		ClienteDao cd = new ClienteDao();
		Cliente cliente = new Cliente();
		
		cliente.setNome("Addson");
		cliente.setEmail("add@gmail.com");
		
		cd.login(cliente);
	}
	
	public void preencheTabela(){
		RestauranteDao r = new RestauranteDao();
		ClienteDao cd = new ClienteDao();
		ReservaDao rd = new ReservaDao();
		
		Restaurante restaurante = new Restaurante();
		Cliente cliente = new Cliente();
		Reserva reserva = new Reserva();
		Mesa mesa = new Mesa();
		Endereco endereco = new Endereco();
		Cardapio cardapio = new Cardapio();
		Horario horario = new Horario();
		
		restaurante.setNome("Bobo Bar");
		restaurante.setEmail("bobo@gmail.com");
		restaurante.setSenha("123");
		
		endereco.setCep("20771410");
		endereco.setLogradouro("Rua Cirne Maia 53");
		restaurante.setEndereco(endereco);
		
		cardapio.setPrato("Mini Hamburguer");
		cardapio.setValor(new Double(25));
		restaurante.addCardapio(cardapio);
		
		horario.setHorario("19:30");
		restaurante.addHorario(horario);
		
		// Cadastro da mesa
		mesa.setMesa("Mesa 1");
		mesa.setQtdPessoas(4);
		restaurante.addMesa(mesa);
		
		r.salvar(restaurante);
		
		// Cadastro do cliente
		cliente.setNome("Addson Mendes");
		cliente.setEmail("add@gmail.com");
		cd.salvar(cliente);
		
		// Cadastro da Reserva
		reserva.setCliente(cliente);
		reserva.setMesa(mesa);
		reserva.setRestaurante(restaurante);
		reserva.setHorario(horario.getHorario());
		rd.salvar(reserva);
		
		System.out.println(r.findAll());
	}
}
