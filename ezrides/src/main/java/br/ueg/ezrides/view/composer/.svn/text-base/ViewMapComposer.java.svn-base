package br.ueg.ezrides.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;

import br.ueg.ezrides.control.RouteController;
import br.ueg.ezrides.model.entitys.Route;

/**
 * Controlador da tela de visualização do mapa da rota.
 * @author Fernando
 * @see Route
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class ViewMapComposer extends BaseComposer<Route, RouteController>{

	/**
	 * Ponto inicial da rota.
	 */
	private String initialPoint;
	
	/**
	 * Ponto final da rota.
	 */
	private String finalPoint;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		initPoints();
		Clients.evalJavaScript("calcRoute(\"" + initialPoint + "\", \"" + finalPoint + "\");");
	}
	
	/**
	 * Inicia os pontos de uma rota.
	 */
	private void initPoints() {
		Route route = null;
		if(arg.get("selectedEntity") != null){
			route = (Route) arg.get("selectedEntity");
		} else if(arg.get("entity") != null){
			route = (Route) arg.get("entity");
		}
		initialPoint = route == null ? "" : route.getInitialPoint();
		finalPoint = route == null ? "" : route.getFinalPoint();
	}

	@Override
	public RouteController getControl() {
		return null;
	}

	@Override
	public Route getEntityObject() {
		return null;
	}

}
