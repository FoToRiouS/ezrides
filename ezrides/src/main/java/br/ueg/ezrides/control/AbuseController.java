package br.ueg.ezrides.control;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.vexillum.control.manager.EmailManager;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.ueg.ezrides.model.entitys.Abuse;
import br.ueg.ezrides.model.entitys.Route;

/**
 * Controlador reponsável pelas ações de denúncia.
 * @see Abuse
 * @author fotorious
 *
 */
@Service
@Scope("prototype")
public class AbuseController extends BaseController<Abuse> {

	/**
	 * Construtor padrão.
	 */
	public AbuseController() {
		super(Abuse.class);
	}
	
	/* (non-Javadoc)
	 * @see br.com.vexillum.control.GenericControl#save()
	 */
	@Override
	public Return save() {
		Return ret = super.save();
		if(ret.isValid()){
			ret.concat(sendEmailToDenounced());
		}
		return ret;
	}
	
	/**
	 * Método responsável por mandar email ao usuário denunciado.
	 * @return {@link Return}
	 */
	private Return sendEmailToDenounced() {
		EmailManager email = new EmailManager("HtmlEmail");
		return email.sendEmail(getEntity().getRoute().getUser().getEmail(), getEmailProperties().getKey("abuse_subject"), getAbuseMessage());
	}

	/**
	 * Criação da mensagem para envio de email pelo método {@link AbuseController#sendEmailToDenounced()}
	 * @return Mensagem a ser enviada por email.
	 */
	private String getAbuseMessage(){
		return "<html>" +
				"<style>p {font-size: 15px;}</style>" +
				"<p>Sua rota foi denunciada:</p>" +
				"<p><b>Nome:</b> " + getEntity().getRoute().getName() + "<br>" +
				"<b>Motivo:</b> " + getEntity().getReason() + "</p>" +
				"<br>" +
				"</html>";
	}
	
	
	/**
	 * Lista todas as denúncias ordenadas pela data.
	 * @return
	 */
	public Return listAllGrowing() {
		String sql = "FROM Abuse WHERE active = true ORDER BY abuseDate";
		data.put("sql", sql);
		return super.searchByHQL();
	}
	
	@Override
	public Return deactivate() {
		Return ret = super.deactivate();
		if(ret.isValid()){
			EmailManager email = new EmailManager("HtmlEmail");
			return email.sendEmail(getEntity().getRoute().getUser().getEmail(), getEmailProperties().getKey("retification_subject"), getRetificationMessage());
		}
		return ret;
	}
	
	
	/**
	 * Criação da mensagem para envio no momento em que um Administrador retirar a denúncia da rota de um usuário.
	 * @return
	 */
	private String getRetificationMessage(){
		return "<html>" +
				"<style>p {font-size: 15px;}</style>" +
				"<p>O administrador retirou a denúncia de sua rota!</p>" +
				"<p><b>Nome da Rota:</b> " + getEntity().getRoute().getName() + "</p>" +
				"<br>" +
				"</html>";
	}
	
	/**
	 * Método responsável por desativar uma rota e todas a denúncias a eles associada.
	 * @return
	 */
	public Return deactivateRoute(){
		Return ret = new Return(true);
		setEntity((Abuse) getData().get("selectedEntity"));
		RouteController controller = SpringFactory.getController("routeController", RouteController.class, null);
		ret.concat(controller.excludeByAbuse(getEntity().getRoute()));
		if(ret.isValid()){
			List<Abuse> list = getAbusesList(getEntity().getRoute());
			for (Abuse abuse : list) {
				ret.concat(deactivate(abuse));
			}
			if(ret.isValid()){
				EmailManager email = new EmailManager("HtmlEmail");
				ret.concat(email.sendEmail(getEntity().getRoute().getUser().getEmail(), getEmailProperties().getKey("abuse_deactivateroute_subject"), getDeactivationMessage()));
			}
		}
		return ret;
	}

	/**
	 * Criação da mensagem para envio no momento em que um Administrador desativar a rota de um usuário devido a uma denúncia.
	 * @return
	 */
	private String getDeactivationMessage() {
		return "<html>" +
				"<style>p {font-size: 15px;}</style>" +
				"<p>Sua rota foi desativada do sistema devido a uma denúncia!</p>" +
				"<p><b>Nome:</b> " + getEntity().getRoute().getName() + "<br>" +
				"<b>Motivo:</b> " + getEntity().getReason() + "</p>" +
				"<br>" +
				"</html>";
	}

	/**
	 * Busca a lista de denúncias baseado em uma rota.
	 * @param route A rota do qual as denúncias serão buscadas.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Abuse> getAbusesList(Route route) {
		String hql = "FROM Abuse WHERE active = true AND route = " + route.getId();
		getData().put("sql", hql);
		return (List<Abuse>) searchByHQL().getList();
	}
	
}
