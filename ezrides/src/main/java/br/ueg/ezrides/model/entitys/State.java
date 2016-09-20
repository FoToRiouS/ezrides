package br.ueg.ezrides.model.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.vexillum.model.CommonEntity;
import br.com.vexillum.model.annotations.SearchField;
import br.com.vexillum.model.annotations.ValidatorClass;

/**
 * Representa os estados que serão usados no sistema.
 * @author Fernando
 *
 */
@SuppressWarnings("serial")
@ValidatorClass
@Entity
@Table(name="estados")
public class State extends CommonEntity {

	/**
	 * Representa em que país o estado está associado.
	 * @see Country
	 */
	@SearchField
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_pais", unique=false, nullable=false, updatable=true)
	private Country pais;
	
	/**
	 * Nome do estado.
	 */
	@SearchField
	@Column(name="nome", unique=false, nullable=false, updatable=true, length=200)
	private String nome;
	
	/**
	 * Sigla que representa o estado.
	 */
	@SearchField
	@Column(name="sigla", unique=false, nullable=false, updatable=true, length=20)
	private String sigla;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Country getPais() {
		return pais;
	}

	public void setPais(Country pais) {
		this.pais = pais;
	}
	
	public String toString(){
		return this.getSigla();
	}
	
}
