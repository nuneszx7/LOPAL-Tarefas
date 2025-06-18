package br.dev.nunes.tarefas.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FrameInicial {

    private JButton btnFuncionarios;
    private JButton btnTarefas;

    // Construtor
    public FrameInicial() {
        criarTela();
    }

    private void criarTela() {
        JFrame telaMainFrame = new JFrame("Gerenciador de tarefas");

        telaMainFrame.setSize(300, 120);
        telaMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        telaMainFrame.setLayout(null);
        telaMainFrame.setLocationRelativeTo(null);
        telaMainFrame.setResizable(false);

        Container painel = telaMainFrame.getContentPane();

        btnFuncionarios = new JButton("Funcionários");
        btnFuncionarios.setBounds(20, 20, 120, 40);

        btnTarefas = new JButton("Tarefas");
        btnTarefas.setBounds(150, 20, 120, 40);

        painel.add(btnFuncionarios);
        painel.add(btnTarefas);

        telaMainFrame.setVisible(true);

        btnFuncionarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FuncionarioListaFrame();
            }
        });

        btnTarefas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Passa o JFrame pai, se TarefaLista for um JDialog.
                // Se TarefaLista for um JFrame independente, pode ser new TarefaLista(null); ou apenas new TarefaLista();
                new TarefaLista(telaMainFrame);
            }
        });
    }

    // MÉTODO MAIN ADICIONADO AQUI
    public static void main(String[] args) {
        // Para garantir que a interface gráfica seja criada na Thread de Despacho de Eventos (EDT)
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FrameInicial(); // Instancia a sua tela inicial
            }
        });
    }
}