package br.ueg.ezrides.control.validator;

import java.util.HashMap;
import java.util.Map;

import br.com.vexillum.control.validator.Validator;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.ueg.ezrides.control.RouteCategoryController;
import br.ueg.ezrides.model.entitys.RouteCategory;

/**
 * Classe responsável por validar as ações do {@link RouteCategoryController}.
 * @author fotorious
 *
 */
public class RouteCategoryValidator extends Validator {

	public RouteCategoryValidator(Map<String, Object> mapData) {
		super(mapData);
	}

	@Override
	public Return validateSave() {
		Return ret = super.validateSave();
		ret.concat(existsName());
		return ret;
	}
	
	/**
	 * Verifica se o nome já existe no sistema.
	 * @return {@link Return}
	 */
	private Return existsName(){
		Return ret = new Return(true);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("sql", "FROM RouteCategory r WHERE r.name = '" + ((RouteCategory) entity).getName() + "'");
		
		RouteCategoryController controller = SpringFactory.getController("routeCategoryController", RouteCategoryController.class, data);
		
		ret.concat(controller.searchByHQL());
		
		if(!ret.getList().isEmpty()){
			ret.concat(creatReturn("name", getValidationMessage("name", "exists", true)));
		}
		return ret;
	}

}
