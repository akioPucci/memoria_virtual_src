package br.usp.memoriavirtual.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Titulo;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirBemPatrimonialRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean
@RequestScoped
public class ExcluirBemPatrimonialMB {

	private String nome = "";
	private BemPatrimonial bemPatrimonial = null;
	private List<BemPatrimonial> bens = new ArrayList<BemPatrimonial>();
	@EJB
	private ExcluirBemPatrimonialRemote excluirBemPatrimonialEJB;

	public void listarBensPatrimoniais(AjaxBehaviorEvent e) {
		this.listarBensPatrimoniais();
	}

	public void listarBensPatrimoniais() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		
		try {
			this.bens = excluirBemPatrimonialEJB
					.listarBensPatrimoniais(this.nome);
			
			for(BemPatrimonial b : bens){
				for(Titulo t : b.getTitulos()){
					if(t.getValor().contains(this.nome)){
						b.getTitulos().set(0, t);
					}
				}
			}
			
			BemPatrimonial showAll = new BemPatrimonial();
			Titulo showAllTitulo = new Titulo();
			showAllTitulo.setValor(bundle.getString("listarTodos"));
			showAll.adicionarTitulo(showAllTitulo);
			this.bens.add(0, showAll);
			
		} catch (ModeloException m) {
			MensagensDeErro.getErrorMessage("excluirBemPatrimonialErroBemInexistente", "resultado");
			m.printStackTrace();
		}
	}
	
	public String selecionarBem(BemPatrimonial bem){
		
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		
		if(bem.getTitulos().get(0).getValor().equals(bundle.getString("listarTodos"))){
			this.nome = "";
			this.listarBensPatrimoniais();
			return null;
		}
		
		this.bemPatrimonial = bem;
		
		return "sucesso";
		
	}

	
	public String excluirBemPatrimonial(){
		
		try{
			this.bemPatrimonial = this.excluirBemPatrimonialEJB.recuperarDados(this.bemPatrimonial);
			this.excluirBemPatrimonialEJB.excluirBem(this.bemPatrimonial);
		}
		catch(ModeloException m){
			MensagensDeErro.getErrorMessage("excluirBemPatrimonialErroBemInexistente", "resultado");
			m.printStackTrace();
		}
		
		MensagensDeErro.getSucessMessage("excluirBemPatrimonialSucesso", "resultado");
		
		return "sucesso";
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BemPatrimonial getBemPatrimonial() {
		return bemPatrimonial;
	}

	public void setBemPatrimonial(BemPatrimonial bemPatrimonial) {
		this.bemPatrimonial = bemPatrimonial;
	}

	public List<BemPatrimonial> getBens() {
		return bens;
	}

	public void setBens(List<BemPatrimonial> bens) {
		this.bens = bens;
	}

	public ExcluirBemPatrimonialRemote getExcluirBemPatrimonialEJB() {
		return excluirBemPatrimonialEJB;
	}

	public void setExcluirBemPatrimonialEJB(
			ExcluirBemPatrimonialRemote excluirBemPatrimonialEJB) {
		this.excluirBemPatrimonialEJB = excluirBemPatrimonialEJB;
	}

}
