package br.ueg.ezrides.view.validator;

import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;

import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.ZKUtils;

/**
 * Classe que valida as imagem do sistema.
 * @author Fernando
 *
 */
public class ImageValidator{
	
	/**
	 * {@link Media} que ser� validada.
	 */
	Media media;

	/**
	 * Construtor padr�o.
	 * @param media {@link Media} a ser validado.
	 */
	public ImageValidator(Media media) {
		this.media = media;
	}
	
	/**
	 * Faz a valida��o da a��o de upload de imagem.
	 * @return {@link Return}
	 */
	public Return upload(){
		Return ret = new Return(true);
		ret.concat(isImage());
		if(ret.isValid()){
			ret.concat(maxSize());
			ret.concat(contentType());
//			ret.concat(maxHeightAndWidth());
		}
		return ret;
	}
	
	/**
	 * Verifica se � uma imagem.
	 * @return {@link Return}
	 */
	private Return isImage(){
		Return ret = new Return(true);
		if(media.getContentType().indexOf("image") == -1){
			ret.concat(new Return(false, new Message(null, "Voc� apenas pode fazer o upload de imagens!")));
		}
		return ret;
	}
	
	/**
	 * Verifica o tamanho m�ximo da imagem.
	 * @return {@link Return}
	 */
	protected Return maxSize(){
		Return ret = new Return(true);
		if(media.getByteData().length > 1000*500){
			ret.concat(new Return(false, new Message(null, "Imagem n�o poder ter tamanho superior a 500KB!")));
		}
		return ret;
	}
	
	/**
	 * Verifica se a imagem � do e algum dos formatos permitidos.
	 * @return {@link Return}
	 */
	private Return contentType(){
		Return ret = new Return(true);
		if(!media.getFormat().equalsIgnoreCase("png") && !media.getFormat().equalsIgnoreCase("gif") && !media.getFormat().equalsIgnoreCase("jpg") && !media.getFormat().equalsIgnoreCase("jpeg")){
			ret.concat(new Return(false, new Message(null, "Imagem n�o suportada! A imagem dever ser JPG, PNG ou GIF!")));
		}
		return ret;
	}
	
	/**
	 * Verifica��o a resolu��o m�xima permitida.
	 * @return {@link Return}
	 */
	protected Return maxHeightAndWidth(){
		Return ret = new Return(true);
		try {
			AImage img = (media instanceof AImage) ? (AImage) media : new AImage("photo", ZKUtils.mediaToStream(media));
			if(img.getHeight() > 150 || img.getWidth() > 150){
				ret.concat(new Return(false, new Message(null, "Imagem n�o poder ter resolu��o superior a 150x150!")));
			}
		} catch (Exception e) {
			ret.concat(new ExceptionManager(e).treatException());
		}
		return ret;
	}	

}
