package br.dev.nunes.tarefas.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.dev.nunes.tarefas.dao.FuncionarioDAO;
import br.dev.nunes.tarefas.dao.TarefaDAO;
import br.dev.nunes.tarefas.model.Funcionario;
import br.dev.nunes.tarefas.model.Status;
import br.dev.nunes.tarefas.model.Tarefa;
import br.dev.nunes.tarefas.utils.Utils; // Certifique-se de que Utils.java está na pasta correta

public class TarefaFrame {

	private JLabel labelNome;
	private JLabel labelDescricao;
	private JLabel labelDataInicial;
	private JLabel labelPrazo;
	private JLabel labelDataConclusao; // Renomeado para Data de Entrega
	private JLabel labelStatus;
	private JLabel labelResponsavel;

	private JTextField txtNome;
	private JTextField txtDescricao;
	private JTextField txtDataInicial;
	private JTextField txtPrazo;
	private JTextField txtDataConclusao; // Para Data de Entrega
	private JComboBox<Status> boxStatus;
	private JComboBox<Funcionario> boxResponsavel; // Mudar para JComboBox<Funcionario>

	private JButton btnSalvar;
	private JButton btnSair;

	private JDialog parentFrame; // Referência ao frame pai

	public TarefaFrame(JDialog telaTarefaLista) {
		this.parentFrame = telaTarefaLista; // Armazena a referência
		criarTela(telaTarefaLista);
	}

