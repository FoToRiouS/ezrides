package br.ueg.ezrides.model.entitys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.vexillum.model.CommonEntityActivated;
import br.com.vexillum.model.annotations.Validate;

/**
 * Classe que representa o modelo das denúncias feitas pelo usuário no sistema.
 * @author Fernando
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "abuse")
public class Abuse extends CommonEntityActivated {

	/**
	 * Construtor padrão
	 */
	public Abuse() {
		abuseDate = new Date();
	}
	
	/**
	 * Atributo que representa a ligação com uma rota.
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_route", unique=false, nullable=false, updatable=false)
	private Route route;
	
	/**
	 * Motivo da denúncia.
	 */
	@Validate(notNull = true)
	@Column(name="reason", unique=false, nullable=false, updatable=true, length=1000)
	private String reason;
	
	/**
	 * Data em que a denúncia foi feita.
	 */
	@Validate(notNull = true)
	@Column(name="abuse_date", unique=false, nullable=false, updatable=true)
	private Date abuseDate;

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getAbuseDate() {
		return abuseDate;
	}

	public void setAbuseDate(Date abuseDate) {
		this.abuseDate = abuseDate;
	}
	
}
