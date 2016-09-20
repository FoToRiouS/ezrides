package br.ueg.ezrides.model.entitys;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import br.com.vexillum.model.CommonEntityActivated;
import br.com.vexillum.model.annotations.Validate;
import br.com.vexillum.model.annotations.ValidatorClass;

/**
 * Classe que representa as mensagens, que são usadas nas Mensagens Pessoais do usuário.
 * @author Fernando
 *
 */
@SuppressWarnings("serial")
@ValidatorClass(validatorClass="br.ueg.ezrides.control.validator.MessagesValidator")
@Entity
public class Messages extends CommonEntityActivated {

	/**
	 * Usuário que enviou a mensagem.
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_owner", unique=false, nullable=false, updatable=false)
	private User owner;
	
	/**
	 * Título da mensagem.
	 */
	@Validate(notNull=false)
	@Column(name="title", unique=false, nullable=true, updatable=true, length=200)
	private String title;
	
	/**
	 * Corpo da mensagem.
	 */
	@Validate(notNull=true)
	@Column(name="message", unique=false, nullable=false, updatable=true, length=5000)
	private String bodyMessage;
	
	/**
	 * Lista que representa as mensagens que foram enviadas a partir desta.
	 * @see SentMessages
	 */
	@OneToMany(fetch=FetchType.LAZY, mappedBy="message", targetEntity=SentMessages.class)
	@Cascade(value=CascadeType.ALL)
	private List<SentMessages> sentMessages;

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBodyMessage() {
		return bodyMessage;
	}

	public void setBodyMessage(String bodyMessage) {
		this.bodyMessage = bodyMessage;
	}

	public List<SentMessages> getSentMessages() {
		if(sentMessages == null){
			sentMessages = new ArrayList<>();
		}
		return sentMessages;
	}

	public void setSentMessages(List<SentMessages> sentMessages) {
		this.sentMessages = sentMessages;
	}
	
}
