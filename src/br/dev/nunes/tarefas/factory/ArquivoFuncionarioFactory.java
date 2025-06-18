package br.dev.nunes.tarefas.factory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ArquivoFuncionarioFactory {

    private String diretorioDados = System.getProperty("user.home") + File.separator + "tarefas_data";
    private String caminho = diretorioDados + File.separator + "funcionarios.csv";

    private FileWriter fw;
    private BufferedWriter bw;
    private FileReader fr;
    private BufferedReader br;

    public ArquivoFuncionarioFactory() {
        File dir = new File(diretorioDados);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public BufferedWriter getBw() throws IOException {
        fw = new FileWriter(caminho, true);
        bw = new BufferedWriter(fw);
        return bw;
    }

    public BufferedReader getBr() throws IOException {
        File file = new File(caminho);
        if (!file.exists()) {
            file.createNewFile();
        }
        fr = new FileReader(caminho);
        br = new BufferedReader(fr);
        return br;
    }
}