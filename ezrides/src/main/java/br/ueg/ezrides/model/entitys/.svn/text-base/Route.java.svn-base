package br.ueg.ezrides.model.entitys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import br.com.vexillum.model.CommonEntityActivated;
import br.com.vexillum.model.annotations.SearchField;
import br.com.vexillum.model.annotations.Validate;
import br.com.vexillum.model.annotations.ValidatorClass;
import br.ueg.ezrides.model.enums.DaysOfWeek;

/**
 * Classe que representa as rotas que o usuário cadastra.
 * @author Fernando
 *
 */
@SuppressWarnings("serial")
@ValidatorClass(validatorClass="br.ueg.ezrides.control.validator.RouteValidator")
@Entity
@Table(name="route")
public class Route extends CommonEntityActivated {

	/**
	 * Contrutor padrão, iniciando a lista de {@link DaysOfWeek}.
	 */
	public Route() {
		setActive(true);
		this.daysOfWeek = new ArrayList<>();
		this.excludeByAbuse = false;
		this.disponibility = true;
	}
	
	/**
	 * Representa o nome que o usuário dá para a rota.
	 */
	@SearchField
	@Validate(notNull=true)
	@Column(name="name", unique=false, nullable=false, updatable=true, length=50)
	private String name;
	
	/**
	 * Representa o ponto inicial da rota, podendo ser tanto um local descrito, quanto um CEP.
	 */
	@Validate(notNull=true)
	@Column(name="initial_point", unique=false, nullable=false, updatable=true, length=500)
	private String initialPoint;
	
	/**
	 * Representa o ponto final da rota, podendo ser tanto um local descrito, quanto um CEP.
	 */
	@SearchField
	@Validate(notNull=true)
	@Column(name="final_point", unique=false, nullable=false, updatable=true, length=500)
	private String finalPoint;
	
	/**
	 * Representa pontos referência do local, para facilitar sua localização.
	 */
	@SearchField
	@Column(name="reference_points", unique=false, nullable=true, updatable=true, length=500)
	private String referencePoints;
	
	/**
	 * Representa a rota de uma categoria.
	 * @see RouteCategory
	 */
	@SearchField
	@Validate(notNull=true)
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_category", unique=false, nullable=false, updatable=true)
	private RouteCategory category;
	
	/**
	 * Representa a data que a rota será feita, caso ela seja feita em apenas um dia.
	 * @see Date
	 */
	@Validate(past = true)
	@Column(name = "data_route", unique = false, nullable = true, updatable = true, length = 50)
	private Date dateRoute;
	
	/**
	 * Lista com os dias da semana que a rota é feita.
	 * @see RouteDaysOfWeek
	 */
	@OneToMany(fetch=FetchType.LAZY, mappedBy="route", targetEntity=RouteDaysOfWeek.class, orphanRemoval=true)
	@Cascade(value=CascadeType.ALL)
	private List<RouteDaysOfWeek> daysOfWeek;
	
	/**
	 * Usuário dono da rota.
	 * @see	User
	 */
	@SearchField
	@Validate(notNull=true)
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_user", unique=false, nullable=false, updatable=false)
	private User user;

	/**
	 * Informa se a rota foi excluida devido a uma denúncia.
	 */
	@SearchField
	@Column(name = "exclude_abuse", unique = false, nullable = false, updatable = true)
	private Boolean excludeByAbuse;
	
	/**
	 * Informa se a rota está disponível.
	 */
	@Column(name = "disponibility", unique = false, nullable = false, updatable = true)
	private Boolean disponibility;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInitialPoint() {
		return initialPoint;
	}

	public void setInitialPoint(String initialPoint) {
		this.initialPoint = initialPoint;
	}

	public String getFinalPoint() {
		return finalPoint;
	}

	public void setFinalPoint(String finalPoint) {
		this.finalPoint = finalPoint;
	}

	public String getReferencePoints() {
		return referencePoints;
	}

	public void setReferencePoints(String referencePoints) {
		this.referencePoints = referencePoints;
	}

	public RouteCategory getCategory() {
		return category;
	}

	public void setCategory(RouteCategory category) {
		this.category = category;
	}

	public Date getDateRoute() {
		return dateRoute;
	}

	public void setDateRoute(Date dateRoute) {
		this.dateRoute = dateRoute;
	}

	public List<RouteDaysOfWeek> getDaysOfWeek() {
		return daysOfWeek;
	}
	
	public List<DaysOfWeek> getDaysOfWeekList() {
		List<DaysOfWeek> set = new ArrayList<>();
		for(RouteDaysOfWeek day : getDaysOfWeek()){
			set.add(day.getDay());
		}
		return set;
	}
	
	public String getDaysOfWeekText() {
		String daysOfWeek = "";
		for(RouteDaysOfWeek day : getDaysOfWeek()){
			daysOfWeek += day.getDay() + ", ";
		}
		if(!daysOfWeek.isEmpty())
			daysOfWeek = daysOfWeek.substring(0, daysOfWeek.lastIndexOf(","));
		return daysOfWeek;
	}

	public void setDaysOfWeek(List<RouteDaysOfWeek> daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}
	
	public void addDayOfWeek(DaysOfWeek day){
		if(this.daysOfWeek == null) this.daysOfWeek = new ArrayList<RouteDaysOfWeek>();
		RouteDaysOfWeek rd = new RouteDaysOfWeek();
		rd.setRoute(this);
		rd.setDay(day);
		this.daysOfWeek.add(rd);
	}
	
	public void addDayOfWeek(RouteDaysOfWeek day){
		if(this.daysOfWeek == null) this.daysOfWeek = new ArrayList<RouteDaysOfWeek>();
		this.daysOfWeek.add(day);
	}
	
	public void setDaysOfWeek(Set<DaysOfWeek> daysOfWeek) {
		if(this.daysOfWeek == null) this.daysOfWeek = new ArrayList<RouteDaysOfWeek>();
		for(DaysOfWeek day : daysOfWeek){
			Boolean flag = true;
			for(RouteDaysOfWeek rday : this.daysOfWeek){
				if(rday.equals(day)){
					flag = false;
					break;
				}
			}
			if(flag){
				RouteDaysOfWeek rd = new RouteDaysOfWeek();
				rd.setRoute(this);
				rd.setDay(day);
				this.daysOfWeek.add(rd);
			}
		}
		
		for(RouteDaysOfWeek rday : this.daysOfWeek){
			Boolean flag = true;
			for(DaysOfWeek day : daysOfWeek){
				if(rday.equals(day)){
					flag = false;
					break;
				}
			}
			if(flag){
				this.daysOfWeek.remove(rday);
			}
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getExcludeByAbuse() {
		return excludeByAbuse;
	}

	public void setExcludeByAbuse(Boolean excludeByAbuse) {
		this.excludeByAbuse = excludeByAbuse;
	}

	public Boolean getDisponibility() {
		return disponibility;
	}

	public void setDisponibility(Boolean disponibility) {
		this.disponibility = disponibility;
	}
	
}
