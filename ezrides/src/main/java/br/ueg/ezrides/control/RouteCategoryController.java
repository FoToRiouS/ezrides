package br.ueg.ezrides.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.vexillum.util.Return;
import br.ueg.ezrides.model.entitys.RouteCategory;

/**
 * Controlador responsável por cuidar das ações relacionadas as Categorias de Rotas.
 * @see RouteCategory
 * @author fotorious
 *
 */
@Service
@Scope("prototype")
public class RouteCategoryController extends BaseController<RouteCategory> {

	public RouteCategoryController() {
		super(RouteCategory.class);
	}
	
	/* (non-Javadoc)
	 * @see br.com.vexillum.control.GenericControl#deactivate()
	 */
	public Return deactivate(){
		entity.setActive(false);
		return super.update();
	}
	
}
