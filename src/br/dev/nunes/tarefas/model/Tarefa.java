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
    private LocalDate dataEntrega; // Data da efetiva entrega
    private Status status;

    public Tarefa(String nome) {
        setID(Utils.gerarUUID8());
        setNome(nome);
        // Inicializações padrão para evitar NullPointerException em toCsvString
        this.descricao = "";
        this.responsavel = new Funcionario("N/A"); // Responsável padrão
        this.dataInicio = LocalDate.now();
        this.prazo = 0;
        this.dataEntrega = LocalDate.now();
        this.status = Status.NAO_INICIADO;
    }

    public Tarefa(String nome, Funcionario responsavel, LocalDate dataInicio, int prazo, Status status) {
        this(nome); // Chama o construtor acima para inicializar ID e nome
        this.responsavel = responsavel;
        this.dataInicio = dataInicio;
        this.prazo = prazo;
        this.status = status;
        // dataEntrega inicializada como dataPrevistaEntrega se não houver data efetiva
        this.dataEntrega = getDataPrevistaEntrega();
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

        if (this.dataEntrega != null && !this.dataEntrega.isEqual(getDataPrevistaEntrega())) {
             if (this.dataEntrega.isAfter(getDataPrevistaEntrega())) {
                return Status.CONCLUIDA_COM_ATRASO;
            } else {
                return Status.CONCLUIDA;
            }
        } else if (dataInicio != null) {
            LocalDate hoje = LocalDate.now();
            LocalDate dataPrevista = getDataPrevistaEntrega();

            if (hoje.isBefore(dataInicio)) {
                return Status.NAO_INICIADO;
            } else if (hoje.isAfter(dataPrevista)) {
                return Status.EM_ATRASO;
            } else {
                return Status.EM_ANDAMENTO;
            }
        }
        return Status.NAO_INICIADO; // padrão ou caso de dados incompletos
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public String toCsvString() {
       
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE; // Ex: 2024-06-18
        String dataInicioStr = (dataInicio != null) ? dataInicio.format(formatter) : "";
        String dataEntregaStr = (dataEntrega != null) ? dataEntrega.format(formatter) : "";
        String responsavelMatricula = (responsavel != null && responsavel.getMatricula() != null) ? responsavel.getMatricula() : "";
        String responsavelNome = (responsavel != null && responsavel.getNome() != null) ? responsavel.getNome() : "";

        return ID + "," +
               nome + "," +
               descricao + "," +
               responsavelMatricula + "," +
               responsavelNome + "," + 
               dataInicioStr + "," +
               prazo + "," +
               dataEntregaStr + "," + 
               status.name();
    }
}