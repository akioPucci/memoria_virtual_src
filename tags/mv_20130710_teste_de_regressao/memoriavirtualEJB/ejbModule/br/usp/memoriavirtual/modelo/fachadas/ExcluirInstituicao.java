package br.usp.memoriavirtual.modelo.fachadas;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.ItemAuditoria;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fabricas.remoto.AuditoriaFabricaRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

/**
 * @author MAC
 */

@Stateless(mappedName = "ExcluirInstituicao")
public class ExcluirInstituicao implements ExcluirInstituicaoRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	MemoriaVirtualRemote memoriaVirtualEJB;
	AuditoriaFabricaRemote autoriaFabricaEJB;

	/**
	 * Construtor Padrão, não leva parâmetros
	 */
	public ExcluirInstituicao() {

	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarAdministradores() throws ModeloException {

		List<Usuario> administradores;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM Usuario a WHERE a.administrador = TRUE ORDER BY a.id ");

		try {
			administradores = (List<Usuario>) query.getResultList();
			return administradores;
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}

	/**
	 * Metodo auxiliar para recuperar usuario ligado a determinada
	 * institui��o
	 * 
	 * @param instituicao
	 *            instituicao
	 * @param grupo
	 *            Grupo ao qual o usuario pertence
	 * @return Usuario pertencente a referido grupo vinculado a referida
	 *         institui��o
	 * @throws ModeloException
	 *             Em caso de erro
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> recuperarGerentesdaInstituicao(Instituicao instituicao,
			boolean b) throws ModeloException {
		
		
		
		Grupo grupo = new Grupo("GERENTE");
		List<Usuario> usuarios;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a.usuario FROM Acesso a WHERE  a.grupo = :grupo AND a.instituicao = :instituicao AND a.validade = :b");
		query.setParameter("grupo", grupo);
		query.setParameter("instituicao", instituicao);
		query.setParameter("b", b);

		try {
			usuarios = (List<Usuario>) query.getResultList();
			return usuarios;
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}

	public Usuario getUsuario(String coluna, String parametro)
			throws ModeloException {
		Usuario usuario;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM Usuario a WHERE  a." + coluna
						+ " = :parametro ");
		query.setParameter("parametro", parametro);
		try {
			usuario = (Usuario) query.getSingleResult();
			return usuario;
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}

	public Aprovacao recuperarAprovacao(long chave)
			throws ModeloException {
		Aprovacao aprovacao;
		
		aprovacao = this.entityManager.find(Aprovacao.class, chave);
		
		return aprovacao;
	}

	public void excluirAprovacao(Aprovacao aprovacao) {
		Query query;
		query = this.entityManager
				.createQuery("DELETE  FROM Aprovacao a WHERE a = :aprovacao ");
		query.setParameter("aprovacao", aprovacao);
		query.executeUpdate();

	}

	public void validarExclusaoInstituicao(Instituicao instituicao,
			boolean acao, boolean flagAcesso) throws ModeloException {
		// Se acao verdadeira a instituicao e definitivamente excluida do
		// sistema
		if (acao) {
			instituicao = this.entityManager.find(Instituicao.class,
					instituicao.getId());
			if (flagAcesso) {
				Query query;
				query = this.entityManager
						.createQuery("DELETE  FROM Acesso a WHERE a.instituicao = :instituicao ");
				query.setParameter("instituicao", instituicao);
				query.executeUpdate();
			}
			try {
				this.entityManager.remove(instituicao);
			} catch (Exception e) {
				throw new ModeloException(e);
			}
		} else {// Caso Acao falso a instituicao volta a funcionar normalmente
			this.marcarInstituicaoExcluida(instituicao, !acao, flagAcesso);
		}
	}

	public void enviaremail(String Email, String assunto, String textoEmail) {

		try {

			this.memoriaVirtualEJB.enviarEmail(Email, assunto, textoEmail);

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public ItemAuditoria recuperarItemAuditoria(long chave) throws ModeloException {
		ItemAuditoria itemAuditoria;
		
		itemAuditoria = this.entityManager.find(ItemAuditoria.class, chave);
				
		return itemAuditoria;
	}

	public Instituicao recuperarInstituicaoFalse(String pnome)
			throws ModeloException {
		Instituicao instituicao;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM Instituicao a WHERE a.nome = :nome AND a.validade = FALSE");
		query.setParameter("nome", pnome);
		try {
			instituicao = (Instituicao) query.getResultList().get(0);
		} catch (Exception e) {
			throw new ModeloException(e);
		}
		return instituicao;
	}

	public void marcarInstituicaoExcluida(Instituicao instituicao,
			boolean marca, boolean flagAcesso) throws ModeloException {
		Query query;
		query = this.entityManager
				.createQuery("UPDATE Instituicao a SET a.validade = :validade WHERE  a.id = :id");
		query.setParameter("id", instituicao.getId());
		query.setParameter("validade", marca);
		query.executeUpdate();
		if (flagAcesso) {
			query = this.entityManager
					.createQuery("UPDATE Acesso a SET a.validade = :validade WHERE  a.instituicao = :id");
			query.setParameter("id", instituicao);
			query.setParameter("validade", marca);
			query.executeUpdate();
		}
	}

	public Aprovacao registrarAprovacao(Usuario validador, Instituicao instituicao,
			Date dataValidade) {
		Date data = new Date();
		instituicao = this.entityManager.find(Instituicao.class,
				instituicao.getId());
		Usuario u = entityManager.find(Usuario.class, validador.getId());
		Aprovacao aprovacao = new Aprovacao(data, u, dataValidade,
				instituicao.getNome(), Instituicao.class.getCanonicalName());
		this.entityManager.persist(aprovacao);
		return aprovacao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Instituicao> listarTodasInstituicoes(Grupo grupo,
			Usuario usuario) throws ModeloException {
		List<Instituicao> lista;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a.instituicao FROM Acesso a WHERE  a.instituicao.validade = TRUE AND a.grupo = :grupo AND a.usuario = :usuario");
		query.setParameter("grupo", grupo);
		query.setParameter("usuario", usuario);
		try {
			lista = (List<Instituicao>) query.getResultList();
			return lista;
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Instituicao> listarTodasInstituicoes() throws ModeloException {
		List<Instituicao> lista;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM Instituicao a WHERE  a.validade = TRUE ");

		try {
			lista = (List<Instituicao>) query.getResultList();
			return lista;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}
}