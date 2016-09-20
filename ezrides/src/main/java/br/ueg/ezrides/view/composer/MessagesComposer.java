package br.ueg.ezrides.view.composer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.ListModelList;

import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.ueg.ezrides.control.MessagesController;
import br.ueg.ezrides.model.entitys.Messages;
import br.ueg.ezrides.model.entitys.SentMessages;
import br.ueg.ezrides.model.entitys.User;

/**
 * Controlador das telas de Mensagens Pessoais
 * @author Fernando
 *
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class MessagesComposer extends BaseComposer<Messages, MessagesController> {

	/**
	 * Lista de amigos selecionados para o envio de mensagens, no momento do envio de mensagens.
	 */
	private List<User> selectedFriends = new ArrayList<>();
	
	/**
	 * Informa se a mensagem é de resposta.
	 */
	private Boolean reply = false;
	
	public List<User> getSelectedFriends() {
		return selectedFriends;
	}

	public void setSelectedFriends(List<User> selectedFriends) {
		this.selectedFriends = selectedFriends;
	}
	
	public Boolean getReply() {
		return reply;
	}

	public void setReply(Boolean reply) {
		this.reply = reply;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setSelectedFriends(SelectEvent evt){
		List<User> list = new ArrayList<>(evt.getSelectedObjects());
		setSelectedFriends(list);
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		initMessage();
		loadBinder();
	}
	
	

	/**
	 * Inicia a mensagem para visualização, pegando a mensagem selecionada.
	 */
	private void initMessage() {
		SentMessages message = arg.get("selectedReceivedMessage") == null ? null : (SentMessages) arg.get("selectedReceivedMessage");
		if(message != null){
			setEntity(message.getMessage());
		}
	}
	
	/**
	 * Busca a lista de amigos do usuário, chamando a ação correspondente no controlador.
	 * @see MessagesController#getFriends()
	 * @return Lista de amigos do usuário.
	 */
	@SuppressWarnings("unchecked")
	public List<User> getFriends(){
		return new ListModelList<User>((Collection<? extends User>) getControl().doAction("getFriends").getList());
	}
	
	/**
	 * Envia mensagem aos usuários selecionados.
	 * @return {@link Return}
	 */
	public Return sendMessages(){
		Return ret = getControl().doAction("sendMessages");
		if(ret.isValid()){
			if(getComponentById("modalEnviar") != null){
				getComponentById("modalEnviar").detach();
			} else {
				getComponentById("modalResponder").detach();
			}
			
		}
		return ret;
	}
	
	/**
	 * Chama a janela de envio de mensagem.
	 */
	public void callSendWindow(){
		callModalWindow("/paginas/mensagens/enviar.zul");
	}
	
	/**
	 * Chama a janela de resposta de mensagem.
	 */
	public void callReplyWindow(){
		setReply(true);
		callModalWindow("/paginas/mensagens/responder.zul");
	}
	
	@Override
	public MessagesController getControl() {
		return SpringFactory.getController("messagesController", MessagesController.class, ReflectionUtils.prepareDataForPersistence(this));
	}

	@Override
	public Messages getEntityObject() {
		return new Messages();
	}

}
