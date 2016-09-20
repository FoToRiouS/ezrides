package br.ueg.ezrides.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.vexillum.control.FriendshipController;
import br.com.vexillum.model.Friendship;

/**
 * Controlador responsável por cuidar das ações relacionadas as Amizades.
 * @see Friendship
 * @author fotorious
 *
 */
@Service
@Scope("prototype")
public class FriendsController extends FriendshipController {
	
//	@Override
//	public Return searchFriends() {
//		User user = (User) data.get("user");
//		String searchKey = (String) data.get("searchKey");
//		
//		String sql = "FROM Friendship f "
//					+ "WHERE ((f.owner = '" + user.getId() + "' OR f.friend = '" + user.getId() + "') AND f.active = true) AND "
//					+ "((f.owner.name like '" + searchKey + "%' OR f.friend.name like '" + searchKey + "%') OR "
//					+ "(f.owner.email like '" + searchKey + "%' OR f.friend.email like '" + searchKey + "%') OR "
//					+ "(f.owner.city like '" + searchKey + "%' OR f.friend.city like '" + searchKey + "%'))";
//		data.put("sql", sql);
//		Return ret = searchByHQL();
//		ret.getList().remove(user);
//		return ret;
//	}

}
