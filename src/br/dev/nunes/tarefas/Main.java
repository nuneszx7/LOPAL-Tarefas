package br.dev.nunes.tarefas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import br.dev.nunes.tarefas.model.Funcionario;
import br.dev.nunes.tarefas.model.Status;
import br.dev.nunes.tarefas.model.Tarefa;
import br.dev.nunes.tarefas.utils.Utils;

public class Main {

	public static void main(String[] args) {
				
		Funcionario funcionario = new Funcionario("Ronaldo", "Programador");
		System.out.println(funcionario);
		
		Tarefa tarefa = new Tarefa(funcionario);
		tarefa.setNome("Lavar a louça");
		tarefa.setDescricao("Lavar a louça antes de eu chegar.");
		tarefa.setDataInicio(LocalDate.of(2025, 5, 21));
		tarefa.setPrazo(1);
		tarefa.setStatus(Status.EM_ANDAMENTO);
		
		
		System.out.println(Utils.gerarUUID8());
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//testarLeituraEscritaArquivo();
		

	}

	private static void testarLeituraEscritaArquivo() {
		String so = System.getProperty("os.name");
		System.out.println(so);
		
		String caminho = "/Users/25132914/projetoTarefas/tarefas";
		
		FileReader fr = null;
		BufferedReader br = null;
		
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fr = new FileReader(caminho);
			br = new BufferedReader(fr);
			
			fw = new FileWriter(caminho, true);
			bw = new BufferedWriter(fw);
			
			bw.append("Corinthians\n");
			bw.flush();
			
			String linha = br.readLine();
			
			while (linha != null) {
				System.out.println(linha);
				linha = br.readLine();
				
			}
			
		} catch (FileNotFoundException erro) {
			System.out.println("Arquivo não encontrado!");
		} catch (IOException erro) {
			System.out.println("O arquivo está protegido para leitura!");
		} catch (Exception erro) {
			System.out.println(erro.getMessage());
		}
	}

}
