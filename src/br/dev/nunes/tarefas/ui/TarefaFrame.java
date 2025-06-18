package br.dev.nunes.tarefas.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import br.dev.nunes.tarefas.dao.FuncionarioDAO;
import br.dev.nunes.tarefas.dao.TarefaDAO;
import br.dev.nunes.tarefas.model.Funcionario;
import br.dev.nunes.tarefas.model.Status;
import br.dev.nunes.tarefas.model.Tarefa;
import br.dev.nunes.tarefas.utils.Utils;

public class TarefaFrame {

    private JLabel labelID;
    private JLabel labelNome;
    private JLabel labelDescricao;
    private JLabel labelDataInicio;
    private JLabel labelPrazo;
    private JLabel labelDataPrevistaEntrega;
    private JLabel labelDataEntrega;
    private JLabel labelStatus;
    private JLabel labelResponsavel;

    private JTextField txtID;
    private JTextField txtNome;
    private JTextArea txtDescricao;
    private JTextField txtDataInicio; // dd/MM/yyyy
    private JTextField txtPrazo;      // numero de dias
    private JTextField txtDataPrevistaEntrega; // exibição
    private JTextField txtDataEntrega; // data da entrega
    private JComboBox<Status> boxStatus;
    private JComboBox<Funcionario> boxResponsavel; // JComboBox de objetos Funcionario

    private JButton btnSalvar;
    private JButton btnSair;

    private List<Funcionario> listaFuncionarios;

    public TarefaFrame(JFrame pai) {
        carregarFuncionarios();
        criarTela(pai);
    }

    private void carregarFuncionarios() {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO(null);
        listaFuncionarios = funcionarioDAO.getFuncionarios();
        if (listaFuncionarios == null) {
            listaFuncionarios = new java.util.ArrayList<>(); //para a lista n ser null
        }
    }

