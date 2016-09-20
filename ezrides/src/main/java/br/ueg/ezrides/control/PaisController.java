package br.ueg.ezrides.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.vexillum.control.GenericControl;
import br.ueg.ezrides.model.entitys.Country;

/**
 * Controlador responsável por cuidar das ações relacionadas aos Países.
 * @see Country
 * @author fotorious
 *
 */
@Service
@Scope("session")
public class PaisController extends GenericControl<Country> {

	public PaisController() {
		super(Country.class);
	}

}
