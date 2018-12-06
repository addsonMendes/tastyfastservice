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

import br.com.tastyfast.tastyfastservice.config.MD5;
import br.com.tastyfast.tastyfastservice.dao.RestauranteDao;
import br.com.tastyfast.tastyfastservice.model.Restaurante;

@Path("/restaurante")
public class RestauranteService {
	
	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public String cadastrar(Restaurante restaurante) throws Exception{
		try{
			new RestauranteDao().salvar(restaurante);
			return "Dados gravados com sucesso!";
		} catch(Exception ex){
			System.out.println("Problemas ao gravar:\n" + ex.getMessage());
			return "Problemas ao gravar dados!\n" + ex.getMessage();
		}
	}
	
	@PUT
	@Consumes("application/json")
	@Produces("text/plain")
	public String alterar(Restaurante restaurante) throws Exception{
		try{
			new RestauranteDao().alterar(restaurante);
			return "Operação realizada com sucesso!";
		} catch(Exception ex){
			System.out.println("Problemas ao alterar:\n" + ex.getMessage());
			return "Problemas ao realizar operação!\n" + ex.getMessage();
		}
	}

	@GET
	@Produces("application/json")
	public String listar() throws Exception{
		try{
			List<Restaurante> restaurantes = new ArrayList<>();
			restaurantes = new RestauranteDao().findAll();
			return new Gson().toJson(restaurantes);
		} catch(Exception ex){
			return "Problemas ao recuperar dados!\n" + ex.getMessage();
		}
	}
	
	@GET
	@Path("/{email}/{senha}")
	@Produces("application/json")
	public String login(@PathParam("email") String email, @PathParam("senha") String senha) throws Exception{
		try{
			Restaurante usuarioAdm = new Restaurante();
			usuarioAdm.setEmail(email);
			usuarioAdm.setSenha(senha);
			Restaurante logado = new RestauranteDao().login(usuarioAdm);
			return new Gson().toJson(logado);
		} catch(Exception ex){
			return "Problemas ao realizar login...\n" + ex.getMessage();
		}
	}
	
}
