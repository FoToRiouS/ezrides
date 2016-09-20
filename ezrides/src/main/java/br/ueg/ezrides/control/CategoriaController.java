package br.ueg.ezrides.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.vexillum.control.GenericControl;
import br.com.vexillum.model.Category;

/**
 * Controlador responsável por cuidar das ações relacionadas a Categoria.
 * @see Category
 * @author fotorious
 *
 */
@Service
@Scope("session")
public class CategoriaController extends GenericControl<Category> {

	public CategoriaController() {
		super(Category.class);
	}

}
