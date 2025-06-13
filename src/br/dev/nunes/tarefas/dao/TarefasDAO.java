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
import br.dev.nunes.tarefas.model.Tarefa;

public class TarefasDAO {
	private Tarefa tarefa;
	ArquivoTarefaFactory atf = new ArquivoTarefaFactory();

	public TarefasDAO(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public boolean gravar() {
		try {
			BufferedWriter bw = atf.getBw();
			bw.write(tarefa.toString());
			bw.flush();

			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public List<Tarefa> getTarefas() {
		List<Tarefa> tarefas = new ArrayList<Tarefa>();

		try {
			BufferedReader br = atf.getBr();
			String linha = "";

			while (linha != null) {
				linha = br.readLine();
				if (linha != null) {
					String[] tarefaVetor = linha.split(",");
					Tarefa tarefa = new Tarefa(null);

					tarefa.setID(tarefaVetor[0]);
					tarefa.setNome(tarefaVetor[1]);
//					tarefa.setResponsavel(tarefaVetor[2]); //TODO: encontrar o funcionario pela "matricula/id"
//					tarefa.setDataInicio(null); //TODO: inserir data de inicio da tarefa
					tarefa.setPrazo(0);
				}
			}

			return tarefas;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
}