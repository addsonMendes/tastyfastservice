package br.com.tastyfast.tastyfastservice.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Restaurante implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idRestaurante;
	private String nome;
	private String email;
	private String senha;
	private String tipoCulinaria;
	private String horarioFuncionamento;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Endereco endereco;
	
	@OrderBy("horario")
	@OneToMany(cascade = CascadeType.ALL)
	private List<Horario> horarios;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Cardapio> cardapios;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Mesa> mesas;
	
	public Restaurante() {
	}

	public Restaurante(Integer idRestaurante, String nome, String email, String senha) {
		super();
		this.idRestaurante = idRestaurante;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}


	public Integer getIdRestaurante() {
		return idRestaurante;
	}

	public void setIdRestaurante(Integer idRestaurante) {
		this.idRestaurante = idRestaurante;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTipoCulinaria() {
		return tipoCulinaria;
	}

	public void setTipoCulinaria(String tipoCulinaria) {
		this.tipoCulinaria = tipoCulinaria;
	}

	public String getHorarioFuncionamento() {
		return horarioFuncionamento;
	}

	public void setHorarioFuncionamento(String horarioFuncionamento) {
		this.horarioFuncionamento = horarioFuncionamento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Horario> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<Horario> horarios) {
		this.horarios = horarios;
	}
	
	public List<Cardapio> getCardapios() {
		return cardapios;
	}

	public void setCardapios(List<Cardapio> cardapios) {
		this.cardapios = cardapios;
	}
	
	public List<Mesa> getMesas() {
		return mesas;
	}

	public void setMesas(List<Mesa> mesas) {
		this.mesas = mesas;
	}

	public void addHorario(Horario horario){
		if(horarios == null){
			horarios = new ArrayList<Horario>();
		}
		horarios.add(horario);
	}
	
	public void addCardapio(Cardapio cardapio){
		if(cardapios == null){
			cardapios = new ArrayList<Cardapio>();
		}
		cardapios.add(cardapio);
	}
	
	public void addMesa(Mesa mesa){
		if(mesas == null){
			mesas = new ArrayList<Mesa>();
		}
		mesas.add(mesa);
	}

	@Override
	public String toString() {
		return "Restaurante [idRestaurante=" + idRestaurante + ", nome=" + nome + ", email=" + email + ", senha="
				+ senha + ", endereco=" + endereco + ", horarios=" + horarios + ", cardapios=" + cardapios + "]";
	}

}
