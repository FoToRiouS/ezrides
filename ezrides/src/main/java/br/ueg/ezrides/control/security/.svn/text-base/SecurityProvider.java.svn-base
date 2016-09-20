package br.ueg.ezrides.control.security;

import java.util.Date;
import java.util.HashMap;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import br.com.vexillum.control.security.AuthenticatorProvider;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.ueg.ezrides.control.UserController;
import br.ueg.ezrides.model.entitys.User;

/**
 * Provedor de segurança, responsável pela autenticação do usuário nas páginas.
 * @author fotorious
 *
 */
public class SecurityProvider extends AuthenticatorProvider<User> {

	/* (non-Javadoc)
	 * @see br.com.vexillum.control.security.AuthenticatorProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		Authentication aut = super.authenticate(auth);
		if(aut != null && aut.isAuthenticated()){
			User user = (User) aut.getDetails();
			if(!saveUserWithDate(user).isValid()){
				return null;
			}
		}
		return aut;
	}
	
	/**
	 * Salva a hora de acesso de um Usuário.
	 * @param user Usuário a ser salvo a data/hora de sua último logon.
	 * @return {@link Return}
	 */
	private Return saveUserWithDate(User user){
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		user.setUltimoAcesso(new Date());
		
		data.put("entity", user);
		UserController controller = SpringFactory.getController("userController", UserController.class, data);
		
		return controller.doAction("update");
	}

}
