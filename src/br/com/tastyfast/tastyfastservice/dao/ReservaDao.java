package br.com.tastyfast.tastyfastservice.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Query;

import br.com.tastyfast.tastyfastservice.model.Cliente;
import br.com.tastyfast.tastyfastservice.model.Reserva;
import br.com.tastyfast.tastyfastservice.model.Restaurante;

public class ReservaDao {
	
	private final EntityManagerFactory factory;
	private final EntityManager manager;
	private Query query;
	
	public ReservaDao() {
		factory = Persistence.createEntityManagerFactory("tastyfastservice");
		manager = factory.createEntityManager();
	}
	
	public void salvar(Reserva reserva){
		try{
			manager.getTransaction().begin();
			manager.persist(reserva);
			manager.getTransaction().commit();
			System.out.println("Reserva Gravada!");
		} catch(Exception ex){
			System.out.println("Problemas ao gravar a reserva!\n" + ex.getMessage());
		}
		
	}
	
	public void alterar(Reserva reserva){
		try{
			manager.getTransaction().begin();
			manager.merge(reserva);
			manager.getTransaction().commit();
			System.out.println("Alterou a reserva...");
			
		} catch(Exception ex){
			System.out.println("Problemas ao alterar dados...\n" + ex.getMessage());
		}
	}
	
	public List<Reserva> findAll(){
		try{
			return manager.createQuery("from Reserva where status = 'Aguardando'").getResultList();
		} catch(Exception ex){
			System.out.println("Problemas ao listar dados...\n" + ex.getMessage());
			return null;
		}
	}
	
	public List<Reserva> findReservaByRestauranteCode(Restaurante restaurante){
		try{
			query = (Query) manager.createQuery("from Reserva where restaurante_idRestaurante = :param1");
			query.setInteger("param1", restaurante.getIdRestaurante());
			List<Reserva> lst = new ArrayList<>();
			lst = query.getResultList();
			return lst;
		} catch(Exception ex){
			System.out.println("Problemas ao localizar reservas! (ReservaDao)");
			return null;
		}
	}
	
	public List<Reserva> findReservaByClienteCode(Cliente cliente){
		try{
			query = (Query) manager.createQuery("from Reserva where cliente_idCliente = :param1");
			query.setInteger("param1", cliente.getIdCliente());
			List<Reserva> lst = new ArrayList<>();
			lst = query.getResultList();
			return lst;
		} catch(Exception ex){
			System.out.println("Problemas ao localizar reservas!");
			return null;
		}
	}

}
