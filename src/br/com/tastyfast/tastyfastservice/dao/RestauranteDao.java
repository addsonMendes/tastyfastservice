package br.com.tastyfast.tastyfastservice.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Query;

import br.com.tastyfast.tastyfastservice.config.MD5;
import br.com.tastyfast.tastyfastservice.model.Restaurante;

public class RestauranteDao {
	
	private final EntityManagerFactory factory;
	private final EntityManager manager;
	private Query query;
	
	
	public RestauranteDao() {
		factory = Persistence.createEntityManagerFactory("tastyfastservice");
		manager = factory.createEntityManager();
	}
	
	public void salvar(Restaurante restaurante){
		try{
			manager.getTransaction().begin();
			restaurante.setSenha(new MD5().criptografa(restaurante.getSenha()) );
			manager.persist(restaurante);
			manager.getTransaction().commit();
			System.out.println("Dados Gravados!");
		} catch(Exception ex){
			System.out.println("Problemas ao gravar!\n" + ex.getMessage());
		}
		
	}
	
	public List<Restaurante> findAll(){
		return manager.createQuery("from Restaurante").getResultList();
	}
	
	public void alterar(Restaurante restaurante){
		try{
			manager.getTransaction().begin();
			manager.merge(restaurante);
			manager.getTransaction().commit();
			System.out.println("Alterou o restaurante...");
		} catch(Exception ex){
			System.out.println("Problemas ao alterar dados...\n" + ex.getMessage());
		}
	}
	
	public Restaurante login(Restaurante usuarioAdm){
		try{
			query = (Query) manager.createQuery("from Restaurante where email = :param1 and senha = :param2");
			query.setString("param1", usuarioAdm.getEmail());
			query.setString("param2", usuarioAdm.getSenha());
			Restaurante restauranteLogado = (Restaurante) query.uniqueResult();
			//System.out.println(restauranteLogado);
			return restauranteLogado;
		} catch(Exception ex){
			System.out.println("Problemas ao localizar usuário(Restaurante)!");
			return null;
		}
	}

}
