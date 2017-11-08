package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.CascadeOnDelete;

import br.usp.memoriavirtual.modelo.entidades.Autoria;
import br.usp.memoriavirtual.modelo.entidades.ContainerMultimidia;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;

@Entity
@SequenceGenerator(name = "BEMPATRIMONIAL_ID", sequenceName = "BEMPATRIMONIAL_SEQ", allocationSize = 1)
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "unicoRegistroSemId", query = "SELECT b FROM BemPatrimonial b WHERE b.numeroRegistro = :numero AND b.id <> :id"),
		@NamedQuery(name = "unicoRegistro", query = "SELECT b FROM BemPatrimonial b WHERE b.numeroRegistro = :numero"),
		@NamedQuery(name = "unicoRegistroComId", query = "SELECT b FROM BemPatrimonial b WHERE b.numeroRegistro = :numero AND b.id = :id") })
public class BemPatrimonial implements Serializable {

	private static final long serialVersionUID = 3790813768470746267L;

	public static enum condicoesTopograficas {
		levementeAcidentado, acidentado, muitoAcidentado, plano
	}

	public static enum Exposicao {
		aberto, alojado, submerso, soterrado, exposto
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BEMPATRIMONIAL_ID")
	private long id;

	@ManyToOne(cascade=CascadeType.MERGE,fetch = FetchType.EAGER)
	private Instituicao instituicao = null;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "BEMPATRIMONIAL_DESCRITOR", joinColumns = @JoinColumn(name = "BEMPATRIMONIAL_ID"), inverseJoinColumns = @JoinColumn(name = "DESCRITOR_ID"))
	private Set<Descritor> descritores = null;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "BEMPATRIMONIAL_ASSUNTO", joinColumns = @JoinColumn(name = "BEMPATRIMONIAL_ID"), inverseJoinColumns = @JoinColumn(name = "ASSUNTO_ID"))
	private Set<Assunto> assuntos = null;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "BEMPATRIMONIAL_FONTESINFORMACAO", joinColumns = @JoinColumn(name = "BEMPATRIMONIAL_ID"))
	@Lob
	private List<String> fontesInformacao = null;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "bemPatrimonial", cascade = CascadeType.ALL)
	private List<Titulo> titulos = null;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "bemPatrimonial", cascade = CascadeType.ALL)
	private List<Autoria> autorias = null;

	@Embedded
	private Producao producao = null;

	@Embedded
	private DisponibilidadeUsoProtecao disponibilidadeUsoProtecao = null;

	@Embedded
	private HistoricoProcedencia historicoProcedencia = null;

	@Embedded
	private Diagnostico diagnostico = null;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "BEMPATRIMONIAL_INTERVENCOES", joinColumns = @JoinColumn(name = "BEMPATRIMONIAL_ID"))
	private List<Intervencao> intervencoes = null;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "BEMPATRIMONIAL_PESQUISADORES", joinColumns = @JoinColumn(name = "BEMPATRIMONIAL_ID"))
	private List<Pesquisador> pesquisadores = null;

	@CascadeOnDelete
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "BEMPATRIMONIAL_BENSRELACIONADOS", inverseJoinColumns = @JoinColumn(name = "BENSRELACIONADOS_ID"), joinColumns = @JoinColumn(name = "BEMPATRIMONIAL_ID"))
	private List<BemPatrimonial> bensRelacionados = null;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ContainerMultimidia containerMultimidia = null;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="bemPatrimonial",cascade = CascadeType.MERGE)
	private List<Revisao> revisoes = null;
	
