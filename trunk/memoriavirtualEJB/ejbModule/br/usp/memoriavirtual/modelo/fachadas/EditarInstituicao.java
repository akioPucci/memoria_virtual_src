package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;

@Stateless (mappedName = "EditarInstituicao")
public class EditarInstituicao implements EditarInstituicaoRemote{
	
	private EntityManager entityManager;

	@Override
	public String editarInstituicao(String velhoNome, String novoNome, String novoEmail, String novoLocalizacao, String novoEndereco, String novoCidade, String novoEstado, String novoCep, String novoTelefone) {
		
		return null;
	}

	@Override
    public List<Instituicao> getInstituicoes(Grupo grupo, Usuario usuario) {
		
		List<Instituicao> instituicoes = new ArrayList<Instituicao>();
		Query query = this.entityManager.createQuery("select a from Acesso a where a.usuario = :usuario and a.grupo = :grupo");
		query.setParameter("usuario", usuario);
		query.setParameter("grupo", grupo);

		@SuppressWarnings("unchecked")
		List<Acesso> acessos = (List<Acesso>) query.getResultList();
		for (Acesso acesso : acessos) {
		    instituicoes.add(acesso.getInstituicao());
		}

		return instituicoes;
	    }

	@Override
	@SuppressWarnings("unchecked")
    public List<Instituicao> getInstituicoes() {
	List<Instituicao> instituicoes = null;
	Query query = this.entityManager.createQuery("select i from Instituicao i");
	instituicoes = (List<Instituicao>) query.getResultList();

	return instituicoes;
    }
	
}