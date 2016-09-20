package br.ueg.ezrides.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Groupbox;

import br.com.vexillum.util.Return;

/**
 * Controlador da página de cadastro, alteração e pesquisa do usuário.
 * @author fotorious
 *
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class ManagerUserComposer extends UserComposer {

	/**
	 * Titulo da página.
	 */
	String title;
	
	/**
	 * Titulo do {@link Groupbox} da página.
	 */
	String groupboxTitle;
	
	/**
	 * Campo de confirmação de senha.
	 */
	private String cSenha = null;
	
	/**
	 * Campo de confirmação de email.
	 */
	private String cEmail = null;
	
	/**
	 * Email de referência, para ver o usuário que convidou.
	 */
	private String referenceEmail = null;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGroupboxTitle() {
		return groupboxTitle;
	}

	public void setGroupboxTitle(String groupboxTitle) {
		this.groupboxTitle = groupboxTitle;
	}
	
	public String getCSenha() {
		return cSenha;
	}

	public void setCSenha(String cSenha) {
		this.cSenha = cSenha;
	}

	public String getCEmail() {
		return cEmail;
	}

	public void setCEmail(String cEmail) {
		this.cEmail = cEmail;
	}

	public String getReferenceEmail() {
		return referenceEmail;
	}

	public void setReferenceEmail(String referenceEmail) {
		this.referenceEmail = referenceEmail;
	}

	/**
	 * Inicia os títulos da páginas, verificando se é cadastro ou alteração.
	 */
	private void initTitles(){
		if(entity.getId() == null || entity.getId() == 0){
			title = "Cadastro de Usuário";
			groupboxTitle = "Cadastro";
		} else {
			title = "Alteração de Usuário";
			groupboxTitle = "Alterar";
		}
	}

	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		if(getUpdate()){
			initUserById(Executions.getCurrent().getParameter("id"));
		} else {
			setReferenceEmail(Executions.getCurrent().getParameter("ref"));
		}
		initTitles();
		loadBinder();
		loadDependentComboboxes();
	}	
	
	@Override
	public Return saveEntity() {
		Return ret = super.saveEntity();
		if(ret.isValid() && !getUpdate()){
			getComponentById(component, "form").setVisible(false);
			getComponentById(component, "message").setVisible(true);
		} else if(ret.isValid() && getUpdate()) {
			Executions.sendRedirect("/paginas/administrador/");
		}
		return ret;
	}
	
	/**
	 * @deprecated
	 */
	public void showDeleteConfirmation(){
		super.showDeleteConfirmation("Tem certeza que deseja excluir o usuário " + getSelectedEntity().getName() + "?");
	}

	/**
	 * Exibe mensagem de confirmação no momento de desativar um usuário.
	 */
	public void callDeactivationConfirmation(){
		super.showDeactivateConfirmation("Você deseja realmente desativar o usuario " + selectedEntity.getName() + "?");
	}
	
	@Override
	protected void onOkDeactivationEvent() {
		if(!((Checkbox)getComponentById(component, "fldAtivo")).isChecked()){
			getListEntity().remove(getSelectedEntity());
		}
		super.onOkDeactivationEvent();
	}

	/**
	 * Redireciona a página de alteração de um usuário selecionado na lista.
	 */
	public void redirectToUpdate(){
		Return ret = validateSelectedEntity();
		if(ret.isValid()){
			Executions.sendRedirect("/paginas/usuario/inclusao.zul?id=" + getSelectedEntity().getId());
		}
		treatReturn(ret);
	}
	
	/**
	 * Redireciona ao perfil de um usuário selecionado na lista.
	 */
	public void redirectToProfile(){
		Return ret = validateSelectedEntity();
		if(ret.isValid()){
			super.redirectToProfile(selectedEntity.getId());
		}
		treatReturn(ret);
	}
	
}
