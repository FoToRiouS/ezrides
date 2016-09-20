package br.ueg.ezrides.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;

import br.com.vexillum.util.Return;
import br.ueg.ezrides.model.entitys.Abuse;
import br.ueg.ezrides.model.entitys.Route;

/**
 * Controlador das telas relacionadas a visualização de dnúncias.
 * @author Fernando
 * @see Abuse
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class ViewAbusesComposer extends AbuseComposer {
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		initAbusesList();
		loadBinder();
	}
	
	/**
	 * Inicia a lista de denúncias, trazendo todas a denúncias ordenadas pela data.
	 */
	@SuppressWarnings("unchecked")
	private void initAbusesList() {
		setListEntity((List<Abuse>) getControl().listAllGrowing().getList());
	}
	
	/**
	 * Exibe a mensagem de confirmação para a desativação da denúncia.
	 */
	public void showDeactivateConfirmation() {
		Route route = getSelectedEntity().getRoute();
		super.showDeactivateConfirmation("Tem certeza que deseja retirar a denúncia da " + route.getName() + " do usuário " + route.getUser() + "?");
	}
	
	@Override
	protected void onOkDeactivationEvent() {
		super.onOkDeactivationEvent();
		initAbusesList();
		loadBinder();
	}
	
	/**
	 * Redireciona a página de visualização da rota.
	 */
	public void viewRoute() {
		setRoute(getSelectedEntity().getRoute());
		callModalWindow("/paginas/rotas/visualizar.zul");
	}
	
	/**
	 * Exibe a janela com as informações da denúncia.
	 */
	public void callVisualizedWindow(){
		callModalWindow("/paginas/denuncia/visualizar.zul");
	}
	
	/**
	 * Exibe a mensagem de confirmação para a desativação da rota relacionada a denúncia.
	 */
	public void showDeactivationRouteConfirmation(){
		showActionConfirmation("Tem certeza que deseja desativar a rota " + getSelectedEntity().getRoute().getName() + " do usuário " + getSelectedEntity().getRoute().getUser() + "?", "deactivateRoute");
	}
	
	/**
	 * Desativa a rota selecionada.
	 * @return {@link Return}
	 */
	public Return deactivateRoute(){
		Return ret = getControl().doAction("deactivateRoute");
		if(ret.isValid()){
			initAbusesList();
			loadBinder();
		}
		return ret;
	}
	
}
