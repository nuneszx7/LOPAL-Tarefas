package br.dev.nunes.tarefas.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog; // Importar JDialog, pois FuncionarioFrame usa um JDialog com a JFrame como pai
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.dev.nunes.tarefas.dao.FuncionarioDAO;
import br.dev.nunes.tarefas.model.Funcionario;

public class FuncionarioListaFrame {

    private JLabel labelTitulo;
    private JButton btnNovoFuncionario;

    private DefaultTableModel model;
    private JTable tabelaFuncionarios;
    private JScrollPane scrollFuncionarios;

    String[] colunas = { "MATRÍCULA", "NOME DO FUNCIONÁRIO", "CARGO", "SETOR", "SALÁRIO" };

    public FuncionarioListaFrame() {
        // Inicializa o model AQUI, antes de criar a tela e carregar os dados
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        criarTela();
        carregarDadosTabela();
    }

    private void criarTela() {
        // CORREÇÃO: Deve ser JFrame, não JDialog
        JFrame telaFuncionarioLista = new JFrame("Lista de Funcionários"); // Mantenha como JFrame
        telaFuncionarioLista.setSize(700, 500);
        telaFuncionarioLista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        telaFuncionarioLista.setLayout(null);
        telaFuncionarioLista.setLocationRelativeTo(null);
        telaFuncionarioLista.setResizable(false);

        Container painel = telaFuncionarioLista.getContentPane();

        labelTitulo = new JLabel("Lista de Funcionários");
        labelTitulo.setBounds(10, 10, 500, 40);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        labelTitulo.setForeground(Color.RED);

        tabelaFuncionarios = new JTable(model); // Usa o model já inicializado
        scrollFuncionarios = new JScrollPane(tabelaFuncionarios);
        scrollFuncionarios.setBounds(10, 70, 660, 300);

        btnNovoFuncionario = new JButton("Cadastrar novo funcionário");
        btnNovoFuncionario.setBounds(420, 380, 250, 50);

        painel.add(scrollFuncionarios);
        painel.add(labelTitulo);
        painel.add(btnNovoFuncionario);

        telaFuncionarioLista.setVisible(true);

        btnNovoFuncionario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Passa a própria telaFuncionarioLista (que agora é um JFrame) como pai
                new FuncionarioFrame(telaFuncionarioLista);
                carregarDadosTabela(); // Recarrega os dados após o cadastro de um novo funcionário
            }
        });
    }

    private void carregarDadosTabela() {
        model.setRowCount(0); // Limpa a tabela antes de recarregar

        FuncionarioDAO dao = new FuncionarioDAO(null);
        List<Funcionario> funcionarios = dao.getFuncionarios();

        if (funcionarios != null) {
            for (Funcionario f : funcionarios) {
                Object[] rowData = {
                    f.getMatricula(),
                    f.getNome(),
                    f.getCargo(),
                    f.getSetor(),
                    String.format("%.2f", f.getSalario())
                };
                model.addRow(rowData);
            }
        }
    }
}