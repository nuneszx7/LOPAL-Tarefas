package br.dev.nunes.tarefas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.dev.nunes.tarefas.dao.FuncionarioDAO;
import br.dev.nunes.tarefas.model.Funcionario;
import br.dev.nunes.tarefas.model.Tarefa;
import br.dev.nunes.tarefas.ui.FuncionarioListaFrame;
import br.dev.nunes.tarefas.utils.Utils;
import br.dev.nunes.tarefas.ui.FuncionarioFrame;

public class Main {
	public static void main(String[] args) {


		FuncionarioDAO dao = new FuncionarioDAO(null);
		dao.getFuncionarios();
		
		
		new FuncionarioListaFrame();
//		new FuncionarioFrame(null);
		
//		testarLeituraEscritaArquivo();
		
//		Funcionario funcionario = new Funcionario("Cristiano Ronaldo");
//		funcionario.setSetor("Goat");
//		funcionario.setSalario(999.999.999);
//		
//		FuncionarioDAO dao = new FuncionarioDAO(funcionario);
//		dao.gravar();
		
//		Tarefa tarefa = new Tarefa(funcionario);
//		tarefa.setNome("Lavar a louça");
//		tarefa.setDescricao("Lavar a louça antes de eu chegar");
//		tarefa.setDataInicio(LocalDate.of(2025, 5, 21));
//		tarefa.setPrazo(1);
//		tarefa.setStatus(Status.EM_ANDAMENTO);
//
//
//		System.out.println(Utils.gerarUUID8());
//		System.out.println(funcionario);

	}
	
//	private static void testarLeituraEscritaArquivo() {
//		String so = System.getProperty("os.name");
//		System.out.println("o seu sistema operacional é o " + so); 
//
//		String path = "/Users/25132698/projetoTarefas/tarefas";
//		FileReader fileReader = null;
//		BufferedReader bufferReader = null;
//
//		FileWriter fileWriter = null;
//		BufferedWriter bufferWriter = null;
//
//		try {
//			fileReader = new FileReader(path);
//			bufferReader = new BufferedReader(fileReader);
//
//			fileWriter = new FileWriter(path, true);
//			bufferWriter = new BufferedWriter(fileWriter);
//
//			bufferWriter.append("FlushedEmoji\n");
//			bufferWriter.flush();
//
//			String linha = bufferReader.readLine();
//			while (linha != null) {
//				System.out.println(linha);
//				linha = bufferReader.readLine();
//			}
//
//		} catch (FileNotFoundException exception) {
//			System.out.println("Error 404 - File not found");
//		} catch (IOException exception) {
//			System.out.println("Erro - Falha de leitura");
//		} catch (Exception exception) {
//			System.out.println(exception.getMessage());
//			// TODO: handle exception
//		}
//	}

}