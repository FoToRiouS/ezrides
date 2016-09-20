package br.ueg.ezrides.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

import br.com.vexillum.util.Return;
import br.ueg.ezrides.model.entitys.SentMessages;
import br.ueg.ezrides.model.entitys.User;

/**
 * Controlador das telas que mostra a lista de mensagens.
 * @author Fernando
 *
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class ReceivedMessagesComposer extends MessagesComposer {

	/**
	 * Lista das mensagens recebidas.
	 */
	private List<SentMessages> receivedMessages;
	
	/**
	 * Mensagem recebida selecionada.
	 */
	private SentMessages selectedReceivedMessage;
	
	public List<SentMessages> getReceivedMessages() {
		return receivedMessages;
	}

	public void setReceivedMessages(List<SentMessages> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	public SentMessages getSelectedReceivedMessage() {
		return selectedReceivedMessage;
	}

	public void setSelectedReceivedMessage(SentMessages selectedReceivedMessage) {
		this.selectedReceivedMessage = selectedReceivedMessage;
	}
	
	/**
	 * Chama a janela de visualização de uma mensagem selecionada.
	 */
	public void callVisualizeWindow(){
		callModalWindow("/paginas/mensagens/visualizar.zul");
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		initReceivedMessages();
		loadBinder();
	}
	
	/**
	 * Mostra a janela de confirmação para a desativação de uma mensagem recebida.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void showSentMessageDeactivateConfirmation() {
		Return ret = validateSelectedSentMessage();

		EventListener evt = new EventListener() {
			public void onEvent(Event evt) throws InterruptedException {
				if (evt.getName().equals("onOK")) {
					doAction("deactivateSentMessage");
				}
			}
		};

		if (ret.isValid()) {
			showWindowConfirmation("Tem certeza que deseja excluir a mensagem " + getSelectedReceivedMessage().getMessage().getTitle() + "?", evt);
		} else {
			treatReturn(ret);
		}
	}
	
	/**
	 * Desativa uma mensagem recebida selecionada, chamando a ação correspondente no controlador.
	 * @return {@link Return}
	 */
	public Return deactivateSentMessage() {
		Return ret = getControl().doAction("deactivateSentMessage");
		setSelectedReceivedMessage(null);
		initReceivedMessages(); 
		loadBinder();
		return ret;
	}
	
	/**
	 * Valida se tem uma mensagem recebida selecionada.
	 * @return {@link Return}
	 */
	private Return validateSelectedSentMessage() {
		Return ret = new Return(true);
		if (getSelectedReceivedMessage() == null) {
			ret = new Return(false, getSelectedErrorMessage(SentMessages.class.getSimpleName()));
		}
		return ret;
	}

	/**
	 * Inicia as mensagens recebidas do usuário logado na sessão. 
	 */
	private void initReceivedMessages(){
		setReceivedMessages(getControl().getReceivedMessages((User) getUserInSession()));
	}
	
}
