package br.ueg.ezrides.control;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.vexillum.util.Return;
import br.ueg.ezrides.model.entitys.Route;
import br.ueg.ezrides.model.entitys.RouteCategory;
import br.ueg.ezrides.model.entitys.RouteDaysOfWeek;
import br.ueg.ezrides.model.entitys.User;
import br.ueg.ezrides.model.enums.DaysOfWeek;

/**
 * Controlador responsável por cuidar das ações relacionadas as Rotas.
 * @see Route
 * @author fotorious
 *
 */
@Service
@Scope("prototype")
public class RouteController extends BaseController<Route> {

	public RouteController() {
		super(Route.class);
	}
	
	@Override
	public Return listAll() {
		String sql = "FROM " + classEntity.getSimpleName() + " WHERE active = true AND user = " + entity.getUser().getId() + " AND excludeByAbuse = false ORDER BY name";
		if(entity == null || entity.getActive() == null || !entity.getActive()){
			sql = sql.replace("active = true AND ", "");
		}
		data.put("sql", sql);
		return super.searchByHQL();
	}
	
	/**
	 * Adiciona um {@link DaysOfWeek} a lista de dias da semana que serão feitas de uma determinada rota.
	 * Este método antes de adicionar verifica em uma lista auxiliar se o {@link DaysOfWeek} já foi deletado anteriormente. Desta forma evitando criação desnecessário de linhas no banco de dados.
	 * @return {@link Return}
	 */
	@SuppressWarnings("unchecked")
	public Return addDaysOfWeek(){
		Return ret = new Return(true);
		List<DaysOfWeek> list = (List<DaysOfWeek>) data.get("listDaysOfWeek");
		List<RouteDaysOfWeek> aux = (List<RouteDaysOfWeek>) data.get("auxListDaysOfWeekDeleted");
		Route route = entity;
		
		for(Iterator<DaysOfWeek> i = list.iterator(); i.hasNext();){
			DaysOfWeek day = i.next();
			Boolean flag = true;
			for(Iterator<RouteDaysOfWeek> j = entity.getDaysOfWeek().iterator(); j.hasNext();){
				RouteDaysOfWeek rday = j.next();
				if(rday.equals(day)){
					flag = false;
					break;
				}
			}
			if(flag){
				Boolean flag2 = true;
				for(Iterator<RouteDaysOfWeek> j = aux.iterator(); j.hasNext();){
					RouteDaysOfWeek rd = j.next();
					if(rd.equals(day)){
						route.addDayOfWeek(rd);
						j.remove();
						flag2 = false;
						break;
					}
				}
				if(flag2){
					route.addDayOfWeek(day);
				}
			}
		}
		
		for(Iterator<RouteDaysOfWeek> i = entity.getDaysOfWeek().iterator(); i.hasNext();){
			RouteDaysOfWeek rday = i.next();
			Boolean flag = true;
			for(Iterator<DaysOfWeek> j = list.iterator(); j.hasNext();){
				DaysOfWeek day = j.next();
				if(rday.equals(day)){
					flag = false;
					break;
				}
			}
			if(flag){
				aux.add(rday);
				i.remove();
			}
		}
		return ret;
	}
	
	/**
	 * Busca as rotas baseadas em uma chave de busca, que será procurada no ponto final de uma rota. Também pesquisa tomando como critério a categoria da rota.
	 * Esta busca exclui as rotas do susuário logado.
	 * @return {@link Return}
	 */
	public Return searchRoutes(){
		Return ret = new Return(true);
		String searchKey = (String) data.get("searchField");
		RouteCategory category = (RouteCategory) data.get("searchCategory");
		Boolean disponibility = (Boolean) data.get("disponibility");
		User userLogged =  (User) data.get("userLogged");
		if((searchKey == null || searchKey.isEmpty()) || !(searchKey.indexOf("%") != 0) || !(searchKey.indexOf("%") != searchKey.length() - 1)){
			return ret;
		}
		String sql = "FROM Route WHERE (finalPoint like '%" + searchKey + "%' OR initialPoint like '%" + searchKey + "%') ";
		if(category != null)
			sql += "AND category = " + category.getId() + " ";
		if(!disponibility){
			sql += "AND disponibility = true ";
		} 
		sql += "AND user <> "+ userLogged.getId() + " AND active = true ORDER BY name";
		data.put("sql", sql);
		return super.searchByHQL();
	}
	
	public Return excludeByAbuse(Route entity){
		entity.setExcludeByAbuse(true);
		return deactivate(entity);
	}

}
