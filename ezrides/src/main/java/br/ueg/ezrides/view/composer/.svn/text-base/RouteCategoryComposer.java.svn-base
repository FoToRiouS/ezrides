package br.ueg.ezrides.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;

import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.ueg.ezrides.control.RouteCategoryController;
import br.ueg.ezrides.model.entitys.RouteCategory;

/**
 * Contrla as telas de categoria de rotas.
 * @author Fernando
 * @see RouteCategory
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class RouteCategoryComposer extends BaseComposer<RouteCategory, RouteCategoryController> {

	/**
	 * Título do groupbox da página.
	 */
	String groupboxTitle;
	
	public String getGroupboxTitle() {
		return groupboxTitle;
	}
	
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		if(getUpdate()){
			initCategoryById(Executions.getCurrent().getParameter("id"));
		}		 
		initTitles();
		loadBinder();
	}	
	
	/**
	 * Inicia os títulos da página.
	 */
	private void initTitles(){
		if(entity.getId() == null || entity.getId() == 0){
			groupboxTitle = "Cadastro de Categoria de Rotas";
		} else {
			groupboxTitle = "Alteração de Categoria de Rotas";
		}
	}
	
	/**
	 * Inicia a categoria da rota.
	 * @param id Id da categoria.
	 * @return true se foi iniciado com sucesso.
	 */
	public boolean initCategoryById(String id) {
		entity = getControl().searchById(Long.parseLong(id));
		if(entity != null)
			return true;
		return false;
	}
	
	/**
	 * Exibe mensagem de confirmação para a desativação da categoria e uma rota.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void showDeactivateConfirmation(){
		Return ret = validateSelectedEntity();
		if(ret.isValid()){
			String message  = "Tem certeza que deseja desativar a categoria " + getSelectedEntity().getName() + "?";
			Messagebox.show(message, "Confirmação", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
			    public void onEvent(Event evt) throws InterruptedException {
			    	if (evt.getName().equals("onOK")) {
			    		deactivateCategory();
			        } 
			    }
			});
		} else {
			treatReturn(ret);
		}
	}
	
	/**
	 * Desativa a categoria de rota, chamando a ação correspondente no controlador.
	 */
	private void deactivateCategory(){
    	setEntity(getSelectedEntity());
    	if(doAction("deactivateEntity")){
    		if(!((Checkbox)getComponentById(component, "fldAtivo")).isChecked()){
    			getListEntity().remove(getSelectedEntity());
    		}
    	}
    	setSelectedEntity(null);
    	initEntity();
    	
    	loadBinder();
	}
	
	/**
	 * Redireciona para a página de alteração.
	 */
	public void redirectToUpdate(){
		Return ret = validateSelectedEntity();
		if(ret.isValid()){
			Executions.sendRedirect("/paginas/categoriarota/inclusao.zul?id=" + getSelectedEntity().getId());
		}
		treatReturn(ret);
	}
	
	public void saveCategory() throws InterruptedException {
		doAction("saveEntity");
//		if(isUpdate() && valid){
//			Thread.sleep(3000);
//			Executions.sendRedirect("/paginas/categoriarota/pesquisa.zul");
//		} else if(!isUpdate() && valid){
//			initEntity();
//			loadBinder();
//		}
	}

	@Override
	public RouteCategoryController getControl() {
		return SpringFactory.getController("routeCategoryController", RouteCategoryController.class, ReflectionUtils.prepareDataForPersistence(this));
	}

	@Override
	public RouteCategory getEntityObject() {
		return new RouteCategory();
	}

}
