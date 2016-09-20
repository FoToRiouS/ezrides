package br.ueg.ezrides.view.attachments;

import java.io.File;
import java.io.InputStream;

import org.zkoss.io.Files;

import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.control.util.Attachment;
import br.com.vexillum.util.Return;
import br.ueg.ezrides.model.entitys.User;

/**
 * Classe responsável por salvar, pegar e deletar arquivos do servidor.
 * @author fotorious
 *
 */
public class AttachmentMedia implements Attachment<InputStream, User> {

	final String FOLDERATTACHMENTS = "profiles";
//	final String PATH = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
	final String PATH = "C:" + File.separator + "Applications" + File.separator + "ezrides";
	
	@Override
	public Return uploadAttachment(InputStream file, String name, User user) {
		Return ret = new Return(true);
		try {
			Files.copy(new File(PATH + File.separator + FOLDERATTACHMENTS + File.separator + user.getId() + File.separator + name), file);
		} catch (Exception e) {
			ret.concat(new ExceptionManager(e).treatException());
		}
		return ret;		
	}

	@Override
	public Return deleteAttachment(String name, User user) {
		Return ret = new Return(true);
		try {
			File f = getAttachment(name, user);
			if(f == null || f.exists()){
				f.delete();
			}	
		} catch (Exception e) {
			ret.concat(new ExceptionManager(e).treatException());
		}
		return ret;
	}

	@Override
	public File getAttachment(String name, User user) {
		if(user == null) return null;
		File f = new File(PATH + File.separator + FOLDERATTACHMENTS + File.separator + user.getId() + File.separator + name); 
		if(!f.exists()) return null;
		return f;
	}
	
}
