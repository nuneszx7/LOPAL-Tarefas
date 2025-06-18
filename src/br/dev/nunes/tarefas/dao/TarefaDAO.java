package br.dev.nunes.tarefas.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.dev.nunes.tarefas.factory.ArquivoTarefaFactory;
import br.dev.nunes.tarefas.model.Funcionario;
import br.dev.nunes.tarefas.model.Status;
import br.dev.nunes.tarefas.model.Tarefa;

public class TarefaDAO {
    private Tarefa tarefa;
    private ArquivoTarefaFactory atf = new ArquivoTarefaFactory();
    private FuncionarioDAO funcionarioDAO; // buscar funcionários por matrícula

    public TarefaDAO() {
        this(null);
    }

    public TarefaDAO(Tarefa tarefa) {
        this.tarefa = tarefa;
        this.funcionarioDAO = new FuncionarioDAO(null); 
    }

    public boolean gravar() {
        BufferedWriter bw = null;
        try {
            bw = atf.getBw();
            bw.write(tarefa.toCsvString());
            bw.newLine();
            bw.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Tarefa> getTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        BufferedReader br = null;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        try {
            br = atf.getBr();
            String linha = "";

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }
                String[] tarefaVetor = linha.split(",");
                
                if (tarefaVetor.length >= 9) {
                    String id = tarefaVetor[0];
                    String nome = tarefaVetor[1];
                    String descricao = tarefaVetor[2];
                    String responsavelMatricula = tarefaVetor[3];
                    // String responsavelNome = tarefaVetor[4]; // aqui n precisa utilizar o nome, só da matrícula para buscar o funcionario

                    LocalDate dataInicio = null;
                    if (!tarefaVetor[5].isEmpty()) {
                        try {
                            dataInicio = LocalDate.parse(tarefaVetor[5], formatter);
                        } catch (Exception e) {
                            System.err.println("Erro ao parsear dataInicio para a tarefa " + id + ": " + tarefaVetor[5]);
                        }
                    }

                    int prazo = 0;
                    try {
                        prazo = Integer.parseInt(tarefaVetor[6]);
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao parsear prazo para a tarefa " + id + ": " + tarefaVetor[6]);
                    }

                    LocalDate dataEntrega = null;
                    if (!tarefaVetor[7].isEmpty()) {
                        try {
                            dataEntrega = LocalDate.parse(tarefaVetor[7], formatter);
                        } catch (Exception e) {
                            System.err.println("Erro ao parsear dataEntrega para a tarefa " + id + ": " + tarefaVetor[7]);
                        }
                    }

                    Status status = Status.NAO_INICIADO;
                    try {
                        status = Status.valueOf(tarefaVetor[8]);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Status inválido para a tarefa " + id + ": " + tarefaVetor[8]);
                    }

                    // buscar o funcionario pelo ID/Matrícula
                    Funcionario responsavel = null;
                    if (!responsavelMatricula.isEmpty()) {
                        responsavel = funcionarioDAO.getFuncionarioByMatricula(responsavelMatricula);
                        //se não encontrou o funcionário, cria um genérico para não quebrar
                        if (responsavel == null) {
                            responsavel = new Funcionario(tarefaVetor[4]); // usa o nome salvo no CSV
                            responsavel.setMatricula(responsavelMatricula);
                        }
                    } else {
                         responsavel = new Funcionario("Não Atribuído");
                         responsavel.setMatricula("");
                    }

                    Tarefa tarefa = new Tarefa(nome);
                    tarefa.setID(id);
                    tarefa.setDescricao(descricao);
                    tarefa.setResponsavel(responsavel);
                    tarefa.setDataInicio(dataInicio);
                    tarefa.setPrazo(prazo);
                    tarefa.setDataEntrega(dataEntrega);
                    tarefa.setStatus(status); // define o status do arquivo

                    tarefas.add(tarefa);
                }
            }
            return tarefas;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Tarefa getTarefaByID(String id) {
        List<Tarefa> tarefas = getTarefas();
        if (tarefas != null) {
            for (Tarefa t : tarefas) {
                if (t.getID().equals(id)) {
                    return t;
                }
            }
        }
        return null;
    }
}