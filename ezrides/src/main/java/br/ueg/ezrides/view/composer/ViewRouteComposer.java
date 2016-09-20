package br.ueg.ezrides.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;
import br.ueg.ezrides.model.entitys.Route;

/**
 * Controlador da tela de visualização de rotas.
 * @author Fernando
 * @see Route
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class ViewRouteComposer extends RouteComposer {
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Object route = arg.get("route");
		String id = Executions.getCurrent().getParameter("route");
		if(id != null){
			if(!initRouteById(id)){
				Executions.sendRedirect("/paginas/dashboard/");
			}
		}else if(route == null){
			setEntity((Route) arg.get("selectedEntity"));
		} else {
			setEntity((Route) route);
		}
		initFldFrequency();
		initToolbarButtons();
		loadBinder();
	}
	
	/**
	 * Inicia o campo de Frequência.
	 * Exibindo apenas um, ou o da Data da rota ou o Dias da semana.
	 */
	private void initFldFrequency() {
		if(getEntity().getDateRoute() == null){
			getComponentById("dateRoute").setVisible(false);
		} else {
			getComponentById("daysOfWeek").setVisible(false);
		}
	}

	/**
	 * Inicia os botões da barra, exibindo as opções de compartilhamento se for o próprio usuário, ou exibindo a opção de denúncia caso não seja.
	 */
	private void initToolbarButtons() {
		if(getUserInSession().getId().equals(getEntity().getUser().getId())){
			getComponentById("toolAbuse").detach();
		} else {
			getComponentById("toolFacebook").detach();
			getComponentById("toolTwitter").detach();
//			getComponentById("toolGooglePlus").detach();
		}
	}

	/**
	 * Redireciona ao perfil do usuário.
	 */
	public void redirectToProfile(){
		redirectToProfile(getEntity().getUser().getId());
	}
	
	/**
	 * Publica a rota no Facebook.
	 */
	public void publishRouteOnFacebook(){
		postStatusMessageFacebook(getPostMessage());
		showMessagePublish();
	}
	
	/**
	 * Publica a rota no Twitter.
	 */
	public void publishRouteOnTwitter(){
		updateStatusTwitter(getPostMessage());
		showMessagePublish();
	}
	
	/**
	 * Exibe mensagem de sucesso na publicação da rota.
	 */
	private void showMessagePublish(){
		Return ret = new Return(true);
		ret.addMessage(new Message(null, "Publicado com sucesso!"));
		treatReturn(ret);
	}
	
	/**
	 * Cria a mensagem padrão para publicação.
	 * @return Mensagem padrão, baseada em uma rota.
	 */
	private String getPostMessage(){
		Route route = getEntity();
		String post = route.getUser().getName() + " publicou uma rota na rede social EZ-Rides.\n\nVisualize-a em " + getContextPath() + "/paginas/rotas/visualizarS.zul?route=" + getEntity().getId();
		return post;
	}
	
}
