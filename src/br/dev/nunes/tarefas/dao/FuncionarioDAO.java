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
        BufferedWriter bw = null;
        try {
            bw = aff.getBw();
            bw.write(funcionario.toCsvString());
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

    public List<Funcionario> getFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        BufferedReader br = null;

        try {
            br = aff.getBr();
            String linha = "";

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }
                String[] funcionarioVetor = linha.split(",");
                if (funcionarioVetor.length >= 5) {
                    Funcionario funcionario = new Funcionario(funcionarioVetor[1]);

                    funcionario.setMatricula(funcionarioVetor[0]);
                    funcionario.setCargo(funcionarioVetor[2]);
                    funcionario.setSetor(funcionarioVetor[3]);
                    try {
                        funcionario.setSalario(Double.parseDouble(funcionarioVetor[4]));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        funcionario.setSalario(0.0);
                    }
                    funcionarios.add(funcionario);
                }
            }
            return funcionarios;

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

    public Funcionario getFuncionarioByMatricula(String matricula) {
        List<Funcionario> funcionarios = getFuncionarios();
        if (funcionarios != null) {
            for (Funcionario f : funcionarios) {
                if (f.getMatricula().equals(matricula)) {
                    return f;
                }
            }
        }
        return null;
    }
}