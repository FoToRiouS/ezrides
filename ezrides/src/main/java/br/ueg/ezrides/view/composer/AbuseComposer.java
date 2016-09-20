package br.ueg.ezrides.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;

import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.ueg.ezrides.control.AbuseController;
import br.ueg.ezrides.model.entitys.Abuse;
import br.ueg.ezrides.model.entitys.Route;

/**
 * Controlador das telas relacionadas a Denúncia.
 * @author fotorious
 * @see Abuse
 *
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class AbuseComposer extends BaseComposer<Abuse, AbuseController> {
	
	/**
	 * Alguma rota que necessite ser salva antes de ser passada ao controlador.
	 * @see Route
	 */
	private Route route;
	
	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if(arg.get("selectedEntity") != null){
			setEntity((Abuse) arg.get("selectedEntity"));
		}else if(arg.get("entity") != null){
			setRoute((Route) arg.get("entity"));
			getEntity().setRoute(getRoute());
		}
		loadBinder();
	}
	
	@Override
	public Return saveEntity() {
		Return ret = super.saveEntity();
		if(ret.isValid()){
			getComponentById("modalDenuncia").detach();
		}
		return ret;
	}
	
	@Override
	public AbuseController getControl() {
		return SpringFactory.getController("abuseController", AbuseController.class, ReflectionUtils.prepareDataForPersistence(this));
	}

	@Override
	public Abuse getEntityObject() {
		return new Abuse();
	}

}