//	@CascadeOnDelete
//	@OneToMany(fetch = FetchType.EAGER,mappedBy="bemPatrimonial",cascade = CascadeType.PERSIST)
//	//private List<VersaoBemPatrimonial> versoes = null;
	
	private String condicaoTopografica = "";
	private String sitioPaisagem = "";
	private String aguaProxima = "";
	private String vegetacao = "";
	private Exposicao exposicao = null;
	private String usoAtual = "";
	@Lob
	private String outros = "";
	@Lob
	private String notas = "";
	private String areaTotal = "";
	private String comprimento = "";
	private String altura = "";
	private String largura = "";
	private String profundidade = "";
	private String uso = "";
	private Integer numeroPavimentos;
	private Integer numeroAmbientes;
	private boolean alcova;
	private boolean porao;
	private boolean sotao;
	private boolean externo;
	private boolean revisao;
	private String alturaFachadaFrontal = "";
	private String alturaFachadaPosterior = "";
	private String alturaTotal = "";
	private String peDireitoTerreo = "";
	private String peDireitoTipo = "";
	private String alturaCumeeira = "";
	private String dimensoesQuantificacoes = "";
	private String tipoBemPatrimonial = "";
	private String numeroRegistro = "";
	private String colecao = "";
	private String localizacaoFisica = "";
	private String latitude = "";
	private String longitude = "";
	private String tituloPrincipal = "";
	@Lob
	private String caracteristicasFisicasTecnicasExecutivas = "";
	@Lob
	private String conteudo = "";
	@Lob
	private String meioAcesso = "";
	private String idMidia = "";
	@Lob
	private String complemento = "";
	@Lob
	private String meioAntropico = "";
	@Lob
	private String caracteristicasAmbientais = "";
	

	public BemPatrimonial() {
		super();
		this.instituicao = new Instituicao();
		this.descritores = new HashSet<Descritor>();
		this.assuntos = new HashSet<Assunto>();
		this.fontesInformacao = new ArrayList<String>();
		this.titulos = new ArrayList<Titulo>();
		this.autorias = new ArrayList<Autoria>();
		this.producao = new Producao();
		this.disponibilidadeUsoProtecao = new DisponibilidadeUsoProtecao();
		this.historicoProcedencia = new HistoricoProcedencia();
		this.diagnostico = new Diagnostico();
		this.intervencoes = new ArrayList<Intervencao>();
		this.pesquisadores = new ArrayList<Pesquisador>();
		this.bensRelacionados = new ArrayList<BemPatrimonial>();
		this.containerMultimidia = new ContainerMultimidia();
		this.revisoes = new ArrayList<Revisao>();
//		this.versoes = new ArrayList<VersaoBemPatrimonial>();
		this.exposicao = BemPatrimonial.Exposicao.exposto;
		this.numeroPavimentos = 0;
		this.numeroAmbientes = 0;
		this.alcova = false;
		this.porao = false;
		this.sotao = false;
		this.externo = false;
		this.revisao = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Set<Descritor> getDescritores() {
		return descritores;
	}

	public void setDescritores(Set<Descritor> descritores) {
		this.descritores = descritores;
	}

	public Set<Assunto> getAssuntos() {
		return assuntos;
	}

	public void setAssuntos(Set<Assunto> assuntos) {
		this.assuntos = assuntos;
	}

	public List<String> getFontesInformacao() {
		return fontesInformacao;
	}

	public void setFontesInformacao(List<String> fontesInformacao) {
		this.fontesInformacao = fontesInformacao;
	}

	public List<Titulo> getTitulos() {
		return titulos;
	}

	public void setTitulos(List<Titulo> titulos) {
		this.titulos = titulos;
	}

	public List<Autoria> getAutorias() {
		return autorias;
	}

	public void setAutorias(List<Autoria> autorias) {
		this.autorias = autorias;
	}

	public Producao getProducao() {
		return producao;
	}

	public void setProducao(Producao producao) {
		this.producao = producao;
	}

	public DisponibilidadeUsoProtecao getDisponibilidadeUsoProtecao() {
		return disponibilidadeUsoProtecao;
	}

	public void setDisponibilidadeUsoProtecao(
			DisponibilidadeUsoProtecao disponibilidadeUsoProtecao) {
		this.disponibilidadeUsoProtecao = disponibilidadeUsoProtecao;
	}

	public HistoricoProcedencia getHistoricoProcedencia() {
		return historicoProcedencia;
	}

	public void setHistoricoProcedencia(
			HistoricoProcedencia historicoProcedencia) {
		this.historicoProcedencia = historicoProcedencia;
	}

	public Diagnostico getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(Diagnostico diagnostico) {
		this.diagnostico = diagnostico;
	}

	public List<Intervencao> getIntervencoes() {
		return intervencoes;
	}

	public void setIntervencoes(List<Intervencao> intervencoes) {
		this.intervencoes = intervencoes;
	}

	public List<Pesquisador> getPesquisadores() {
		return pesquisadores;
	}

	public void setPesquisadores(List<Pesquisador> pesquisadores) {
		this.pesquisadores = pesquisadores;
	}

	public List<BemPatrimonial> getBensRelacionados() {
		return bensRelacionados;
	}

	public void setBensRelacionados(List<BemPatrimonial> bensRelacionados) {
		this.bensRelacionados = bensRelacionados;
	}
	
//	public List<VersaoBemPatrimonial> getVersoes() {
//		return versoes;
//	}

//	public void setVersoes(List<VersaoBemPatrimonial> versoes) {
//		this.versoes = versoes;
//	}

	public ContainerMultimidia getContainerMultimidia() {
		return containerMultimidia;
	}

	public void setContainerMultimidia(ContainerMultimidia containerMultimidia) {
		this.containerMultimidia = containerMultimidia;
	}

	public String getCondicaoTopografica() {
		return condicaoTopografica;
	}

	public void setCondicaoTopografica(String condicaoTopografica) {
		this.condicaoTopografica = condicaoTopografica;
	}

	public String getSitioPaisagem() {
		return sitioPaisagem;
	}

	public void setSitioPaisagem(String sitioPaisagem) {
		this.sitioPaisagem = sitioPaisagem;
	}

	public String getAguaProxima() {
		return aguaProxima;
	}

	public void setAguaProxima(String aguaProxima) {
		this.aguaProxima = aguaProxima;
	}

	public String getVegetacao() {
		return vegetacao;
	}

	public void setVegetacao(String vegetacao) {
		this.vegetacao = vegetacao;
	}

	public Exposicao getExposicao() {
		return exposicao;
	}

	public void setExposicao(Exposicao exposicao) {
		this.exposicao = exposicao;
	}

	public String getUsoAtual() {
		return usoAtual;
	}

	public void setUsoAtual(String usoAtual) {
		this.usoAtual = usoAtual;
	}

	public String getOutros() {
		return outros;
	}

	public void setOutros(String outros) {
		this.outros = outros;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getAreaTotal() {
		return areaTotal;
	}

	public void setAreaTotal(String areaTotal) {
		this.areaTotal = areaTotal;
	}

	public String getComprimento() {
		return comprimento;
	}

	public void setComprimento(String comprimento) {
		this.comprimento = comprimento;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public String getLargura() {
		return largura;
	}

	public void setLargura(String largura) {
		this.largura = largura;
	}

	public String getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(String profundidade) {
		this.profundidade = profundidade;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public Integer getNumeroPavimentos() {
		return numeroPavimentos;
	}

	public void setNumeroPavimentos(Integer numeroPavimentos) {
		this.numeroPavimentos = numeroPavimentos;
	}

	public Integer getNumeroAmbientes() {
		return numeroAmbientes;
	}

	public void setNumeroAmbientes(Integer numeroAmbientes) {
		this.numeroAmbientes = numeroAmbientes;
	}

	public boolean isAlcova() {
		return alcova;
	}

	public void setAlcova(boolean alcova) {
		this.alcova = alcova;
	}

	public boolean isPorao() {
		return porao;
	}

	public void setPorao(boolean porao) {
		this.porao = porao;
	}

	public boolean isSotao() {
		return sotao;
	}

	public void setSotao(boolean sotao) {
		this.sotao = sotao;
	}

	public boolean isExterno() {
		return externo;
	}

	public void setExterno(boolean externo) {
		this.externo = externo;
	}

	public String getAlturaFachadaFrontal() {
		return alturaFachadaFrontal;
	}

	public void setAlturaFachadaFrontal(String alturaFachadaFrontal) {
		this.alturaFachadaFrontal = alturaFachadaFrontal;
	}

	public String getAlturaFachadaPosterior() {
		return alturaFachadaPosterior;
	}

	public void setAlturaFachadaPosterior(String alturaFachadaPosterior) {
		this.alturaFachadaPosterior = alturaFachadaPosterior;
	}

	public String getAlturaTotal() {
		return alturaTotal;
	}

	public void setAlturaTotal(String alturaTotal) {
		this.alturaTotal = alturaTotal;
	}

	public String getPeDireitoTerreo() {
		return peDireitoTerreo;
	}

	public void setPeDireitoTerreo(String peDireitoTerreo) {
		this.peDireitoTerreo = peDireitoTerreo;
	}

	public String getPeDireitoTipo() {
		return peDireitoTipo;
	}

	public void setPeDireitoTipo(String peDireitoTipo) {
		this.peDireitoTipo = peDireitoTipo;
	}

	public String getAlturaCumeeira() {
		return alturaCumeeira;
	}

	public void setAlturaCumeeira(String alturaCumeeira) {
		this.alturaCumeeira = alturaCumeeira;
	}

	public String getDimensoesQuantificacoes() {
		return dimensoesQuantificacoes;
	}

	public void setDimensoesQuantificacoes(String dimensoesQuantificacoes) {
		this.dimensoesQuantificacoes = dimensoesQuantificacoes;
	}

	public String getTipoBemPatrimonial() {
		return tipoBemPatrimonial;
	}

	public void setTipoBemPatrimonial(String tipoBemPatrimonial) {
		this.tipoBemPatrimonial = tipoBemPatrimonial;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getColecao() {
		return colecao;
	}

	public void setColecao(String colecao) {
		this.colecao = colecao;
	}

	public String getLocalizacaoFisica() {
		return localizacaoFisica;
	}

	public void setLocalizacaoFisica(String localizacaoFisica) {
		this.localizacaoFisica = localizacaoFisica;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTituloPrincipal() {
		return tituloPrincipal;
	}

	public void setTituloPrincipal(String tituloPrincipal) {
		this.tituloPrincipal = tituloPrincipal;
	}

	public String getCaracteristicasFisicasTecnicasExecutivas() {
		return caracteristicasFisicasTecnicasExecutivas;
	}

	public void setCaracteristicasFisicasTecnicasExecutivas(
			String caracteristicasFisicasTecnicasExecutivas) {
		this.caracteristicasFisicasTecnicasExecutivas = caracteristicasFisicasTecnicasExecutivas;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getMeioAcesso() {
		return meioAcesso;
	}

	public void setMeioAcesso(String meioAcesso) {
		this.meioAcesso = meioAcesso;
	}

	public String getIdMidia() {
		return idMidia;
	}

	public void setIdMidia(String idMidia) {
		this.idMidia = idMidia;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getMeioAntropico() {
		return meioAntropico;
	}

	public void setMeioAntropico(String meioAntropico) {
		this.meioAntropico = meioAntropico;
	}

	public String getCaracteristicasAmbientais() {
		return caracteristicasAmbientais;
	}

	public void setCaracteristicasAmbientais(String caracteristicasAmbientais) {
		this.caracteristicasAmbientais = caracteristicasAmbientais;
	}

	public boolean isRevisao() {
		return revisao;
	}

	public void setRevisao(boolean revisao) {
		this.revisao = revisao;
	}

	public List<Revisao> getRevisoes() {
		return revisoes;
	}

	public void setRevisoes(List<Revisao> revisoes) {
		this.revisoes = revisoes;
	}
}
