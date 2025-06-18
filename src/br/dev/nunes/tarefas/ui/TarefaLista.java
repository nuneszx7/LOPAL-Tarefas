package br.dev.nunes.tarefas.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.dev.nunes.tarefas.dao.TarefaDAO;
import br.dev.nunes.tarefas.model.Tarefa;

public class TarefaLista {

    private JLabel labelTitulo;
    private JButton btnNovaTarefa;

    private DefaultTableModel model;
    private JTable tabelaTarefas;
    private JScrollPane scrollTarefas;

    String[] colunas = { "ID", "NOME", "DESCRIÇÃO", "RESPONSÁVEL", "INÍCIO", "PRAZO", "PREVISTO", "ENTREGA", "STATUS" };

    public TarefaLista(JFrame parentFrame) { 
    	
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna as células não editáveis
            }
        };
        criarTela(parentFrame); // Passa o parentFrame para criarTela
        carregarDadosTabela();
    }

    private void criarTela(JFrame parentFrame) {
        
        JFrame telaTarefaLista = new JFrame("Lista de Tarefas");
        telaTarefaLista.setSize(900, 500); // largura maior pra suportar mais colunas
        telaTarefaLista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        telaTarefaLista.setLayout(null);
        telaTarefaLista.setLocationRelativeTo(null);
        telaTarefaLista.setResizable(false);

        Container painel = telaTarefaLista.getContentPane();

        labelTitulo = new JLabel("Lista de Tarefas");
        labelTitulo.setBounds(10, 10, 500, 40);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        labelTitulo.setForeground(Color.BLUE);

        tabelaTarefas = new JTable(model);
        scrollTarefas = new JScrollPane(tabelaTarefas);
        scrollTarefas.setBounds(10, 70, 860, 300); // largura de tela ajustada

        btnNovaTarefa = new JButton("Registrar nova tarefa");
        btnNovaTarefa.setBounds(620, 380, 250, 50); //largura de tela ajustada2

        painel.add(scrollTarefas);
        painel.add(labelTitulo);
        painel.add(btnNovaTarefa);

        telaTarefaLista.setVisible(true);

        btnNovaTarefa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TarefaFrame(telaTarefaLista); 
                carregarDadosTabela(); 
            }
        });
    }

    private void carregarDadosTabela() {
        model.setRowCount(0); // Limpa a tabela antes de recarregar

        TarefaDAO dao = new TarefaDAO(); // Instancia sem tarefa específica para buscar todas
        List<Tarefa> tarefas = dao.getTarefas();

        if (tarefas != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (Tarefa t : tarefas) {
                String dataInicioStr = (t.getDataInicio() != null) ? t.getDataInicio().format(formatter) : "";
                String dataPrevistaStr = (t.getDataPrevistaEntrega() != null) ? t.getDataPrevistaEntrega().format(formatter) : "";
                String dataEntregaStr = (t.getDataEntrega() != null) ? t.getDataEntrega().format(formatter) : "";
                String responsavelNome = (t.getResponsavel() != null) ? t.getResponsavel().getNome() : "N/A";

                Object[] rowData = {
                    t.getID(),
                    t.getNome(),
                    t.getDescricao(),
                    responsavelNome,
                    dataInicioStr,
                    t.getPrazo(),
                    dataPrevistaStr,
                    dataEntregaStr,
                    t.getStatus().name()
                };
                model.addRow(rowData);
            }
        }
    }
}