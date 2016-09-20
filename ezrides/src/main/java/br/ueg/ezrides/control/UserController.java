package br.ueg.ezrides.control;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.vexillum.configuration.Properties;
import br.com.vexillum.control.UserBasicControl;
import br.com.vexillum.control.manager.EmailManager;
import br.com.vexillum.model.Friendship;
import br.com.vexillum.util.EncryptUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.ueg.ezrides.model.entitys.User;

/**
 * Controlador responsável por cuidar das ações relacionadas aos Usuários.
 * @see User
 * @author fotorious
 *
 */
@Service
@Scope("prototype")
public class UserController extends UserBasicControl<User> {

	/**
	 * Properties com as informações que serão usadas no email do sistema.
	 */
	private Properties emailProperties;
	
	public UserController() {
		super(User.class);
	}
	
	public Properties getEmailProperties() {
		if(emailProperties == null){
			emailProperties = SpringFactory.getInstance().getBean("emailProperties", Properties.class);
		}
		return emailProperties;
	}

	public void setEmailProperties(Properties emailProperties) {
		this.emailProperties = emailProperties;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Return save() {
		Return ret = new Return(true);
		getEntity().setPassword(EncryptUtils.encryptOnSHA512(entity.getPassword()));
		getEntity().setVerificationCode(generateVerificationCode());
		getEntity().setUltimoAcesso(new Date());
		
		String refEmail = getData().get("referenceEmail") == null ? null : (String) getData().get("referenceEmail");
		if(refEmail != null){
			getData().put("sql", "FROM User u WHERE u.email = '" + refEmail + "'");
			List<User> list = (List<User>) searchByHQL().getList();
			if(list != null && !list.isEmpty()){
				ret.concat(createFriendship(list.get(0)));
			} else {
				ret = super.save();
			}
		} else {
			ret = super.save();
		}
		
		if(ret.isValid()){
			EmailManager email = new EmailManager("HtmlEmail");
			ret.concat(email.sendEmail(entity.getEmail(), getEmailProperties().getKey("inncludeemail_subject"), getConfirmationMessage(entity)));
		}
		return ret;
	}
	
	private Return createFriendship(User user) {
		Return ret = new Return(true);
		
		if(user != null){
			Friendship friend = new Friendship();
			friend.setActive(true);
			friend.setOwner(user);
			friend.setFriend(getEntity());
			
			FriendsController controller = SpringFactory.getController("friendsController", FriendsController.class, null);
			return controller.save(friend);
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see br.com.vexillum.control.GenericControl#activate()
	 */
	public Return activate(){
		Return ret = null;
		
		String email = (String) data.get("user");
		String verifCode = (String) data.get("verificationCode");
		
		ret = verifyValidationCode(email, verifCode);
		
		if(ret.isValid()){
			entity = (User) ((ret.getList() == null || ret.getList().isEmpty()) ? null : ret.getList().get(0));
			entity.setActive(true);
			entity.setVerificationCode(null);
			ret.concat(update());
		}
		
		return ret;
	}

	/**
	 * Criptografa a senha de usuário e atualiza no banco de dados.
	 * @return {@link Return}
	 */
	public Return updatePassword(){
		entity.setPassword(EncryptUtils.encryptOnSHA512((String)data.get("novaSenha")));
		return super.update();
	}
	
	/* (non-Javadoc)
	 * @see br.com.vexillum.control.GenericControl#deactivate()
	 */
	public Return deactivate(){
		entity.setActive(false);
		return super.update();
	}
	
	/**
	 * Procura os usuários baseado em uma chave de pesquisa, que será procurada no Nome, Email, Cidade do usuário.
	 * @see User
	 * @return {@link Return}
	 */
	public Return searchUsers(){
		Return ret = new Return(true);
		String searchKey = (String) data.get("searchField");
		User userLogged =  (User) data.get("userLogged");
		if(searchKey == null || searchKey.isEmpty() || !(searchKey.indexOf("%") != 0) || !(searchKey.indexOf("%") != searchKey.length() - 1)){
			return ret;
		}
		String sql = "FROM User WHERE (name like '" + searchKey + "%' OR email like '" + searchKey + "%' OR cidade like '" + searchKey + "%') "
					+ "AND id <> " + userLogged.getId() + " AND active = true AND perfilPublico = true ORDER BY name";
		data.put("sql", sql);
		return super.searchByHQL();
	}
	
	/**
	 * Envia o email para a recuperação da senha usuário.
	 * @return {@link Return}
	 */
	public Return recoveryPassword(){
		Return ret = new Return(true);
		User user = (User) data.get("userEmail");
		String verificationCode = generateVerificationCode();
		
		user.setVerificationCode(verificationCode);
		entity = user;
		ret.concat(update());
		
		if(ret.isValid()){
			EmailManager email = new EmailManager("HtmlEmail");
			ret.concat(email.sendEmail(user.getEmail(), getEmailProperties().getKey("recoverypassword_subject"), getMessageRecovery(user)));
		}
		
		return ret;
	}
	
	/**
	 * Gera um código de verificação, que será usado no envio de emails.
	 * @return Código de verificação.
	 */
	private String generateVerificationCode() {
		String verificationCode = "";
		Random rnd = new Random();
		for (int i = 0; i < 30; i++) {
			verificationCode += (char)(rnd.nextInt(26) + 65);
		}
		return verificationCode;
	}

	/**
	 * Gera a mensagem para a recuperação de senha de um usuário.
	 * @param user Usuário para referência de informações.
	 * @return Mensagem a ser usada no email.
	 */
	private String getMessageRecovery(User user){
		return "<html>" +
				"<style>p {font-size: 15px;}</style>" +
				"<p>Você solicitou a mudança de sua senha, acesse o link abaixo e mude sua senha:</p>" +
				"<p><a href='" + getEmailProperties().getKey("recoverypassword_url") + "?verif=" + user.getVerificationCode() + 
				"&user=" + user.getEmail() + "&modif=true'>Clique aqui para mudar de senha!</a></p>" +
				"<br>" +
				"<p>Caso você não tenha solicitado esta mudança, desconsidere este email!</p>" +
				"</html>";
	}
	
	/**
	 * Gera a mensagem para ativação no momento de cadastro de um usuário.
	 * @param user Usuário para referência de informações.
	 * @return Mensagem a ser usada no email.
	 */
	private String getConfirmationMessage(User user){
		return "<html>" +
				"<style>p {font-size: 15px;}</style>" +
				"<p>Bem vindo a rede social EZ-Rides</p>" +
				"<p>Para confirmar o seu cadastro, acesse o link abaixo:</p>" +
				"<p><a href='" + getEmailProperties().getKey("includeconfirmation_url") + "?verif=" + user.getVerificationCode() + 
				"&user=" + user.getEmail() + "'>Clique aqui para ativar seu usuário!</a></p>" +
				"<br>" +
				"<p>Caso você não tenha se cadastrado, desconsidere este email!</p>" +
				"</html>";
	}
	
	/**
	 * Gera a mensagem para convidar uma pessoa.
	 * @return Mensagem a ser usada no email.
	 */
	private String getInvitationMessage(){
		return "<html>" +
				"<style>p {font-size: 15px;}</style>" +
				"<p>Venha fazer parte da rede social EZ-Rides</p>" +
				"<p>Para se cadastrar, acesse o link abaixo:</p>" +
				"<p><a href='" + getEmailProperties().getKey("invite_url") + ((User) getData().get("userLogged")).getEmail() + "'>Clique aqui para se cadastrar!</a></p>" +
				"<br>" +
				"</html>";
	}

	/**
	 * Envia um email para um email, convidado-o para a EZ-Rides.
	 * @return {@link Return}
	 */
	public Return inviteUser(){
		String inviteEmail = (String) getData().get("inviteEmail");
		EmailManager email = new EmailManager("HtmlEmail");
		return email.sendEmail(inviteEmail, getEmailProperties().getKey("invite_subject"), getInvitationMessage());
	}
	
	/**
	 * Atualiza a senha de um usuário, a partir do email enviado para a recuperação de senha.
	 * <b>OBS:</b> Seta a o código de verificação null.
	 * @return {@link Return}
	 */
	public Return updateRecoveryPassword(){
		Return ret = new Return(true);
		User user = entity;
		String password = EncryptUtils.encryptOnSHA512((String) data.get("recoveryPassword"));
		
		user.setPassword(password);
		user.setVerificationCode(null);
		
		ret.concat(update());
		
		return ret;
	}
	
	/**
	 * Verifica se o código de verificação corresponde ao email.
	 * @param email Email a ser verificado.
	 * @param verifCode Código de Verificação.
	 * @return {@link Return}
	 */
	public Return verifyValidationCode(String email, String verifCode){
		Return ret = null;
		
		if(email != null && verifCode != null){
			data.put("sql", "FROM User WHERE email = '" + email + "'");
			ret = searchByHQL();
			entity = (User) ((ret.getList() == null || ret.getList().isEmpty()) ? null : ret.getList().get(0));
			if(entity != null && entity.getVerificationCode() != null){
				ret.concat(new Return(entity.getVerificationCode().equals(verifCode)));
			} else {
				ret.concat(new Return(false));
			}
		}
		
		return ret;
	}
	
	@Override
	public Return listAll() {
		String sql = "FROM User WHERE active = true ORDER BY name";
		if(data.get("entity") == null || ((User) data.get("entity")).getActive() == null || !((User) data.get("entity")).getActive()){
			sql = sql.replace(" WHERE active = true", "");
		}
		data.put("sql", sql);
		return super.searchByHQL();
	}
}
