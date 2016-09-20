package br.ueg.ezrides.control;

import br.com.vexillum.configuration.Properties;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.model.IActivatedEntity;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;

/**
 * Contrador padrão que deve se usado com herança de todos os controladores da EZ-Rides.
 * 
 * @author fotorious
 *
 * @param <E> Entidade que extenda de {@link IActivatedEntity}
 */
public abstract class BaseController<E extends IActivatedEntity> extends GenericControl<E> {

	/**
	 * Properties com as informações que serão usadas no email do sistema.
	 */
	private Properties emailProperties;
	
	public Properties getEmailProperties() {
		if(emailProperties == null){
			emailProperties = SpringFactory.getInstance().getBean("emailProperties", Properties.class);
		}
		return emailProperties;
	}

	public void setEmailProperties(Properties emailProperties) {
		this.emailProperties = emailProperties;
	}

	public BaseController(Class<E> classEntity) {
		super(classEntity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Return listAll() {
		String sql = "FROM " + classEntity.getSimpleName() + " WHERE active = true ORDER BY name";
		if(data.get("entity") == null || ((E) data.get("entity")).getActive() == null || !((E) data.get("entity")).getActive()){
			sql = sql.replace(" WHERE active = true", "");
		}
		data.put("sql", sql);
		return super.searchByHQL();
	}

}
