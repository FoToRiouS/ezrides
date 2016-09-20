package br.ueg.ezrides.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import br.com.vexillum.util.Return;
import br.ueg.ezrides.control.RouteController;
import br.ueg.ezrides.model.entitys.Route;
import br.ueg.ezrides.model.entitys.RouteCategory;

/**
 * Controlador da página de pesquisa de rotas.
 * @author Fernando
 *
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class SearchRoutesComposer extends RouteComposer {

	/**
	 * Lista de resultados.
	 */
	private Grid resultList;
//	private Listbox resultList;
	
	/**
	 * Mensagem que será exibida quando nenhuma rota for encontrada.
	 */
	private Label noResultMessage;
	
	/**
	 * Chave de pesquisa.
	 */
	private String searchField;
	
	/**
	 * Categoria a ser pesquisada.
	 */
	private RouteCategory searchCategory;
	
	/**
	 * Disponibilidade a ser pesquisada.
	 */
	private Boolean disponibility = false;
	
	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	
	public RouteCategory getSearchCategory() {
		return searchCategory;
	}

	public void setSearchCategory(RouteCategory searchCategory) {
		this.searchCategory = searchCategory;
	}

	public Boolean getDisponibility() {
		return disponibility;
	}

	public void setDisponibility(Boolean disponibility) {
		this.disponibility = disponibility;
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if(Executions.getCurrent().getParameter("searchKey") != null){
			setSearchField(Executions.getCurrent().getParameter("searchKey"));
			searchRoutes();
			loadBinder();
		}
	}
	
	/**
	 * Procura as rotas baseado na chave de pesquisa e categoria, chamando a ação correspondente no controlador.
	 * @return {@link Return}
	 * @see RouteController#searchRoutes()
	 */
	@SuppressWarnings("unchecked")
	public Return searchRoutes(){
		Return ret = getControl().doAction("searchRoutes");
		if(ret.getList() != null && !ret.getList().isEmpty()){
			setListEntity((List<Route>) ret.getList());
			loadBinder();
			resultList.setVisible(true);
			noResultMessage.setVisible(false);
		} else {
			resultList.setVisible(false);
			noResultMessage.setVisible(true);
		}
		
		return ret;
	}
	
	@Override
	public void redirectToProfile(Component comp) {
		Row row = (Row) super.getParent(comp, "row");
		Route route = row.getValue();
		redirectToProfile(route.getUser().getId());
	}
	
	/**
	 * Redireciona a janela de visualização de uma rota.
	 * @param comp Componente que contém a rota.
	 */
	public void viewRoute(Component comp) {
		Row row = (Row) super.getParent(comp, "row");
		Route route = row.getValue();
		setSelectedEntity(route);
		super.viewRoute();
	}
	
	/**
	 * Redireciona a janela de visualização de rotas pelo GMaps.
	 * @param comp
	 */
	public void callViewRouteModal(Component comp){
		Row row = (Row) super.getParent(comp, "row");
		Route route = row.getValue();
		setSelectedEntity(route);
		callModalWindow("/paginas/rotas/visualizarRota.zul");
	}
	
	public void createDisponibilityButton(Hlayout layout){
		Route route = ((Row) layout.getParent().getParent().getParent().getParent().getParent()).getValue();
		Button button = new Button();
		button.setWidth("120px");
		if(route.getDisponibility()){
			button.setLabel("Rota Disponível");
			button.setZclass("btn btn-success btn-xs disabled");
		} else {
			button.setLabel("Rota Indisponível");
			button.setZclass("btn btn-danger btn-xs disabled");
		}
		layout.appendChild(button);
	}
	
}
