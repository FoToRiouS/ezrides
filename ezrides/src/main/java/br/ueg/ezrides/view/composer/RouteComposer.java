package br.ueg.ezrides.view.composer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.ListModelList;

import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.ueg.ezrides.control.RouteCategoryController;
import br.ueg.ezrides.control.RouteController;
import br.ueg.ezrides.model.entitys.Route;
import br.ueg.ezrides.model.entitys.RouteCategory;
import br.ueg.ezrides.model.entitys.RouteDaysOfWeek;
import br.ueg.ezrides.model.entitys.User;
import br.ueg.ezrides.model.enums.DaysOfWeek;

/**
 * Controlador das telas de Rotas.
 * @author Fernando
 * @see Route
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class RouteComposer extends BaseComposerSocial<Route, RouteController> {

	/**
	 * Título do {@link Groupbox} da página.
	 */
	private String groupboxTitle;
	
	/**
	 * Lista de dias da semana.
	 * @see DaysOfWeek
	 */
	private List<DaysOfWeek> listDaysOfWeek = new ArrayList<>();
	
	/**
	 * Lista auxiliar com o relacionamento de rotas e dias da semana deletados.
	 * Usado em {@link RouteController#addDaysOfWeek()} para evitar linhas desnecessárias no banco.
	 */
	private List<RouteDaysOfWeek> auxListDaysOfWeekDeleted;
	
	public List<DaysOfWeek> getListDaysOfWeek() {
		return listDaysOfWeek;
	}

	public void setListDaysOfWeek(List<DaysOfWeek> listDaysOfWeek) {
		this.listDaysOfWeek = listDaysOfWeek;
	}

	public List<RouteDaysOfWeek> getAuxListDaysOfWeekDeleted() {
		return auxListDaysOfWeekDeleted;
	}

	public void setAuxListDaysOfWeekDeleted(
			List<RouteDaysOfWeek> auxListDaysOfWeekDeleted) {
		this.auxListDaysOfWeekDeleted = auxListDaysOfWeekDeleted;
	}

	public String getGroupboxTitle() {
		return groupboxTitle;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if(getUpdate()){
			initUpdate();
		}	
		this.auxListDaysOfWeekDeleted = new ArrayList<>();
		initTitles();
		loadBinder();
	}
	
	/**
	 * Inicializa a página de update, inicializando os campos.
	 */
	private void initUpdate(){
		initRouteById(Executions.getCurrent().getParameter("id"));
		Combobox cmb = (Combobox) getComponentById("cmbFrequency");
		if(getEntity().getDateRoute() != null){
			showDateRouteField();
			cmb.setSelectedIndex(0);
		} else {
			showDaysOfWeekField();
			setListDaysOfWeek(getEntity().getDaysOfWeekList());
			cmb.setSelectedIndex(1);
		}
		calcRoute(getEntity().getInitialPoint(), getEntity().getFinalPoint());
	}
	
	/**
	 * Calcula a rota para a passagem para a API do GMaps. 
	 * @param initialPoint Ponto inicial da rota.
	 * @param finalPoint Ponto final da rota.
	 */
	protected void calcRoute(String initialPoint, String finalPoint) {
		Clients.evalJavaScript("calcRoute(\"" + initialPoint + "\", \"" + finalPoint + "\");");
	}
	
	/**
	 * Inicia rota pelo ID.
	 * @param id ID da rota.
	 * @return true se a rota foi inicializada com sucesso.
	 */
	public boolean initRouteById(String id) {
		entity = getControl().searchById(Long.parseLong(id));
		if(entity == null){
			return false;
		}
		return true;
	}
	
	/**
	 * Inicializa os títulos da página.
	 */
	private void initTitles(){
		if(entity.getId() == null || entity.getId() == 0){
			groupboxTitle = "Cadastro de Rotas";
		} else {
			groupboxTitle = "Alteração de Rotas";
		}
	}
	
	@Override
	protected void clearForm() {
		((Combobox)getComponentById("cmbFrequency")).setRawValue(null);
		setListDaysOfWeek(new ArrayList<DaysOfWeek>());
		hideDaysField();
		resetGMaps();
		super.clearForm();
	}
	
	/**
	 * Busca a lista de todas as categorias de rotas.
	 * @return Lista de {@link RouteCategory}
	 */
	@SuppressWarnings("unchecked")
	public List<RouteCategory> getRouteCategories(){
		RouteCategoryController controller = SpringFactory.getController("routeCategoryController", RouteCategoryController.class, null);
		return (List<RouteCategory>) controller.doAction("listAll").getList();
	}
	
	/**
	 * Lista com todos os dias da semana, a partir do enum {@link DaysOfWeek}.
	 * @return Lista de {@link DaysOfWeek}
	 */
	public List<DaysOfWeek> getDaysOfWeek(){
		return new ListModelList<DaysOfWeek>(Arrays.asList(DaysOfWeek.values()));
	}
	
	/**
	 * Identifica e exibe corretamente o campos de dia único ou dias da semana de uma rotas, baseado na combobox.
	 * @param cmb {@link Combobox} a ser usada como referência.
	 */
	public void showDaysField(Comboitem cmb){
		if(cmb.getValue().equals("dateRoute")){
			showDateRouteField();
		} else {
			showDaysOfWeekField();
		}
	}
	
	/**
	 * Reseta o mapa do GMaps.
	 */
	private void resetGMaps(){
		Clients.evalJavaScript("calcRoute(\"\", \"\");");
	}
	
	/**
	 * Exibe o campo de datá única da rota na tela e esconde o campo de dias da semana.
	 */
	private void showDateRouteField(){
		Component dateRoute = getComponentById("dateRoute");
		Component daysOfWeek = getComponentById("daysOfWeek");
		
		dateRoute.setVisible(true);
		daysOfWeek.setVisible(false);
		
		((Chosenbox)getComponentById("fldDaysOfWeek")).clearSelection();
		getEntity().getDaysOfWeek().clear();
	}
	
	/**
	 * Exibe o campo de dias da semana da rota na tela e esconde o campo de dia único.
	 */
	private void showDaysOfWeekField(){
		Component dateRoute = getComponentById("dateRoute");
		Component daysOfWeek = getComponentById("daysOfWeek");
		
		dateRoute.setVisible(false);
		daysOfWeek.setVisible(true);
		
		((Datebox)getComponentById("fldDateRoute")).setValue(null);
		getEntity().setDateRoute(null);
	}
	
	/**
	 * Esconde campo de dia único e dias da semana.
	 */
	private void hideDaysField(){
		Component dateRoute = getComponentById("dateRoute");
		Component daysOfWeek = getComponentById("daysOfWeek");
		
		dateRoute.setVisible(false);
		daysOfWeek.setVisible(false);
		
		((Datebox)getComponentById("fldDateRoute")).setValue(null);
	}
	
	/**
	 * Redireciona para a página de alteração, baseado em uma rota selecionada.
	 */
	public void redirectToUpdate(){
		Return ret = validateSelectedEntity();
		if(ret.isValid()){
			Executions.sendRedirect("/paginas/rotas/inclusao.zul?id=" + getSelectedEntity().getId());
		}
		treatReturn(ret);
	}
	
	/**
	 * Evento que adiciona um {@link DaysOfWeek} na rota.
	 * @param evt evento disparado.
	 * @see RouteController#addDaysOfWeek()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setDayOfWeek(SelectEvent evt){
		List<DaysOfWeek> list = new ArrayList<>(evt.getSelectedObjects());
		setListDaysOfWeek(list);
		getControl().doAction("addDaysOfWeek");
	}
	
	/**
	 * Abre a página de viasualização da rota.
	 */
	public void viewRoute() {
		Return ret = validateSelectedEntity();
		if (ret.isValid()) {
			callModalWindow("/paginas/rotas/visualizar.zul");
		} 
		treatReturn(ret);
	}
	
	/**
	 * Exibe mensagem de confirmação de desativação da rota.
	 */
	public void showDeactivateConfirmation() {
		super.showDeactivateConfirmation("Você tem certeza que deseja desativar a rota " + selectedEntity.getName() + "?");
	}
	
	/**
	 * Exibe mensagem de confirmação de ativação da rota.
	 */
	public void showActivateConfirmation() {
		super.showActivateConfirmation("Você tem certeza que deseja ativar a rota " + selectedEntity.getName() + "?");
	}
	
	@Override
	public Return saveEntity() {
		Return ret = super.saveEntity();
		if(ret.isValid() && getUpdate()){
			Executions.sendRedirect("/paginas/rotas/pesquisa.zul");
		}
		return ret; 
	}
	
	@Override
	public RouteController getControl() {
		return SpringFactory.getController("routeController", RouteController.class, ReflectionUtils.prepareDataForPersistence(this));
	}

	@Override
	public Route getEntityObject() {
		Route route = new Route();
		route.setUser((User) getUserInSession());
		return route;
	}

}
