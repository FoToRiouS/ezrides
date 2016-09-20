package br.ueg.ezrides.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.vexillum.control.FriendshipController;
import br.com.vexillum.model.Friendship;
import br.com.vexillum.util.HibernateUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.ueg.ezrides.model.entitys.Messages;
import br.ueg.ezrides.model.entitys.SentMessages;
import br.ueg.ezrides.model.entitys.User;

/**
 * Controlador responsável por cuidar das ações relacionadas as Mensagens Pessoais.
 * @see Messages
 * @author fotorious
 *
 */
@Service
@Scope("prototype")
public class MessagesController extends BaseController<Messages> {

	public MessagesController() {
		super(Messages.class);
	}

	/**
	 * Retorna a lista de mensagens ativas recebidas por um usuário.
	 * @param user Usuário que recebeu as mensagens.
	 * @return Lista de {@link SentMessages}
	 */
	@SuppressWarnings("unchecked")
	public List<SentMessages> getReceivedMessages(User user){
		String sql = "FROM SentMessages WHERE destiny = " + user.getId() + " AND active = true ORDER BY sentTime desc";
		getData().put("sql", sql);
		return (List<SentMessages>) searchByHQL().getList();
	}
	
	/**
	 * Retorna a lista de amigos do usuário logado na sessão.
	 * @return {@link Return}
	 */
	@SuppressWarnings("unchecked")
	public Return getFriends(){
		Return ret = new Return(true);
		
		User user = (User) getData().get("userLogged");
		List<User> friends = new ArrayList<>();
		
		HashMap<String, Object> data = new HashMap<>();
		data.put("user", user);
		
		FriendshipController controller = SpringFactory.getController("friendshipController", FriendshipController.class, data);
		
		for (Friendship friend : (List<Friendship>)controller.searchAllFriends().getList()) {
			if(friend.getOwner().equals(user)){
				friends.add((User) HibernateUtils.materializeProxy(friend.getFriend()));
			} else {
				friends.add((User) HibernateUtils.materializeProxy(friend.getOwner()));
			}
		}
		
		ret.setList(friends);
		return ret;
	}
	
	/**
	 * Desativa uma mensagem recebida.
	 * @return {@link Return}
	 */
	public Return deactivateSentMessage(){
		SentMessages message = (SentMessages) getData().get("selectedReceivedMessage");
		message.setActive(false);
		
		SentMessagesController controller = SpringFactory.getController("sentMessagesController", SentMessagesController.class, null);
		return controller.update(message);
	}
	
	/**
	 * Envia mensagens a um ou mais usuários.
	 * @return {@link Return}
	 */
	@SuppressWarnings("unchecked")
	public Return sendMessages(){
		Messages message = getEntity();
		message.setOwner((User) getData().get("userLogged"));
		if(message.getTitle() == null || message.getTitle().isEmpty()){
			message.setTitle("(Sem Título)");
		}
		
		List<User> selectedFriends = (List<User>) getData().get("selectedFriends");
		for (User user : selectedFriends) {
			SentMessages sent = new SentMessages();
			sent.setDestiny(user);
			sent.setMessage(message);
			
			message.getSentMessages().add(sent);
		}
		
		return super.save();
	}
	
}
