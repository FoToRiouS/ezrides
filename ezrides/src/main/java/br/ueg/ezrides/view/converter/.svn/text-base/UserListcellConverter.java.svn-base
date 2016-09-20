package br.ueg.ezrides.view.converter;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Image;

/**
 * Converter que abrange a conversão da sua do usuário em uma imagem para informar se ele está ativo ou não.
 * @author Fernando
 *
 */
public class UserListcellConverter implements TypeConverter {

	@Override
	public Object coerceToUi(Object val, Component comp) {
		String image  = "";
		Image img;
		if((Boolean) val){
			image = "/imagens/granted.png";
		} else {
			image = "/imagens/denied.png";
		}
		img = new Image(image);
		img.setWidth("22px");
		img.setHeight("22px");
		img.setParent(comp);		
		return img;
//		img.setParent(component);
	}

	@Override
	public Object coerceToBean(Object val, Component comp) {
		return val;
	}
}
