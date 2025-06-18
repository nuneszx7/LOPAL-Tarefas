package br.dev.nunes.tarefas.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import br.dev.nunes.tarefas.dao.FuncionarioDAO;
import br.dev.nunes.tarefas.model.Funcionario;
import br.dev.nunes.tarefas.utils.Utils;

public class FuncionarioFrame {
    private JLabel labelMatricula;
    private JLabel labelNome;
    private JLabel labelCargo;
    private JLabel labelSetor;
    private JLabel labelSalario;

    private JTextField txtMatricula;
    private JTextField txtNome;
    private JTextField txtCargo;
    private JTextField txtSetor;
    private JTextField txtSalario;

    private JButton btnSalvar;
    private JButton btnSair;

    public FuncionarioFrame(JFrame pai) {
        criarTela(pai);
    }

    private void criarTela(JFrame pai) {
        JDialog telaFuncionario = new JDialog(pai, "Cadastrar novo funcionário", true);
        telaFuncionario.setSize(500, 500);
        telaFuncionario.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        telaFuncionario.setLayout(null);
        telaFuncionario.setLocationRelativeTo(null);
        telaFuncionario.setResizable(false);

        Container painel = telaFuncionario.getContentPane();

        labelMatricula = new JLabel("Matrícula:");
        labelMatricula.setBounds(20, 20, 100, 25);
        txtMatricula = new JTextField(Utils.gerarUUID8());
        txtMatricula.setBounds(150, 20, 300, 25);
        txtMatricula.setEditable(false);

        labelNome = new JLabel("Nome:");
        labelNome.setBounds(20, 60, 100, 25);
        txtNome = new JTextField();
        txtNome.setBounds(150, 60, 300, 25);

        labelCargo = new JLabel("Cargo:");
        labelCargo.setBounds(20, 100, 100, 25);
        txtCargo = new JTextField();
        txtCargo.setBounds(150, 100, 300, 25);

        labelSetor = new JLabel("Setor:");
        labelSetor.setBounds(20, 140, 100, 25);
        txtSetor = new JTextField();
        txtSetor.setBounds(150, 140, 300, 25);

        labelSalario = new JLabel("Salário:");
        labelSalario.setBounds(20, 180, 100, 25);
        txtSalario = new JTextField();
        txtSalario.setBounds(150, 180, 300, 25);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(100, 250, 120, 40);

        btnSair = new JButton("Sair");
        btnSair.setBounds(280, 250, 120, 40);

        painel.add(labelMatricula);
        painel.add(txtMatricula);
        painel.add(labelNome);
        painel.add(txtNome);
        painel.add(labelCargo);
        painel.add(txtCargo);
        painel.add(labelSetor);
        painel.add(txtSetor);
        painel.add(labelSalario);
        painel.add(txtSalario);
        painel.add(btnSalvar);
        painel.add(btnSair);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarFuncionario(telaFuncionario);
            }
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resposta = JOptionPane.showConfirmDialog(telaFuncionario, "Sair da tela de cadastro?", "Sair",
                        JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    telaFuncionario.dispose();
                }
            }
        });

        telaFuncionario.setVisible(true);
    }

    private void salvarFuncionario(JDialog telaFuncionario) {
        String nome = txtNome.getText().trim();
        String cargo = txtCargo.getText().trim();
        String setor = txtSetor.getText().trim();
        String salarioStr = txtSalario.getText().trim();

        if (nome.isEmpty() || cargo.isEmpty() || setor.isEmpty() || salarioStr.isEmpty()) {
            JOptionPane.showMessageDialog(telaFuncionario, "Todos os campos são obrigatórios!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double salario;
        try {
            salario = Double.parseDouble(salarioStr);
            if (salario < 0) {
                 JOptionPane.showMessageDialog(telaFuncionario, "Salário não pode ser negativo!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                 return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(telaFuncionario, "Salário inválido. Digite um número.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Funcionario f = new Funcionario(nome);
        f.setMatricula(txtMatricula.getText());
        f.setCargo(cargo);
        f.setSetor(setor);
        f.setSalario(salario);

        FuncionarioDAO dao = new FuncionarioDAO(f);
        boolean success = dao.gravar();

        if (success) {
            JOptionPane.showMessageDialog(telaFuncionario, nome + " cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparFormulario();
            txtMatricula.setText(Utils.gerarUUID8());
        } else {
            JOptionPane.showMessageDialog(telaFuncionario, "Ocorreu um erro na gravação.\nTente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparFormulario() {
        txtNome.setText("");
        txtCargo.setText("");
        txtSetor.setText("");
        txtSalario.setText("");
    }
}