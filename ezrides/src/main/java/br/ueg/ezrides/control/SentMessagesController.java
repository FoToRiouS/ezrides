package br.ueg.ezrides.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ueg.ezrides.model.entitys.SentMessages;

/**
 * Controlador respons�vel por cuidar das a��es relacionadas as Mensagens Enviadas.
 * @see SentMessages
 * @author fotorious
 *
 */
@Service
@Scope("prototype")
public class SentMessagesController extends BaseController<SentMessages> {

	public SentMessagesController() {
		super(SentMessages.class);
	}

}
