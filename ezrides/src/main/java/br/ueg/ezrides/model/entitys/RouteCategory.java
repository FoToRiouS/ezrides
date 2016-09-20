package br.ueg.ezrides.model.entitys;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.vexillum.model.EntityBasicActivated;
import br.com.vexillum.model.annotations.ValidatorClass;

/**
 * Classe que representa as categorias das rotas.
 * @author Fernando
 * @see EntityBasicActivated
 *
 */
@SuppressWarnings("serial")
@ValidatorClass(validatorClass="br.ueg.ezrides.control.validator.RouteCategoryValidator")
@Entity
@Table(name="route_category")
public class RouteCategory extends EntityBasicActivated {

	public RouteCategory() {
		setActive(true);
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
