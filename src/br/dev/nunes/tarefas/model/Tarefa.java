package br.dev.nunes.tarefas.model;

import java.time.LocalDate;

public class Tarefa {
	
	
	private String nome;
	private String descricao;
	private Funcionario responsavel;
	private LocalDate dataInicio;
	private int prazo;
	private LocalDate dataEntrega;
	private Status status;
	public String getNome() {
		return nome;
	}
	
	public Tarefa(Funcionario responsavel) {
		this.responsavel = responsavel;
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
		return dataInicio.plusDays(prazo);
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Status getStatus() {
		LocalDate hoje = LocalDate.now();
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	

}
