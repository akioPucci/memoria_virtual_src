package br.usp.memoriavirtual.controle;

import java.util.ArrayList;
import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.Multimidia;

public interface BeanComMidia {
	
	/**
	 * 
	 * @return Cole��o de objetos Multim�dia
	 */
	public List<Multimidia> recuperaColecaoMidia();
	/**
	 *Adicionar Objetos Multimidia 
	 */
	public void adicionarMidia (Multimidia midia);
	/**
	 *Remover Objetos Multimidia 
	 */
	public String removeMidia(Multimidia midia);
	public String removeMidia(int index);
	public ArrayList<Integer> getApresentaMidias() ;
	public void setApresentaMidias(ArrayList<Integer> apresentaMidias);
	public boolean  isRenderCell(int index) ;
	
}
