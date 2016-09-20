package br.ueg.ezrides.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;

import br.com.vexillum.util.Return;
import br.ueg.ezrides.model.entitys.User;

/**
 * Controlador das telas de perfil de usuário, relacionadas as ações de Atualizar Informações e Atualizar Senha.
 * @author Fernando
 *
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class ModalWindowsProfileComposer extends UserComposer {

	/**
	 * Senha atual do usuário, usado na atualização de senha.
	 */
	private String senhaAtual = null;
	
	/**
	 * Nova senha do usuário, usado na atualização de senha.
	 */
	private String novaSenha  = null;
	
	/**
	 * Confirmação da senha de usuário, usado na atualização de senha.
	 */
	private String cNovaSenha  = null;
	
	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getCNovaSenha() {
		return cNovaSenha;
	}

	public void setCNovaSenha(String cNovaSenha) {
		this.cNovaSenha = cNovaSenha;
	}

	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		setEntity((User) arg.get("entity"));
		loadBinder();
		loadDependentComboboxes();
	}
	
	/**
	 * Atualiza as informações do usuário, chamando a ação correspondente no controlador.
	 * @return {@link Return}
	 */
	public Return updateInformation(){
		Return ret =null;
		ret = saveEntity();
		if(ret.isValid()){
			//this.composer.setEntity(getEntity());
			component.detach();
		}
		return ret;
	}
	
	/**
	 * Atualiza a senha do usuário, chamando a ação correspondente no controlador.
	 * @return
	 */
	public Return updatePassword(){
		Return ret = null;
		ret = getControl().doAction("updatePassword");
		if(ret.isValid()){
			component.detach();
		}
		return ret;
	}
	
}
