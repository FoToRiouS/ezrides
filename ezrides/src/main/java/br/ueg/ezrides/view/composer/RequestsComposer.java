package br.ueg.ezrides.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Row;

import br.com.vexillum.model.Friendship;
import br.com.vexillum.util.HibernateUtils;

/**
 * Controla a tela requisições pendentes e em aberto de amizade.
 * @author Fernando
 *
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class RequestsComposer extends FriendshipComposer {

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		setPendents(getListPendents()); 
		setOpenRequests(getListOpenRequests());
		loadBinder();
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.ezrides.view.composer.FriendshipComposer#initLists()
	 */
	@Override
	protected void initLists() {
		HibernateUtils.initialize(getPendents());
		HibernateUtils.initialize(getOpenRequests());
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.ezrides.view.composer.BaseComposer#redirectToProfile(org.zkoss.zk.ui.Component)
	 */
	@Override
	public void redirectToProfile(Component comp) {
		Row row = (Row) super.getParent(comp, "row");
		Friendship fri = row.getValue();
		redirectToProfile(fri.getFriend().getId());
	}
	
}
