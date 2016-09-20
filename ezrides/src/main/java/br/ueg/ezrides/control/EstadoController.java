package br.ueg.ezrides.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.vexillum.control.GenericControl;
import br.ueg.ezrides.model.entitys.State;

/**
 * Controlador respons�vel por cuidar das a��es relacionadas aos Estados.
 * @see State
 * @author fotorious
 *
 */
@Service
@Scope("session")
public class EstadoController extends GenericControl<State> {

	public EstadoController() {
		super(State.class);
	}

}
