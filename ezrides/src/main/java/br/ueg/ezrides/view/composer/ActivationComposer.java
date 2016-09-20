package br.ueg.ezrides.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import br.com.vexillum.util.Return;

/**
 * Controlador da tela de ativa;'ao do usuário.
 * @author fotorious
 *
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class ActivationComposer extends UserComposer {
	
	/**
	 * Id do usuário passado via GET pelo navegador.
	 */
	private String user;
	
	/**
	 * Código de verificação passado via GET pelo navegador.
	 */
	private String verificationCode;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		user = Executions.getCurrent().getParameter("user");
		verificationCode = Executions.getCurrent().getParameter("verif");
		
		Return ret = getControl().doAction("activate");
		
		if(!ret.isValid()){
			Executions.sendRedirect("/paginas/erros/error.zul");
		}
	}

}
