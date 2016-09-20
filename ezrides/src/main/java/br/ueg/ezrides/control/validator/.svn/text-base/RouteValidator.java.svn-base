package br.ueg.ezrides.control.validator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import br.com.vexillum.control.GenericControl;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.model.CommonEntity;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.ueg.ezrides.control.RouteController;
import br.ueg.ezrides.model.entitys.Route;

/**
 * Classe responsável por validar as ações do {@link RouteController}.
 * @author fotorious
 *
 */
public class RouteValidator extends BaseValidator {

	public RouteValidator(Map<String, Object> mapData) {
		super(mapData);
	}
	
	@Override
	public Return validateSave() {
		Return ret = super.validateSave();
		ret.concat(existsName());
		return ret;
	}
	
	/**
	 * Valida se a pesquisa feita pelo método {@link GenericControl#searchByCriteria()} tem ao menos um critério definido.
	 * @return {@link Return}
	 */
	public Return validateSearchByCriteria(){
		CommonEntity centity = (CommonEntity) entity;
		Return ret = creatReturn(null, getValidationMessage(null, "searchfields", false));
		try {
			for (Field field : ReflectionUtils.getSearchFields(centity)) {
				field.setAccessible(true);
				if(!field.getName().equals("active") && !field.getName().equals("user") && field.get(centity) != null && !field.get(centity).equals("")){
					return new Return(true);
				}
			}
		} catch (Exception e) {
			ret = new ExceptionManager(e).treatException();
		}
		return ret;
	}
	
	/**
	 * Verifica se o nome já existe no sistema para o usuário.
	 * @return {@link Return}
	 */
	private Return existsName(){
		Return ret = new Return(true);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("sql", "FROM Route r WHERE r.name = '" + ((Route) entity).getName() + "' AND r.user = '" + ((Route) entity).getUser().getId() + "'");
		
		RouteController controller = SpringFactory.getController("routeController", RouteController.class, data);
		
		ret.concat(controller.searchByHQL());
		
		if(!ret.getList().isEmpty()){
			ret.concat(creatReturn("nome", getValidationMessage("name", "exists", true)));
		}
		return ret;
	}
	
}