    private void criarTela(JFrame pai) {
        JDialog tela = new JDialog(pai, "Registrar nova tarefa", true);
        tela.setSize(600, 600);
        tela.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        tela.setLayout(null);
        tela.setLocationRelativeTo(null);
        tela.setResizable(false);

        Container painel = tela.getContentPane();

        // Linha 1: ID da Tarefa
        labelID = new JLabel("ID da Tarefa:");
        labelID.setBounds(20, 20, 150, 25);
        txtID = new JTextField(Utils.gerarUUID8());
        txtID.setBounds(180, 20, 380, 25);
        txtID.setEditable(false);

        // Linha 2: Nome da Tarefa
        labelNome = new JLabel("Nome da Tarefa:");
        labelNome.setBounds(20, 60, 150, 25);
        txtNome = new JTextField();
        txtNome.setBounds(180, 60, 380, 25);

        // Linha 3: Descrição
        labelDescricao = new JLabel("Descrição:");
        labelDescricao.setBounds(20, 100, 150, 25);
        txtDescricao = new JTextArea();
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        scrollDescricao.setBounds(180, 100, 380, 80);

        // Linha 4: Data de Início (formato dd/MM/yyyy)
        labelDataInicio = new JLabel("Data de Início (dd/MM/yyyy):");
        labelDataInicio.setBounds(20, 190, 150, 25);
        txtDataInicio = new JTextField();
        txtDataInicio.setBounds(180, 190, 100, 25);
        txtDataInicio.setText(LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))); // Valor inicial

        // Linha 5: Prazo (em dias)
        labelPrazo = new JLabel("Prazo (dias):");
        labelPrazo.setBounds(300, 190, 80, 25);
        txtPrazo = new JTextField();
        txtPrazo.setBounds(390, 190, 170, 25);
        txtPrazo.setText("0"); // Valor inicial

        // Linha 6: Data Prevista de Entrega (somente leitura)
        labelDataPrevistaEntrega = new JLabel("Previsto para:");
        labelDataPrevistaEntrega.setBounds(20, 230, 150, 25);
        txtDataPrevistaEntrega = new JTextField();
        txtDataPrevistaEntrega.setBounds(180, 230, 380, 25);
        txtDataPrevistaEntrega.setEditable(false);

        // Listener para atualizar Data Prevista de Entrega ao mudar Prazo
        txtPrazo.addActionListener(e -> atualizarDataPrevistaEntrega());
        txtDataInicio.addActionListener(e -> atualizarDataPrevistaEntrega());
        // E também para quando o campo perde o foco
        txtPrazo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                atualizarDataPrevistaEntrega();
            }
        });
        txtDataInicio.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                atualizarDataPrevistaEntrega();
            }
        });


        // Linha 7: Data de Entrega (efetiva)
        labelDataEntrega = new JLabel("Data de Entrega (opcional):");
        labelDataEntrega.setBounds(20, 270, 150, 25);
        txtDataEntrega = new JTextField();
        txtDataEntrega.setBounds(180, 270, 380, 25);


        // Linha 8: Status
        labelStatus = new JLabel("Status:");
        labelStatus.setBounds(20, 310, 150, 25);
        boxStatus = new JComboBox<>(Status.values());
        boxStatus.setBounds(180, 310, 380, 25);
        boxStatus.setSelectedItem(Status.NAO_INICIADO);

        // Linha 9: Responsável
        labelResponsavel = new JLabel("Responsável:");
        labelResponsavel.setBounds(20, 350, 150, 25);
        boxResponsavel = new JComboBox<>();
        for (Funcionario f : listaFuncionarios) {
            boxResponsavel.addItem(f);
        }
        if (listaFuncionarios.isEmpty()) {
            boxResponsavel.addItem(new Funcionario("Nenhum funcionário cadastrado"));
            boxResponsavel.setEnabled(false);
        }
        boxResponsavel.setBounds(180, 350, 380, 25);

        // Botões
        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(150, 420, 120, 40);

        btnSair = new JButton("Sair");
        btnSair.setBounds(330, 420, 120, 40);

        painel.add(labelID);
        painel.add(txtID);
        painel.add(labelNome);
        painel.add(txtNome);
        painel.add(labelDescricao);
        painel.add(scrollDescricao); // Adiciona o JScrollPane
        painel.add(labelDataInicio);
        painel.add(txtDataInicio);
        painel.add(labelPrazo);
        painel.add(txtPrazo);
        painel.add(labelDataPrevistaEntrega);
        painel.add(txtDataPrevistaEntrega);
        painel.add(labelDataEntrega);
        painel.add(txtDataEntrega);
        painel.add(labelStatus);
        painel.add(boxStatus);
        painel.add(labelResponsavel);
        painel.add(boxResponsavel);
        painel.add(btnSalvar);
        painel.add(btnSair);

        
        atualizarDataPrevistaEntrega();

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarTarefa(tela);
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

    private void atualizarDataPrevistaEntrega() {
        try {
            LocalDate dataInicio = LocalDate.parse(txtDataInicio.getText(), java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int prazo = Integer.parseInt(txtPrazo.getText());
            LocalDate dataPrevista = dataInicio.plusDays(prazo);
            txtDataPrevistaEntrega.setText(dataPrevista.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (DateTimeParseException | NumberFormatException e) {
            txtDataPrevistaEntrega.setText("Formato inválido");
        }
    }

    private void salvarTarefa(JDialog tela) {
        String id = txtID.getText().trim();
        String nome = txtNome.getText().trim();
        String descricao = txtDescricao.getText().trim();
        String dataInicioStr = txtDataInicio.getText().trim();
        String prazoStr = txtPrazo.getText().trim();
        String dataEntregaStr = txtDataEntrega.getText().trim();
        Status status = (Status) boxStatus.getSelectedItem();
        Funcionario responsavel = (Funcionario) boxResponsavel.getSelectedItem();

        if (nome.isEmpty() || dataInicioStr.isEmpty() || prazoStr.isEmpty() || responsavel == null || responsavel.getMatricula().isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Nome, Data de Início, Prazo e Responsável são campos obrigatórios!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate dataInicio;
        try {
            dataInicio = LocalDate.parse(dataInicioStr, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(tela, "Data de Início inválida. Use o formato dd/MM/yyyy.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int prazo;
        try {
            prazo = Integer.parseInt(prazoStr);
            if (prazo < 0) {
                JOptionPane.showMessageDialog(tela, "Prazo não pode ser negativo!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(tela, "Prazo inválido. Digite um número inteiro.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate dataEntrega = null;
        if (!dataEntregaStr.isEmpty()) {
            try {
                dataEntrega = LocalDate.parse(dataEntregaStr, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(tela, "Data de Entrega inválida. Use o formato dd/MM/yyyy ou deixe em branco.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Tarefa t = new Tarefa(nome);
        t.setID(id);
        t.setDescricao(descricao);
        t.setResponsavel(responsavel);
        t.setDataInicio(dataInicio);
        t.setPrazo(prazo);
        t.setDataEntrega(dataEntrega); // Pode ser null se não for preenchida
        t.setStatus(status); // O status pode ser atualizado posteriormente pela lógica de negócios ou UI

        TarefaDAO dao = new TarefaDAO(t);
        boolean success = dao.gravar();

        if (success) {
            JOptionPane.showMessageDialog(tela, nome + " registrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparFormulario();
            txtID.setText(Utils.gerarUUID8());
            atualizarDataPrevistaEntrega(); // Atualiza para a nova data de início padrão
        } else {
            JOptionPane.showMessageDialog(tela, "Ocorreu um erro na gravação.\nTente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparFormulario() {
        txtNome.setText("");
        txtDescricao.setText("");
        txtDataInicio.setText(LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtPrazo.setText("0");
        txtDataEntrega.setText("");
        boxStatus.setSelectedItem(Status.NAO_INICIADO);
        if (!listaFuncionarios.isEmpty()) {
            boxResponsavel.setSelectedIndex(0);
        }
    }
}