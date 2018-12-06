package br.com.tastyfast.tastyfastservice.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Query;

import br.com.tastyfast.tastyfastservice.config.MD5;
import br.com.tastyfast.tastyfastservice.model.Cliente;
import br.com.tastyfast.tastyfastservice.model.Restaurante;

public class ClienteDao {
	
	private final EntityManagerFactory factory;
	private final EntityManager manager;
	private Query query;
	
	public ClienteDao() {
		factory = Persistence.createEntityManagerFactory("tastyfastservice");
		manager = factory.createEntityManager();
	}
	
	public void salvar(Cliente cliente){
		try{
			manager.getTransaction().begin();
			cliente.setSenha(new MD5().criptografa(cliente.getSenha()));
			manager.persist(cliente);
			manager.getTransaction().commit();
			System.out.println("Cliente gravado com sucesso!");
		} catch(Exception ex){
			System.out.println("Problemas ao gravar cliente!\n" + ex.getMessage());
		}
	}
	
	public void alterar(Cliente cliente){
		try{
			manager.getTransaction().begin();
			manager.merge(cliente);
			manager.getTransaction().commit();
			System.out.println("Alterou o cliente...");
		} catch(Exception ex){
			System.out.println("Problemas ao alterar dados...\n" + ex.getMessage());
		}
	}
	
	public List<Cliente> findAll(){
		try{
			return manager.createQuery("from Cliente").getResultList();
		} catch(Exception ex){
			System.out.println("Problemas ao listar clientes...\n" + ex.getMessage());
			return null;
		}
	}
	
	public Cliente login(Cliente usuarioApp){
		try{
			query = (Query) manager.createQuery("from Cliente where email = :param1 and senha = :param2");
			query.setString("param1", usuarioApp.getEmail());
			query.setString("param2", usuarioApp.getSenha());
			Cliente clienteLogado = (Cliente) query.uniqueResult();
			return clienteLogado;
		} catch(Exception ex){
			System.out.println("Problemas ao localizar usuários(Clientes)!");
			return null;
		}
	}
	
	public List<Object> findByRestaurante(String idRestaurante){
		try{
			
			query = (Query) manager.createQuery("select c.nome, c.email," +
					   " r.horario from Cliente c" +
					   " inner join Reserva r on c.idCliente = r.cliente.idCliente" +
					   " inner join Restaurante re on r.restaurante.idRestaurante = re.idRestaurante" +
					   " where idRestaurante = :param1"); 
			query.setString("param1", idRestaurante);
			return query.getResultList();
						
		} catch(Exception ex){
			System.out.println("Problemas ao listar clientes...\n" + ex.getMessage());
			return null;
		}
	}
	
	public List<Cliente> findListagemNotificacao(String idRestaurante){
		try{
			
			query = (Query) manager.createQuery("select c.nome,c.tokenAparelho from Cliente c" +
					   " inner join Reserva r on c.idCliente = r.cliente.idCliente" +
					   " inner join Restaurante re on r.restaurante.idRestaurante = re.idRestaurante" +
					   " where idRestaurante = :param1" + 
					   " group by c.nome, c.tokenAparelho"); 
			query.setString("param1", idRestaurante);
			return query.getResultList();
						
		} catch(Exception ex){
			System.out.println("Problemas ao montar lista de clientes para envio de notificações...\n" + ex.getMessage());
			return null;
		}
	}
}
