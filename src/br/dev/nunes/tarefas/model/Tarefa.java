package br.dev.nunes.tarefas.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter; 

import br.dev.nunes.tarefas.utils.Utils;

public class Tarefa {

	private String ID;
	private String nome;
	private String descricao;
	private Funcionario responsavel;
	private LocalDate dataInicio;
	private int prazo;
	private LocalDate dataEntrega;
	private Status status;

	public Tarefa(String nome) {
		System.out.println("Criando Tarefa...");
		setID(Utils.gerarUUID8());
		setNome(nome);
		this.status = Status.NAO_INICIADO; 
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Funcionario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Funcionario responsavel) {
		this.responsavel = responsavel;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public int getPrazo() {
		return prazo;
	}

	public void setPrazo(int prazo) {
		this.prazo = prazo;
	}

	public LocalDate getDataPrevistaEntrega() {
		if (dataInicio != null) {
			return dataInicio.plusDays(prazo);
		}
		return null;
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Status getStatus() {
		if (this.status == Status.CONCLUIDO) {
			return Status.CONCLUIDO;
		}

		LocalDate hoje = LocalDate.now();

		if (dataInicio == null) {
			return Status.NAO_INICIADO; //se n ter data de início, não será iniciado
		}

		LocalDate dataPrevistaEntrega = getDataPrevistaEntrega();

		if (dataEntrega != null && !dataEntrega.isAfter(dataPrevistaEntrega)) { //se foi entregue e dentro do prazo original ou antes
		    return Status.CONCLUIDO;
		}
		
		if (hoje.isAfter(dataPrevistaEntrega)) {
			return Status.EM_ATRASO; // Em atraso se a data atual passou do prazo previsto
		} else if (hoje.isAfter(dataInicio) || hoje.isEqual(dataInicio)) {
			return Status.EM_ANDAMENTO; // Em andamento se a data atual está entre o início e o prazo
		} else {
			return Status.NAO_INICIADO; // Não iniciado se a data de início ainda não chegou
		}
	}

	//setter para status, caso seja necessário definir manualmente (ex: CONCLUIDO)
	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		String responsavelMatricula = (responsavel != null) ? responsavel.getMatricula() : "";
		
		String dataInicioStr = (dataInicio != null) ? dataInicio.format(DateTimeFormatter.ISO_LOCAL_DATE) : "";
		String dataEntregaStr = (dataEntrega != null) ? dataEntrega.format(DateTimeFormatter.ISO_LOCAL_DATE) : "";
		
		return ID + "," + 
			   nome + "," + 
			   (descricao != null ? descricao : "") + "," +
			   responsavelMatricula + "," + 
			   dataInicioStr + "," + 
			   prazo + "," + 
			   dataEntregaStr + "," + 
			   status + "\n";
	}
}