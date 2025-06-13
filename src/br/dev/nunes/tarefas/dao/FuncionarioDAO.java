package br.dev.nunes.tarefas.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.dev.nunes.tarefas.factory.ArquivoFuncionarioFactory;
import br.dev.nunes.tarefas.model.Funcionario;

public class FuncionarioDAO {

	private Funcionario funcionario;
	private ArquivoFuncionarioFactory aff = new ArquivoFuncionarioFactory();

	public FuncionarioDAO(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public boolean gravar() {
		
		try {
			
			BufferedWriter bw = aff.getBw();
			bw.write(funcionario.toString());
			bw.flush();
			System.out.println(funcionario.getNome() + " gravado com sucesso!");
			return true;
		
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			return false;
		
		}
		
	}
	
	public List<Funcionario> getFuncionarios() {
		
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		
		try {
			BufferedReader br = aff.getBr();
			
			String linha = "";
			
			while (linha != null) {
				linha = br.readLine();
				if (linha != null) {
					String[] funcionarioVetor = linha.split(",");
					Funcionario funcionario = new Funcionario(null);
					funcionario.setMatricula(funcionarioVetor[0]);
					funcionario.setNome(funcionarioVetor[1]);
					funcionario.setCargo(funcionarioVetor[2]);
					funcionario.setSetor(funcionarioVetor[3]);
					funcionario.setSalario(Double.parseDouble(funcionarioVetor[4]));
					funcionarios.add(funcionario);
				}

			}
			
			return funcionarios;
			
		} catch (IOException e) {	
			e.printStackTrace();
			return null;
		}
		
	}

}