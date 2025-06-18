package br.dev.nunes.tarefas.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter; // Para formatar datas na tabela
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
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

	String[] colunas = { "ID", "NOME", "DESCRIÇÃO", "RESPONSÁVEL", "INÍCIO", "PRAZO (dias)", "ENTREGA", "STATUS" };

	private JFrame parentFrame;

	public TarefaLista(JFrame parentFrame) {
		this.parentFrame = parentFrame;
		criarTela(parentFrame);
		carregarDadosTabela(); // Carrega os dados na inicialização
	}

	private void criarTela(JFrame parentFrame) {
		JDialog telaTarefaLista = new JDialog(parentFrame, "Lista de tarefas", true); // True para modal
		telaTarefaLista.setSize(900, 500); // Ajustado para mais colunas
		telaTarefaLista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		telaTarefaLista.setLayout(null);
		telaTarefaLista.setLocationRelativeTo(null);
		telaTarefaLista.setResizable(false);

		Container painel = telaTarefaLista.getContentPane();

		labelTitulo = new JLabel("Lista de Tarefas");
		labelTitulo.setBounds(10, 10, 500, 40);
		labelTitulo.setFont(new Font("Arial", Font.BOLD, 32));
		labelTitulo.setForeground(Color.BLUE); // Corrigindo a cor

		// Criando tabela
		model = new DefaultTableModel(colunas, 0) { //o 0 indica que começa com 0 linhas
			@Override
            public boolean isCellEditable(int row, int column) {
                return false; //torna as células não editáveis
            }
		};
		tabelaTarefas = new JTable(model);
		scrollTarefas = new JScrollPane(tabelaTarefas);
		scrollTarefas.setBounds(10, 70, 860, 300); // Ajustado largura para mais colunas

		btnNovaTarefa = new JButton("Registrar nova tarefa");
		btnNovaTarefa.setBounds(650, 380, 220, 50); // Ajustado posição

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
		model.setRowCount(0);

		TarefaDAO dao = new TarefaDAO(null); 
		List<Tarefa> tarefas = dao.getTarefas();

		if (tarefas != null) {
			for (Tarefa tarefa : tarefas) {
				// Formatar as datas para exibição na tabela
				String dataInicioStr = (tarefa.getDataInicio() != null) ? 
				    tarefa.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
				String dataEntregaStr = (tarefa.getDataEntrega() != null) ? 
				    tarefa.getDataEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
				
				String responsavelNome = (tarefa.getResponsavel() != null) ? 
				    tarefa.getResponsavel().getNome() : "N/A"; 
				
				Object[] rowData = {
					tarefa.getID(),
					tarefa.getNome(),
					tarefa.getDescricao(),
					responsavelNome,
					dataInicioStr,
					tarefa.getPrazo(),
					dataEntregaStr,
					tarefa.getStatus()
				};
				model.addRow(rowData);
			}
		}
	}
}