package br.ueg.ezrides.model.entitys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.vexillum.model.CommonEntityActivated;

/**
 * Classe que representa o envio de mensagens.
 * @author Fernando
 *
 */
@SuppressWarnings("serial")
@Entity
public class SentMessages extends CommonEntityActivated {
	
	/**
	 * Construtor padrão, que inicia a data/hora de envio com a data/hora atual.
	 */
	public SentMessages() {
		this.sentTime = new Date();
	}
	
	/**
	 * Representa a mensagem que está sendo enviada.
	 * @see Messages
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_message", unique=false, nullable=false, updatable=false)
	private Messages message;
	
	/**
	 * Usuário que a mensagem será enviada.
	 * @see User
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_destiny", unique=false, nullable=false, updatable=false)
	private User destiny;
	
	/**
	 * Data/hora de envio da mensagem.
	 */
	@Column(name="sent_time", unique=false, nullable=true, updatable=false)
	private Date sentTime;

	public Messages getMessage() {
		return message;
	}

	public void setMessage(Messages message) {
		this.message = message;
	}

	public User getDestiny() {
		return destiny;
	}

	public void setDestiny(User destiny) {
		this.destiny = destiny;
	}

	public Date getSentTime() {
		return sentTime;
	}

	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}
	
}
