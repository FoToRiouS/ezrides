package br.ueg.ezrides.control.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.vexillum.util.EncryptUtils;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.ueg.ezrides.control.UserController;
import br.ueg.ezrides.model.entitys.User;

/**
 * Classe responsável por validar as ações do {@link UserController}.
 * @author fotorious
 *
 */
public class UserValidator extends BaseValidator {
	
	
	public UserValidator(Map<String, Object> mapData) {
		super(mapData);
	}

	@Override
	public Return validateSave() {
		Return ret = super.validateSave();
		ret.concat(equalsEmail());
		ret.concat(equalsSenha());
		ret.concat(existsEmail());
		return ret;
	}
	
	/**
	 * Válida o método {@link UserController#updatePassword()}, verificando se a senha atual e nova senha, correspondem aos críterios definidos.
	 * @return {@link Return}
	 */
	public Return validateUpdatePassword(){
		User user = (User) entity;
		String pass = user.getPassword();
		String actualPass = (String)mapData.get("senhaAtual");
		String newPass = (String)mapData.get("novaSenha");
		String cNewPass = (String)mapData.get("cNovaSenha");
		
		Return ret = new Return(true);
		
		ret.concat(notNull("senhaAtual", actualPass, true));
		ret.concat(notNull("novaSenha", newPass, true));
		ret.concat(notNull("cNovaSenha", cNewPass, true));
		
		if(ret.isValid()){
			if(!pass.equals(EncryptUtils.encryptOnSHA512(actualPass))){
				ret.setValid(false);
				ret.addMessage(new Message("senhaAtual", getValidationMessage("senhaatual", "equals", false)));
			} 
			if((newPass == null || newPass.length() < 6)){
				ret.setValid(false);
				ret.addMessage(new Message("novaSenha", getValidationMessage("newpass", "length", false)));
			}
			if(!newPass.equals(cNewPass)){
				ret.setValid(false);
				ret.addMessage(new Message("cNovaSenha", getValidationMessage("cnewpass", "equals", false)));
			}
		}
		return ret;
	}
	
	/**
	 * Valida o método {@link UserController#recoveryPassword()}, verificando se o email usado na recuperação existe no sistema.
	 * @return {@link Return}
	 */
	@SuppressWarnings("unchecked")
	public Return validateRecoveryPassword(){
		Return ret = new Return(true);
		String email = (String) mapData.get("userEmail");
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		data.put("sql", "FROM User u WHERE u.email = '" + email + "' and active = true");
		
		UserController controller = SpringFactory.getController("userController", UserController.class, data);
		List<User> result = (List<User>) controller.searchByHQL().getList();
		User user = (result != null && !result.isEmpty()) ? result.get(0) : null;
		if(user == null){
			ret.concat(creatReturn(null, getValidationMessage("recoveryemail", "exists", false)));
		} else {
			mapData.put("recoveryUser", user);
		}
		
		return ret;
	}
	
	/**
	 * Valida o método {@link UserController#updateRecoveryPassword()}, verificando a senha de recuperação corresponde aos críterios definidos.
	 * @return {@link Return}
	 */
	public Return validateUpdateRecoveryPassword(){
		Return ret = new Return(true);
		
		String newPassword = (String)mapData.get("recoveryPassword");;
		String cNewPassword = (String)mapData.get("cRecoveryPassword");;
		
		ret.concat(notNull("recoveryPassword", newPassword, true));
		ret.concat(notNull("cRecoveryPassword", cNewPassword, true));
		
		if(ret.isValid()){
			if((newPassword == null || newPassword.length() < 6)){
				ret.setValid(false);
				ret.addMessage(new Message("recoveryPassword", getValidationMessage("recoverypass", "length", false)));
			}
			if(!newPassword.equals(cNewPassword)){
				ret.setValid(false);
				ret.addMessage(new Message("cRecoveryPassword", getValidationMessage("crecoverypass", "equals", false)));
			}
		}
		
		return ret;
	}
	
	/**
	 * Valida o método {@link UserController#inviteUser()}, verificando se o email é válido e se não existe no sistema.
	 * @return {@link Return}
	 */
	public Return validateInviteUser(){
		Return ret = new Return(true);
		String inviteEmail = (String) mapData.get("inviteEmail");
		if(inviteEmail == null || inviteEmail.isEmpty()){
			ret.concat(creatReturn(null, getValidationMessage("invite", "notnull", false)));
		}
		if(ret.isValid()){
			ret.concat(searchEmail(inviteEmail));
			if(!ret.getList().isEmpty()){
				ret.concat(creatReturn(null, getValidationMessage("invite", "exists", true)));
			}
		}
		return ret;
	}
	
	/**
	 * Verifica se o email já existe no sistema.
	 * @return {@link Return}
	 */
	private Return existsEmail(){
		Return ret = searchEmail(((User)entity).getEmail());
		
		if(!ret.getList().isEmpty()){
			ret.concat(creatReturn("email", getValidationMessage("email", "exists", true)));
		}
		return ret;
	}
	
	
	/**
	 * Procura um email no sistema.
	 * @param email Email a ser procurado.
	 * @return {@link Return}
	 */
	private Return searchEmail(String email){
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("sql", "FROM User u WHERE u.email = '" + email + "'");
		
		UserController controller = SpringFactory.getController("userController", UserController.class, data);
		
		return controller.searchByHQL();
	}
	
	/**
	 * Verifica se a senha e confirmação de senha são iguais.
	 * @return {@link Return}
	 */
	private Return equalsSenha(){
		Return ret = new Return(true);
		if(!equalsFields(((User)entity).getPassword(), (String)mapData.get("cSenha")).isValid()){
			ret.concat(creatReturn("cSenha", getValidationMessage("senha", "equals", false)));
		}
		return ret;
	}
	
	/**
	 * Confirma se o email e a confirmação se email são iguais.
	 * @return {@link Return}
	 */
	private Return equalsEmail(){
		Return ret = new Return(true);
		if(!equalsFields(((User)entity).getEmail(), (String)mapData.get("cEmail")).isValid()){
			ret.concat(creatReturn("cEmail", getValidationMessage("email", "equals", false)));
		}
		return ret;
	}

}
