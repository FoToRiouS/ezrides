package br.ueg.ezrides.model.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.vexillum.model.CommonEntity;
import br.ueg.ezrides.model.enums.DaysOfWeek;

/**
 * Classe intermediária, responsável por fazer a ligação entre uma rota e o dia da semana.
 * @author Fernando
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="route_daysofweek")
public class RouteDaysOfWeek extends CommonEntity {

	/**
	 * Rota a qual está ligada.
	 * @see Route
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_route", unique=false, nullable=false, updatable=false)
	private Route route;
	
	/**
	 * Dia da semana que a rota está ligada.
	 * @see	DaysOfWeek
	 */
	@Column(name = "day", unique = false, nullable = false, updatable = false, length = 50)
	@Enumerated(EnumType.STRING)
	private DaysOfWeek day;

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public DaysOfWeek getDay() {
		return day;
	}

	public void setDay(DaysOfWeek day) {
		this.day = day;
	}
	
	public boolean equals(DaysOfWeek day) {
		if(this.day.equals(day)){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean equals(RouteDaysOfWeek day) {
		if(this.day.equals(day.getDay())){
			return true;
		} else {
			return super.equals(day);
		}
	}
	
}
