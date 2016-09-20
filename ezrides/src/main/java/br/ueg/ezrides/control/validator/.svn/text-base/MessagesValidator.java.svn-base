package br.ueg.ezrides.control.validator;

import java.util.List;
import java.util.Map;

import br.com.vexillum.control.validator.Validator;
import br.com.vexillum.util.Return;
import br.ueg.ezrides.control.MessagesController;
import br.ueg.ezrides.model.entitys.User;

/**
 * Classe responsável por validar as ações do {@link MessagesController}.
 * @author fotorious
 *
 */
public class MessagesValidator extends Validator {

	public MessagesValidator(Map<String, Object> mapData) {
		super(mapData);
	}

	/**
	 * Faz a validação básica dos atributos.
	 * Valida se a mensagem tem ao menos um destinatário.
	 * @return {@link Return}
	 */
	@SuppressWarnings("unchecked")
	public Return validateSendMessages(){
		Return ret = validateModel();
		if(ret.isValid()){
			List<User> selectedFriends = (List<User>) mapData.get("selectedFriends");
			if(selectedFriends == null || selectedFriends.isEmpty()){
				ret.concat(creatReturn("destiny", getValidationMessage("destiny", "notNull", false)));
			}
		}
		return ret;
	}
	
}
