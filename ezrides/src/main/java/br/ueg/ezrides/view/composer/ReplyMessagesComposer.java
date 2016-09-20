package br.ueg.ezrides.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;

import br.ueg.ezrides.model.entitys.Messages;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class ReplyMessagesComposer extends MessagesComposer {

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		initReply();
		loadBinder();
	}
	
	/**
	 * Seta a mensagem de envio com para parâmetros da mensagem que veio para resposta.
	 */
	private void initReply() {
		Messages message = arg.get("entity") == null ? null : (Messages) arg.get("entity");
		if(message != null){
			getEntity().setTitle("RE: " + message.getTitle());
			getSelectedFriends().add(message.getOwner());
		}
	}
}
