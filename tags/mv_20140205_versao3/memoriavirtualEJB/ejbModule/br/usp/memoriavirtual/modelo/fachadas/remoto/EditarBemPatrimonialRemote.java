package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

public interface EditarBemPatrimonialRemote {

	public List<BemPatrimonial> listarBensPatrimoniais(String strDeBusca)
			throws ModeloException;

	public void editarBemPatrimonial(BemPatrimonial bem) throws ModeloException;
}
