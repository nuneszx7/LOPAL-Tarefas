package br.dev.nunes.tarefas.factory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ArquivoFuncionarioFactory {
	
	private String caminho = "/Users/25132914/eclipse-workspace/tarefas/funcionarios.csv";
	private FileWriter fw;
	private BufferedWriter bw;
	
	
	public BufferedWriter getBw() throws IOException {
		
		fw = new FileWriter(caminho, true);
		bw = new BufferedWriter(fw);
		
		return bw;
	}


	public BufferedReader getBufferedReader(String string) {
		// TODO Auto-generated method stub
		return null;
	}


	public BufferedWriter getBufferedWriter(String string, boolean b) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
