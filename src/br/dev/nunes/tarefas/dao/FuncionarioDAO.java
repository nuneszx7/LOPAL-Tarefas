package br.dev.nunes.tarefas.dao;

import java.io.BufferedWriter;

import br.dev.nunes.tarefas.factory.ArquivoFuncionarioFactory;
import br.dev.nunes.tarefas.model.Funcionario;

public class FuncionarioDAO {
	
	private Funcionario funcionario;
	
	public FuncionarioDAO(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public void gravar() {
		ArquivoFuncionarioFactory aff = new ArquivoFuncionarioFactory();
		try {
			BufferedWriter bw = aff.getBw();
			bw.write(funcionario.toString());
			bw.flush();
			System.out.println(funcionario.getNome() + " gravado com sucesso!");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
	}
	
}
