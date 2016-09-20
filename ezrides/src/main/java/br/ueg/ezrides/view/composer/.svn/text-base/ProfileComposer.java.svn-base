package br.ueg.ezrides.view.composer;

import java.io.InputStream;

import org.springframework.context.annotation.Scope;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;

import br.com.vexillum.control.FriendshipController;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.control.util.Attachment;
import br.com.vexillum.model.UserBasic;
import br.com.vexillum.util.ImageUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.com.vexillum.util.ZKUtils;
import br.ueg.ezrides.view.attachments.AttachmentMedia;
import br.ueg.ezrides.view.validator.ImageValidator;

/**
 * Controlador da tela do Perfil do usuário.
 * @author Fernando
 *
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class ProfileComposer extends UserComposer {

	/**
	 * Atributo que define se o usuário visualizado no perfil é o mesmo logado.
	 */
	private Boolean isMe;
	
	public Boolean getIsMe() {
		return isMe;
	}
	
	public Boolean getNotMe() {
		return !(isMe);
	}

	public void setIsMe(Boolean isMe) {
		this.isMe = isMe;
	}

	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);	
		if(!(haveIdOnRequest() && initUserById(Executions.getCurrent().getParameter("id")))){
			Executions.sendRedirect("/paginas/erros/error.zul");
		} 
		setIsMe(isMe());
		initData();
		validateAccess();
		loadBinder();
	}
	
	private void initData() {
		Boolean isFriend = isFriend();
		if(isFriend != null && isFriend){
			getComponentById("btnSolicitarAmizade").detach();
		} else if(isFriend != null) {
			getComponentById("rowPhones").detach();
			getComponentById("rowBirthDate").detach();
			getComponentById("rowLabelAddress").detach();
			getComponentById("rowCountryState").detach();
			getComponentById("rowAddress1").detach();
			getComponentById("rowAddress2").detach();
			getComponentById("rowCEP").detach();
		}
	}

	private Boolean isFriend() {
		FriendshipController controller = SpringFactory.getController("friendshipController", FriendshipController.class, null);
		return controller.isFriend(getEntity(), getUserInSession());
	}

	/**
	 * Evento que muda a imagem do perfil do usuário.
	 * @param event Evento disparado.
	 */
	public void changeProfileImage(UploadEvent event){
        Media media = event.getMedia();
        ImageValidator val = new ImageValidator(media);
        Return ret = val.upload();
        if(ret.isValid()){
                uploadProfileImages(media, entity);
                //imageProfile.setContent((AImage) media);
                Executions.sendRedirect("");
        }
        treatReturn(ret);
	}
	
	/**
	 * A partir de uma imagem, faz o upload linkado ao usuário.
	 * Faz o upload da imagem em 3 tamanhos: 150x150, 67x67, 34x34.
	 * Este upload é feito visando a visualização da iamgem em várias telas, desta forma, diminuindo a banda usada na visualização.
	 * @param file Arquivo a ser feito pelo upload.
	 * @param user Usuário associado.
	 * @return {@link Return}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Return uploadProfileImages(Media file, UserBasic user){
		Return ret = new Return(true);
		AImage image = (AImage) file;
		Attachment att = new AttachmentMedia();
		try {
			InputStream image1 = ZKUtils.mediaToStream(image);
			if(image.getHeight() > 150 || image.getWidth() > 150){
				image1 = ImageUtils.scaleImage(image1, 150, 150, image.getFormat());
			}
			att.uploadAttachment(image1, "image_profile", user);
			
			InputStream image2 = ZKUtils.mediaToStream(image);
			if(image.getHeight() > 67 || image.getWidth() > 67){
				image2 = ImageUtils.scaleImage(image2, 67, 67, image.getFormat());
			}
			att.uploadAttachment(image2, "image_profile_67x67", user);
			
			InputStream image3 = ZKUtils.mediaToStream(image);
			if(image.getHeight() > 34 || image.getWidth() > 34){
				image3 = ImageUtils.scaleImage(image3, 34, 34, image.getFormat());
			}
			att.uploadAttachment(image3, "image_profile_34x34", user);
			
		} catch (Exception e) {
			ret.concat(new ExceptionManager(e).treatException());
		}
		return ret;
	}
	
	/**
	 * Exclui a imagem do perfil do usuário.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void deleteProfileImage(){
		Attachment att = new AttachmentMedia();
        Return ret = new Return(true);
        if(ret.isValid()){
                att.deleteAttachment("image_profile", entity);
                Executions.sendRedirect("");
        }
        treatReturn(ret);
	}
	
	/**
	 * Valida se o perfil é público, caso não será redirecionado a página de erro.
	 */
	private void validateAccess(){
		if(!entity.getPerfilPublico() && !getIsMe()){
			Executions.sendRedirect("/paginas/erros/acessoprivado.zul");
		}
	}
	
}
