package br.usp.memoriavirtual.modelo.fachadas;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.UtilMultimidiaRemote;
import br.usp.memoriavirtual.utils.MVModeloCamposMultimidia;
import br.usp.memoriavirtual.utils.MVModeloStatusAprovacao;

@Stateless(mappedName = "ExcluirBemPatrimonial")
public class ExcluirBemPatrimonial implements ExcluirBemPatrimonialRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@EJB
	private UtilMultimidiaRemote utilMultimidiaEJB;

	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuariosAprovadores(Instituicao instituicao, Usuario usuario) throws ModeloException {

		try {
			Grupo grupo = new Grupo("GERENTE");
			List<Usuario> usuarios;
			Query query;
			query = this.entityManager.createQuery("SELECT a.usuario FROM Acesso a WHERE "
					+ "a.grupo = :grupo AND a.instituicao = :instituicao AND a.validade = true AND "
					+ "a.usuario <> :usuario");
			query.setParameter("grupo", grupo);
			query.setParameter("instituicao", instituicao);
			query.setParameter("usuario", usuario);

			usuarios = (List<Usuario>) query.getResultList();
			
			query = this.entityManager.createQuery("SELECT u FROM Usuario u WHERE "
					+ "u.administrador = true AND u <> :usuario");
			query.setParameter("usuario", usuario);
			usuarios.addAll((List<Usuario>) query.getResultList());
			
			return usuarios;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModeloException(e);
		}

	}

	@Override
	public void excluir(long id) throws ModeloException {

		try {

			BemPatrimonial bemPatrimonial = this.entityManager.find(BemPatrimonial.class, id);

			for (MVModeloCamposMultimidia midiaGenerica : utilMultimidiaEJB.listarCampos(bemPatrimonial
					.getContainerMultimidia())) {
				Multimidia midia = this.entityManager.find(Multimidia.class, midiaGenerica.getId());
				this.entityManager.remove(midia);
			}
			
			this.entityManager.remove(bemPatrimonial.getContainerMultimidia());

			this.entityManager.remove(bemPatrimonial);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModeloException(e);
		}
	}
	
	@Override
	public long solicitarExclusao(BemPatrimonial bem, Aprovacao aprovacao) {
		bem.setExterno(false);
		bem.setRevisao(false);
		entityManager.persist(aprovacao);
		return aprovacao.getId();
	}

	@Override
	public void aprovarExclusao(BemPatrimonial bem, Aprovacao aprovacao) throws ModeloException {
		aprovacao.setStatus(MVModeloStatusAprovacao.aprovada);
		aprovacao.setAlteradaEm(new Date());
		entityManager.merge(aprovacao);
		
		this.excluir(bem.getId());
	}
	
	@Override
	public void negarExclusao(BemPatrimonial bem, Aprovacao aprovacao)  throws ModeloException{
		aprovacao.setStatus(MVModeloStatusAprovacao.negada);
		aprovacao.setAlteradaEm(new Date());
		entityManager.merge(aprovacao);
		entityManager.merge(bem);
	}
}
