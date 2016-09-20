package br.ueg.ezrides.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;

import br.com.vexillum.control.GenericControl;
import br.com.vexillum.model.ICommonEntity;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.ueg.ezrides.control.UserController;
import br.ueg.ezrides.model.entitys.User;

/**
 * Controlador da Dashboard do sistema.
 * @author fotorious
 *
 */
@SuppressWarnings({ "serial", "rawtypes" })
@org.springframework.stereotype.Component
@Scope("prototype")
public class DashboardComposer extends BaseComposer {

	/**
	 * Email do usuário a ser convidado.
	 */
	private String inviteEmail;
	
	public String getInviteEmail() {
		return inviteEmail;
	}

	public void setInviteEmail(String inviteEmail) {
		this.inviteEmail = inviteEmail;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
//		if(Executions.getCurrent().getParameter("code") == null){
//			Facebook face = new br.com.vexillum.vexsocial.core.FacebookConnection().getFacebookInstance();
//			Session sess = Sessions.getCurrent();
//			sess.setAttribute("facebook", face);
//			Executions.sendRedirect(face.getOAuthAuthorizationURL("http://localhost:8080/ezrides/paginas/dashboard/"));
//		} else {
//			Session sess = Sessions.getCurrent();
//			Facebook face = (Facebook) sess.getAttribute("facebook");
//			face.getOAuthAccessToken(Executions.getCurrent().getParameter("code"));
//			face.postStatusMessage("Teste");
//			System.out.println(face.getName());
//		}
	}
	
	/**
	 * Chama a ação de convidar uma pessoa no controlador correspondente.
	 * @see UserController#inviteUser().
	 * @return
	 */
	public Return sendInviteEmail(){
		saveBinder();
		UserController controller = SpringFactory.getController("userController", UserController.class, ReflectionUtils.prepareDataForPersistence(this));
		controller.setEntity(new User());
		Return ret = controller.doAction("inviteUser");
		if(ret.isValid()){
			setInviteEmail("");
			loadComponent("fldInvite");
		}
		return ret;
	}
	
	@Override
	public GenericControl getControl() {
		return null;
	}

	@Override
	public ICommonEntity getEntityObject() {
		return null;
	}

}
