package br.dev.nunes.tarefas.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import br.dev.nunes.tarefas.factory.ArquivoFuncionarioFactory;
import br.dev.nunes.tarefas.model.Tarefa;

public class TarefasDAO {

	private Tarefa tarefa;
	private ArquivoFuncionarioFactory ff = new ArquivoFuncionarioFactory();

	// Método construtor
	public TarefasDAO(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public void gravar() {

		try {

			//MEU PC: "C:\\Users\\pedro\\Desktop\\tarefas.csv"
			//SENAI: "C:\\Users\\25132416\\tarefa\\tarefas.csv"
			BufferedWriter bw = ff.getBufferedWriter("/Users/25132914/eclipse-workspace/tarefas/funcionarios.csv", true);

			bw.write(tarefa.toString());
			bw.flush();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	public List<Tarefa> showTasks() {

		List<Tarefa> tarefas = new ArrayList<>();

		try {

			//MEU PC: "C:\\Users\\pedro\\Desktop\\tarefas.csv"
			//SENAI: "C:\\Users\\25132416\\tarefa\\tarefas.csv"
			BufferedReader br = ff.getBufferedReader("/Users/25132914/eclipse-workspace/tarefas/funcionarios.csv");

			String linha = br.readLine();

			do {
				linha = br.readLine();

				Tarefa t = new Tarefa(null);
				String[] tarefa = linha != null ? linha.split(",") : null;

				if (tarefa != null) {
					t.setResponsavel(null);
					t.setNome(tarefa[1]);
					t.setDescricao(tarefa[2]);
//					t.setDataInicial(tarefa[3]);
//					t.setPrazo(tarefa[4]);
//					t.setDataConclusao(tarefa[5]);
//					t.setStatus(tarefa[6]);
//					t.setResponsavel(tarefa[7]);				

					tarefas.add(t);
				}

			} while (linha != null);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return tarefas;
	}
	
}