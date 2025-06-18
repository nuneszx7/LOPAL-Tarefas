package br.dev.nunes.tarefas.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import br.dev.nunes.tarefas.factory.ArquivoTarefaFactory;
import br.dev.nunes.tarefas.model.Funcionario;
import br.dev.nunes.tarefas.model.Status;
import br.dev.nunes.tarefas.model.Tarefa;

public class TarefaDAO {
	private Tarefa tarefa;
	ArquivoTarefaFactory atf = new ArquivoTarefaFactory();
	private FuncionarioDAO funcionarioDAO = new FuncionarioDAO(null); 

	public TarefaDAO(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public boolean gravar() {
		try {
			BufferedWriter bw = atf.getBw();
			bw.write(tarefa.toString());
			bw.flush(); 
			bw.close(); //fechar o buffered writer
			return true;
		} catch (Exception e) {
			System.err.println("Erro ao gravar tarefa: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public List<Tarefa> getTarefas() {
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		BufferedReader br = null; 

		try {
			br = atf.getBr();
			String linha = "";

			while ((linha = br.readLine()) != null) {
				if (linha.trim().isEmpty()) {
					continue;
				}
				String[] tarefaVetor = linha.split(",");
				
				if (tarefaVetor.length >= 8) { 
					Tarefa tarefa = new Tarefa(tarefaVetor[1]); 

					tarefa.setID(tarefaVetor[0]);
					// tarefa.setNome(tarefaVetor[1]); //setado pelo construtor
					tarefa.setDescricao(tarefaVetor[2]); 

					String matriculaResponsavel = tarefaVetor[3];
					Funcionario responsavel = funcionarioDAO.getFuncionarioByMatricula(matriculaResponsavel);
					tarefa.setResponsavel(responsavel);

					try {
						if (!tarefaVetor[4].isEmpty()) {
							tarefa.setDataInicio(LocalDate.parse(tarefaVetor[4], DateTimeFormatter.ISO_LOCAL_DATE));
						}
					} catch (DateTimeParseException e) {
						System.err.println("Erro ao parsear data de início para a tarefa " + tarefa.getNome() + ": " + tarefaVetor[4]);
					}
					
					try {
						tarefa.setPrazo(Integer.parseInt(tarefaVetor[5]));
					} catch (NumberFormatException e) {
						System.err.println("Erro ao parsear prazo para a tarefa " + tarefa.getNome() + ": " + tarefaVetor[5]);
						tarefa.setPrazo(0); 
					}

					try {
						if (!tarefaVetor[6].isEmpty()) {
							tarefa.setDataEntrega(LocalDate.parse(tarefaVetor[6], DateTimeFormatter.ISO_LOCAL_DATE));
						}
					} catch (DateTimeParseException e) {
						System.err.println("Erro ao parsear data de entrega para a tarefa " + tarefa.getNome() + ": " + tarefaVetor[6]);
					}

					try {
						tarefa.setStatus(Status.valueOf(tarefaVetor[7]));
					} catch (IllegalArgumentException e) {
						System.err.println("Erro ao parsear status para a tarefa " + tarefa.getNome() + ": " + tarefaVetor[7]);
						tarefa.setStatus(Status.NAO_INICIADO); 
					}
					
					tarefas.add(tarefa);
				} else {
					System.err.println("Linha do CSV com formato inválido para Tarefa: " + linha);
				}
			}
			return tarefas;

		} catch (IOException e) {
			System.err.println("Erro ao ler tarefas do arquivo: " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
            if (br != null) {
                try {
                    br.close(); //fechar o BufferedReader
                } catch (IOException e) {
                    System.err.println("Erro ao fechar BufferedReader: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
	}
}