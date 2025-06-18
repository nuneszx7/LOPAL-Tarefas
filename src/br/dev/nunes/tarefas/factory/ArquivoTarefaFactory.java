package br.dev.nunes.tarefas.factory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ArquivoTarefaFactory {

    private String diretorioDados = System.getProperty("user.home") + File.separator + "tarefas_data";
    private String caminho = diretorioDados + File.separator + "tarefas.csv"; // arquivo específico para tarefas

    private FileWriter fw;
    private BufferedWriter bw;
    private FileReader fr;
    private BufferedReader br;

    public ArquivoTarefaFactory() {
        File dir = new File(diretorioDados);
        if (!dir.exists()) {
            dir.mkdirs(); // cria o diretório se não existir
        }
    }

    public BufferedWriter getBw() throws IOException {
        fw = new FileWriter(caminho, true); // true para adicionar ao final do arquivo
        bw = new BufferedWriter(fw);
        return bw;
    }

    public BufferedReader getBr() throws IOException {
        File file = new File(caminho);
        if (!file.exists()) {
            file.createNewFile(); // cria o arquivo se não existir praticamente a mesma coisa da linha 23
        }
        fr = new FileReader(caminho);
        br = new BufferedReader(fr);
        return br;
    }
}