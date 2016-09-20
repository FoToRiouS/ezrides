package br.ueg.ezrides.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import br.com.vexillum.util.Return;
import br.ueg.ezrides.control.RouteController;
import br.ueg.ezrides.control.UserController;
import br.ueg.ezrides.model.entitys.User;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class SearchUsersComposer extends UserComposer {

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
	
	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	/**
	 * Procura usuários baseado na chave de pesquisa, chamando a ação correspondente no controlador.
	 * @return {@link Return}
	 * @see UserController#searchUsers()
	 */
	@SuppressWarnings("unchecked")
	public Return searchUsers(){
		binder.saveAll();
		Return ret = getControl().doAction("searchUsers");
		if(ret.getList() != null && !ret.getList().isEmpty()){
			setListEntity((List<User>) ret.getList());
			loadBinder();
			resultList.setVisible(true);
			noResultMessage.setVisible(false);
		} else {
			resultList.setVisible(false);
			noResultMessage.setVisible(true);
		}
		
		return ret;
	}
	
	/**
	 * Fazer a requisição de amizade a um usuário.
	 * @param comp Componente que contém o usuário.
	 */
	public void requestFriendship(Component comp) {
		Row row = (Row) super.getParent(comp, "row");
		User user = row.getValue();
		setEntity(user);
		doAction("requestFriendship");
	}
	
}
