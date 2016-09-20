package br.ueg.ezrides.view.composer;

import java.io.File;
import java.util.List;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Row;

import br.com.vexillum.control.GenericControl;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.control.util.Attachment;
import br.com.vexillum.model.ICommonEntity;
import br.com.vexillum.util.Return;
import br.com.vexillum.vexsocial.view.composer.SocialComposer;
import br.ueg.ezrides.model.entitys.User;
import br.ueg.ezrides.view.attachments.AttachmentMedia;

@SuppressWarnings("serial")
public abstract class BaseComposerSocial<E extends ICommonEntity, G extends GenericControl<E>> extends SocialComposer<E, G> {

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		setUserLogged(getUserInSession());
		if(haveIdOnRequest()){
			update = true;
			disableComponentsNoUpdatables(comp);
		}
	}
	
	/**
	 * Redireciona para um perfil de usuário.
	 * @param id Id do usuário.
	 */
	public static void redirectToProfile(Long id) {
		Executions.sendRedirect("/paginas/perfil/visualizar.zul?id=" + id);
	}

	/**
	 * Redireciona ao perfil do usuário logado na sessão.
	 */
	public static void redirectToProfileSession() {
		redirectToProfile(getUserInSession().getId());
	}
	
	/**
	 * Carrega as imagens de um grid de usuários.
	 * @param grid Grid que contém a lista de usuários.
	 */
	public void loadImages(Grid grid){
		List<Component> list = grid.getRows().getChildren();
		for(Component row : list){
			User user = ((Row)row).getValue();
			showImageProfile((Image) getComponentByType(row, "image"), user, "67x67");
		}
	}
	
	/**
	 * Redireciona ao pefil de um usuário, baseado em um componente.
	 * @param comp Componente onde o usuário está inserido.
	 */
	public void redirectToProfile(Component comp) {
		Row row = (Row) super.getParent(comp, "row");
		User user = row.getValue();
		redirectToProfile(user.getId());
	}
	
	/**
	 * Carrega a imagem a ser exibida no perfil do usuário a partir de um component.
	 * @param comp Component que receberá a imagem.
	 * @param widthHeight Altura e largura da imagem no padrão WW_HH.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void showImageProfile(Image comp, String widthHeight) {
		Attachment att = new AttachmentMedia();
		String name = "image_profile" + ((widthHeight != null && !widthHeight.isEmpty()) ? ("_" + widthHeight) : "");
		try {
			File image = att.getAttachment(name, getUserInSession());
			if (image != null) {
				comp.setContent(new AImage(image));
			}
		} catch (Exception e) {
			new ExceptionManager(e).treatException();
		}
	}
	
	/**
	 * Carrega a imagem a ser exibida na pesquisa de usuário e de amigos.
	 * @param comp Component que receberá a imagem.
	 * @param user Usuário a ser carregada a imagem.
	 * @param widthHeight Altura e largura da imagem no padrão WW_HH.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void showImageProfile(Image comp, Object user, String widthHeight) {
		Attachment att = new AttachmentMedia();
		String name = "image_profile" + ((widthHeight != null && !widthHeight.isEmpty()) ? ("_" + widthHeight) : "");
		try {
			File image = att.getAttachment(name, (User) user);
			if (image != null) {
				comp.setContent(new AImage(image));
			}
		} catch (Exception e) {
			new ExceptionManager(e).treatException();
		}
	}
	
	/**
	 * Faz a requisição de amizade.
	 * @return {@link Return}
	 */
	public Return requestFriendship(){
		return saveFriendship(getUserInSession(), getFriend());
	}
	
	/**
	 * Pega uma amigo.
	 * @return Usuário amigo.
	 */
	protected User getFriend(){
		return (User) getEntity();
	}
	
}