	private void criarTela(JDialog telaTarefaLista) {
		JDialog tela = new JDialog(telaTarefaLista, "Cadastrar nova tarefa", true);
		tela.setSize(500, 600); // Ajustado para mais espaço
		tela.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		tela.setLayout(null);
		tela.setLocationRelativeTo(null);
		tela.setResizable(false);

		Container painel = tela.getContentPane();

		// Labels e TextFields
		labelNome = new JLabel("Nome da Tarefa:");
		labelNome.setBounds(20, 20, 150, 25);
		txtNome = new JTextField();
		txtNome.setBounds(180, 20, 280, 25);

		labelDescricao = new JLabel("Descrição:");
		labelDescricao.setBounds(20, 60, 150, 25);
		txtDescricao = new JTextField();
		txtDescricao.setBounds(180, 60, 280, 25);

		labelDataInicial = new JLabel("Data de Início (AAAA-MM-DD):");
		labelDataInicial.setBounds(20, 100, 180, 25);
		txtDataInicial = new JTextField();
		txtDataInicial.setBounds(200, 100, 260, 25);

		labelPrazo = new JLabel("Prazo (dias):");
		labelPrazo.setBounds(20, 140, 150, 25);
		txtPrazo = new JTextField();
		txtPrazo.setBounds(180, 140, 280, 25);

		labelDataConclusao = new JLabel("Data de Entrega (AAAA-MM-DD):"); // Pode ser a data real de conclusão
		labelDataConclusao.setBounds(20, 180, 200, 25);
		txtDataConclusao = new JTextField();
		txtDataConclusao.setBounds(220, 180, 240, 25);

		labelStatus = new JLabel("Status:");
		labelStatus.setBounds(20, 220, 150, 25);
		boxStatus = new JComboBox<>(Status.values()); // Popula com os valores do enum Status
		boxStatus.setBounds(180, 220, 280, 25);

		labelResponsavel = new JLabel("Responsável:");
		labelResponsavel.setBounds(20, 260, 150, 25);
		boxResponsavel = new JComboBox<>(); // Inicializa vazio, será populado em seguida
		boxResponsavel.setBounds(180, 260, 280, 25);
		popularComboBoxResponsaveis(); // Chama método para popular o ComboBox

		// Botões
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(100, 320, 120, 40);

		btnSair = new JButton("Sair");
		btnSair.setBounds(280, 320, 120, 40);

		// Adicionando componentes ao painel
		painel.add(labelNome);
		painel.add(txtNome);
		painel.add(labelDescricao);
		painel.add(txtDescricao);
		painel.add(labelDataInicial);
		painel.add(txtDataInicial);
		painel.add(labelPrazo);
		painel.add(txtPrazo);
		painel.add(labelDataConclusao);
		painel.add(txtDataConclusao);
		painel.add(labelStatus);
		painel.add(boxStatus);
		painel.add(labelResponsavel);
		painel.add(boxResponsavel);

		painel.add(btnSalvar);
		painel.add(btnSair);

		// Ações dos botões
		btnSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvarTarefa();
			}
		});

		btnSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int resposta = JOptionPane.showConfirmDialog(tela, "Sair da tela de cadastro?", "Sair",
						JOptionPane.YES_NO_OPTION);
				if (resposta == JOptionPane.YES_OPTION) {
					tela.dispose();
				}
			}
		});

		tela.setVisible(true);
	}

	private void popularComboBoxResponsaveis() {
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO(null); // Não precisa de Funcionario para instanciar o DAO aqui
		List<Funcionario> funcionarios = funcionarioDAO.getFuncionarios();
		if (funcionarios != null) {
			for (Funcionario f : funcionarios) {
				boxResponsavel.addItem(f); // Adiciona o objeto Funcionario
			}
		}
	}

	private void salvarTarefa() {
		String nome = txtNome.getText().trim();
		String descricao = txtDescricao.getText().trim();
		String dataInicialStr = txtDataInicial.getText().trim();
		String prazoStr = txtPrazo.getText().trim();
		String dataConclusaoStr = txtDataConclusao.getText().trim();
		Status status = (Status) boxStatus.getSelectedItem();
		Funcionario responsavel = (Funcionario) boxResponsavel.getSelectedItem();

		if (nome.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O nome da tarefa é obrigatório!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Tarefa tarefa = new Tarefa(nome); // Chama o construtor com o nome
		tarefa.setDescricao(descricao);

		// Parsing da data de início
		if (!dataInicialStr.isEmpty()) {
			try {
				tarefa.setDataInicio(LocalDate.parse(dataInicialStr, DateTimeFormatter.ISO_LOCAL_DATE));
			} catch (DateTimeParseException ex) {
				JOptionPane.showMessageDialog(null, "Formato de Data de Início inválido. Use AAAA-MM-DD.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else {
            // Se a data de início é obrigatória, descomente a linha abaixo
            // JOptionPane.showMessageDialog(null, "A Data de Início é obrigatória!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            // return;
        }

		// Parsing do prazo
		if (!prazoStr.isEmpty()) {
			try {
				tarefa.setPrazo(Integer.parseInt(prazoStr));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Prazo inválido. Digite um número inteiro.", "Erro de Prazo", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else {
            // Se o prazo é obrigatório, descomente a linha abaixo
            // JOptionPane.showMessageDialog(null, "O Prazo é obrigatório!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            // return;
        }

		// Parsing da data de entrega (conclusão)
		if (!dataConclusaoStr.isEmpty()) {
			try {
				tarefa.setDataEntrega(LocalDate.parse(dataConclusaoStr, DateTimeFormatter.ISO_LOCAL_DATE));
			} catch (DateTimeParseException ex) {
				JOptionPane.showMessageDialog(null, "Formato de Data de Entrega inválido. Use AAAA-MM-DD.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		tarefa.setStatus(status);
		tarefa.setResponsavel(responsavel);

		TarefaDAO dao = new TarefaDAO(tarefa);
		boolean success = dao.gravar();

		if (success) {
			JOptionPane.showMessageDialog(null, nome + " gravado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
			limparFormulario();
		} else {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro na gravação.\nTente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limparFormulario() {
		txtNome.setText("");
		txtDescricao.setText("");
		txtDataInicial.setText("");
		txtPrazo.setText("");
		txtDataConclusao.setText("");
		boxStatus.setSelectedIndex(0); // Seleciona o primeiro item (NAO_INICIADO)
		if (boxResponsavel.getItemCount() > 0) {
			boxResponsavel.setSelectedIndex(0); // Seleciona o primeiro responsável
		}
	}
}