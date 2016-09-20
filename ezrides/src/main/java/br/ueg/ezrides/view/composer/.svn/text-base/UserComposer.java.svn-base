package br.ueg.ezrides.view.composer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import br.com.vexillum.control.security.ManagerAuthenticator;
import br.com.vexillum.model.Category;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.SpringFactory;
import br.ueg.ezrides.control.CategoriaController;
import br.ueg.ezrides.control.EstadoController;
import br.ueg.ezrides.control.PaisController;
import br.ueg.ezrides.control.UserController;
import br.ueg.ezrides.model.entitys.Country;
import br.ueg.ezrides.model.entitys.State;
import br.ueg.ezrides.model.entitys.User;

/**
 * Controlador das telas relacionadas ao usuário.
 * @author Fernando
 * @see User
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class UserComposer extends BaseComposer<User, UserController> {

	/**
	 * Atributo usado para carregar a lista de estados baseados no país.
	 */
	private Country pesqPais;
	
	public Country getPesqPais() {
		return pesqPais;
	}

	public void setPesqPais(Country pesqPais) {
		this.pesqPais = pesqPais;
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	/**
	 * Inicializa um usuário pelo ID.
	 * @param id Id do Usuário.
	 * @return true se foi inicializado corretamente.
	 */
	@SuppressWarnings("unchecked")
	public boolean initUserById(String id) {
		entity.setId(Long.parseLong(id));
		entity.setActive(null);
		entity.setPerfilPublico(null);
		List<User> list = (List<User>) getControl().doAction("searchByCriteria").getList();
		if (list != null && !list.isEmpty()) {
			entity = list.get(0);
			return true;
		}
		return false;
	}
	
	/**
	 * Inicializa um usuário pelo email.
	 * @param email Email do Usuário.
	 * @return true se foi inicializado corretamente.
	 */
	public boolean initUserByEmail(String email) {
		User user = (User) getControl().getUserByMail(email);
		if (user != null) {
			entity = user;
			return true;
		}
		return false;
	}
	
	/**
	 * Carrega as {@link Combobox} que são dependentes.País e Estado.
	 */
	public void loadDependentComboboxes(){
		Textbox tb;
		Combobox cb;
		
//		pais
		tb = (Textbox) getComponentById(this.component, "fldPaisHidden");
		cb = (Combobox) getComponentById(this.component, "fldPais");
		if(tb != null) cb.setValue(tb.getValue());
		
//		estado
		tb = (Textbox) getComponentById(this.component, "fldEstadoHidden");
		cb = (Combobox) getComponentById(this.component, "fldEstado");
		if(tb != null) cb.setValue(tb.getValue());
	}
	
	/**
	 * Verifica se o usuário que está na página é o memso logado na sessão.
	 * @return true se é o mesmo usuário, false se não é o mesmo usuário
	 */
	public Boolean isMe() {
		return entity != null && entity.getId() != null && entity.getId().equals(getUserInSession().getId());
	}

	/**
	 * Verifica se o usuário logado é administrador.
	 * @return true se é administrador, false sse não é administrador
	 */
	@SuppressWarnings("rawtypes")
	public static Boolean isAdministrator() {
		return (isLogged() && ((ManagerAuthenticator)SecurityContextHolder.getContext().getAuthentication()).isAdministrator());
	}

	@SuppressWarnings("unchecked")
	public List<Country> getPaises() {
		return (List<Country>) SpringFactory.getController("paisController", PaisController.class, null).doAction("listAll").getList();
	}

	@SuppressWarnings("unchecked")
	public List<State> getEstados() {
		if (pesqPais == null) {
			return new ArrayList<State>();
		} else {
			HashMap<String, Object> data = new HashMap<String, Object>();

			State estado = new State();
			estado.setPais(pesqPais);

			data.put("entity", estado);
			EstadoController controlador = SpringFactory.getController("estadoController", EstadoController.class, data);
			return (List<State>) controlador.doAction("searchByCriteria")
					.getList();
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<Category> getCategorias() {
		CategoriaController controlador = SpringFactory.getController("categoriaController", CategoriaController.class, ReflectionUtils.prepareDataForPersistence(this));
		return (List<Category>) controlador.doAction("listAll").getList();
	}
	
	@Override
	public UserController getControl() {
		return SpringFactory.getController("userController", UserController.class, ReflectionUtils.prepareDataForPersistence(this));
	}
	
	@Override
	public User getEntityObject() {
		return new User();
	}

}
