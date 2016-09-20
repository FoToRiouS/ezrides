package br.ueg.ezrides.model.entitys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.vexillum.model.Category;
import br.com.vexillum.model.UserBasic;
import br.com.vexillum.model.annotations.SearchField;
import br.com.vexillum.model.annotations.Validate;
import br.com.vexillum.model.annotations.ValidatorClass;

/**
 * Representa um usuário do sistema.
 * @author Fernando
 *
 */
@SuppressWarnings("serial")
@ValidatorClass(validatorClass = "br.ueg.ezrides.control.validator.UserValidator")
@DiscriminatorValue("1")
@Entity
public class User extends UserBasic {
	
	/**
	 * Construtor padrão que inicia por padrão um usuário como desativado, sendo o perfil público habilitado por padrão e sua categoria sendo do tipo 'Usuário'
	 */
	public User(){
		setActive(false);
		this.perfilPublico = true;
		this.category = new Category(2l, "ROLE_USER");
	}

	/**
	 * Data/hora da última vez que o usuário se logou no sistema.
	 */
	@Column(name="ultimo_acesso", unique=false, nullable=true, updatable=true)
	private Date ultimoAcesso;
	
	/**
	 * Estado em que usuário reside, implicitamente possui o país.
	 * @see State
	 */
	@Validate(notNull=true)
	@SearchField
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_estado", unique=false, nullable=false, updatable=true)
	private State estado;
	
	/**
	 * Cidade de residência do usuário.
	 */
	@Validate(notNull=true, min=3, max=50)
	@SearchField
	@Column(name="cidade", unique=false, nullable=false, updatable=true, length=100)
	private String cidade;
	
	/**
	 * Endereço completo do usuário.
	 */
	@Validate(notNull=true, min=4, max=500)
	@SearchField
	@Column(name="endereco", unique=false, nullable=false, updatable=true, length=500)
	private String endereco;
	
	/**
	 * Número da residência do usuário.
	 */
	@Validate(notNull=true)
	@SearchField
	@Column(name="numero", unique=false, nullable=false, updatable=true, length=10)
	private String numero;
	
	/**
	 * Bairro de residência do usuário.
	 */
	@Validate(notNull=true, min=3, max=50)
	@SearchField
	@Column(name="bairro", unique=false, nullable=false, updatable=true, length=100)
	private String bairro;
	
	/**
	 * CEP de residência do usuário.
	 */
	@Validate(notNull=true, max=18)
	@SearchField
	@Column(name="cep", unique=false, nullable=false, updatable=true)
	private Long cep;
	
	/**
	 * Telefone do usuário.
	 */
	@Validate(notNull=true, max=18)
	@Column(name="telefone", unique=false, nullable=false, updatable=true)
	private Long telefone;
	
	/**
	 * Celular do usuário.
	 */
	@Validate(notNull=true, max=18)
	@Column(name="celular", unique=false, nullable=true, updatable=true)
	private Long celular;

	/**
	 * Flag que diz se o perfil do usuário é público ou privado.
	 */
	@Validate(notNull=true)
	@Column(name="perfil_publico", unique=false, nullable=false, updatable=true)
	private Boolean perfilPublico;
	
	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	public State getEstado() {
		return estado;
	}

	public void setEstado(State estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Long getCep() {
		return cep;
	}

	public void setCep(Long cep) {
		this.cep = cep;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public Long getCelular() {
		return celular;
	}

	public void setCelular(Long celular) {
		this.celular = celular;
	}

	public Boolean getPerfilPublico() {
		return perfilPublico;
	}

	public void setPerfilPublico(Boolean perfilPublico) {
		this.perfilPublico = perfilPublico;
	}

	@Override
	public String toString() {
		return getName();
	}
	
}
