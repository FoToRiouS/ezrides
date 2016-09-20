package br.ueg.ezrides.view.converter;

import java.util.Iterator;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.RadiogroupSelectedItemConverter;
import org.zkoss.zul.Radio;

/**
 * Converter que abrange a conversão de um {@link Boolean} para {@link Radio}.
 * @author Fernando
 *
 */
@SuppressWarnings("serial")
public class BooleanRadiogroupConverter extends RadiogroupSelectedItemConverter {

	   @Override
	   public Object coerceToUi(Object val, Component component) {
	        if (val instanceof Boolean) {
	            Boolean bvalue = (Boolean) val;
	            String constanteName = bvalue.toString();
	            //return searchRadio(constanteName, component.getRoot());
	            return super.coerceToUi(constanteName, component);
	        } else if (val == null){
	            return null;
	        } else {
	            throw new IllegalArgumentException("val Object must be an Boolean");
	        }
	    }

	    @Override
	    public Object coerceToBean(Object val, Component component) {
	        String booleanName = (String) super.coerceToBean(val, component);
	        return Boolean.parseBoolean(booleanName);
	    }
	    
	    @SuppressWarnings({ "unused", "rawtypes" })
		private Object searchRadio(Object val, Component component){
	    	for (Iterator it = component.getChildren().iterator(); it.hasNext();) {
				final Component child = (Component)it.next();
				if (child instanceof Radio && ((Radio)child).getRadiogroup().equals(component)) {
					if (val.equals(((Radio)child).getValue())) {
						return child;
					}
				} 
			}
	    	return null;
	    }
}
