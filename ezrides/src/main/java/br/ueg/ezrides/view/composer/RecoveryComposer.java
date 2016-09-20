package br.ueg.ezrides.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import br.com.vexillum.util.Return;
import br.ueg.ezrides.control.UserController;
import br.ueg.ezrides.model.entitys.User;

/**
 * Controla a telas de recuperação de senha.
 * @author Fernando
 *
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class RecoveryComposer extends UserComposer {

	/**
	 * Email do usuário.
	 */
	private String userEmail;
	
	/**
	 * Código de verificação.
	 */
	private String verificationCode;
	
	/**
	 * Nova senha do usuário.
	 */
	private String recoveryPassword;
	
	/**
	 * Confirmação da nova senha do usuário.
	 */
	private String cRecoveryPassword;


	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getRecoveryPassword() {
		return recoveryPassword;
	}

	public void setRecoveryPassword(String recoveryPassword) {
		this.recoveryPassword = recoveryPassword;
	}

	public String getCRecoveryPassword() {
		return cRecoveryPassword;
	}

	public void setCRecoveryPassword(String cRecoveryPassword) {
		this.cRecoveryPassword = cRecoveryPassword;
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		String modif = Executions.getCurrent().getParameter("modif");
		Boolean flag = false;
		
		if(modif != null && modif.equals("true")){
			userEmail = Executions.getCurrent().getParameter("user");
			verificationCode = Executions.getCurrent().getParameter("verif");
			
			Return ret = getControl().verifyValidationCode(userEmail, verificationCode);
			flag = !(ret.isValid());
			entity = (User) ret.getList().get(0);
		}
		
		if(flag){
			Executions.sendRedirect("/paginas/erros/error.zul");
		}
	}
	
	/**
	 * Atualiza a senha do usuário, chamando a ação corresposndente no controlador.
	 * @see UserController#recoveryPassword()
	 * @return {@link Return}
	 */
	public Return recoveryPassword(){
		binder.saveAll();
		Return ret = getControl().doAction("recoveryPassword");
		if(ret.isValid()){
			getComponentById(component, "form").setVisible(false);
			getComponentById(component, "message").setVisible(true);
		}
		return ret;
	}
	
	/**
	 * Atualiza a senha do usuário, chamando a ação corresposndente no controlador.
	 * @see UserController#updateRecoveryPassword()
	 * @return {@link Return}
	 */
	public Return updateRecoveryPassword(){
		binder.saveAll();
		Return ret = getControl().doAction("updateRecoveryPassword");
		if(ret.isValid()){
			getComponentById(component, "form").setVisible(false);
			getComponentById(component, "message").setVisible(true);
		}
		return ret;
	}
	
}
