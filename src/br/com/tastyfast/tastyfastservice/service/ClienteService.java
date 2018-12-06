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
import br.com.tastyfast.tastyfastservice.model.Cliente;
import br.com.tastyfast.tastyfastservice.model.Restaurante;

@Path("/cliente")
public class ClienteService {

	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public String cadastrar(Cliente cliente) throws Exception{
		try{
			new ClienteDao().salvar(cliente);
			return new Gson().toJson(cliente);
		} catch(Exception ex){
			System.out.println("Problemas ao cadastrar cliente...\n" + ex.getMessage());
			return "Problemas ao cadastrar cliente!\n" + ex.getMessage();
		}
	}
	
	@PUT
	@Consumes("application/json")
	@Produces("text/plain")
	public String alterar(Cliente cliente) throws Exception{
		try{
			new ClienteDao().alterar(cliente);
			System.out.println("Cliente alterado com sucesso!");
			return new Gson().toJson(cliente);
		} catch(Exception ex){
			System.out.println("Problemas ao cadastrar cliente...\n" + ex.getMessage());
			return "Problemas ao cadastrar cliente!\n" + ex.getMessage();
		}
	}
	
	@GET
	@Produces("application/json")
	public String listar() throws Exception{
		try{
			List<Cliente> clientes = new ArrayList<>();
			clientes = new ClienteDao().findAll();
			return new Gson().toJson(clientes);
		} catch(Exception ex){
			return "Problemas ao listar clientes via webservice...\n" + ex.getMessage();
		}
	}
	
	@GET
	@Path("/{email}/{senha}")
	@Produces("application/json")
	public String login(@PathParam("email") String email, @PathParam("senha") String senha) throws Exception{
		try{
			Cliente usuarioApp = new Cliente();
			usuarioApp.setEmail(email);
			usuarioApp.setSenha(senha);
			Cliente logado = new ClienteDao().login(usuarioApp);
			return new Gson().toJson(logado);
		} catch(Exception ex){
			return "Problemas ao realizar login...\n" + ex.getMessage();
		}
	}
	
	@Path("/dispositivo/cliente")
	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public String gravaTokenUsuario(Cliente cliente) throws Exception{
		try{
			List<Cliente> clientes = new ArrayList<>();
			clientes = new ClienteDao().findAll();
			for(int i = 0; i < clientes.size(); i++){
				if(cliente.getIdCliente() == clientes.get(i).getIdCliente()){
					new ClienteDao().alterar(cliente);
				} else {
					new ClienteDao().salvar(cliente);
				}
			}
			return new Gson().toJson(cliente);
			
		} catch(Exception ex){
			System.out.println("Problemas ao cadastrar cliente...\n" + ex.getMessage());
			return "Problemas ao cadastrar cliente!\n" + ex.getMessage();
		}
	}
	
	@Path("/relatorio/{idRestaurante}")
	@GET
	@Produces("application/json")
	public String listarClientes(@PathParam("idRestaurante") String idRestaurante) throws Exception{
		try{
			List<Object> clientes = new ArrayList<>();
			clientes = new ClienteDao().findByRestaurante(idRestaurante);
			return new Gson().toJson(clientes);
		} catch(Exception ex){
			return "Problemas ao listar clientes via webservice...\n" + ex.getMessage();
		}
	} 

}
