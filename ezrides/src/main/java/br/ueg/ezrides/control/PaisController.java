package br.ueg.ezrides.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.vexillum.control.GenericControl;
import br.ueg.ezrides.model.entitys.Country;

/**
 * Controlador respons�vel por cuidar das a��es relacionadas aos Pa�ses.
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
