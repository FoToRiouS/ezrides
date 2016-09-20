package br.ueg.ezrides.model.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.vexillum.model.CommonEntity;
import br.com.vexillum.model.annotations.SearchField;
import br.com.vexillum.model.annotations.ValidatorClass;

/**
 * Classe que representa os países no sistema.
 * @author Fernando
 */
@SuppressWarnings("serial")
@ValidatorClass
@Entity
@Table(name="pais")
public class Country extends CommonEntity {

	/**
	 * Nome do país.
	 */
	@SearchField
	@Column(name="nome", unique=true, nullable=false, updatable=true, length=200)
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String toString(){
		return this.getNome();
	}
	
}
