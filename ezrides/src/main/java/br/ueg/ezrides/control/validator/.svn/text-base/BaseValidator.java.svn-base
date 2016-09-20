package br.ueg.ezrides.control.validator;

import java.lang.reflect.Field;
import java.util.Map;

import br.com.vexillum.control.GenericControl;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.control.validator.Validator;
import br.com.vexillum.model.CommonEntity;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;

public class BaseValidator extends Validator {

	public BaseValidator(Map<String, Object> mapData) {
		super(mapData);
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
				if(!field.getName().equals("active") && field.get(centity) != null && !field.get(centity).equals("")){
					return new Return(true);
				}
			}
		} catch (Exception e) {
			ret = new ExceptionManager(e).treatException();
		}
		return ret;
	}
	
}
