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
	private JFrame telaMainFrame; 

	public FrameInicial() {
		criarTela();
	}

	private void criarTela() {
		telaMainFrame = new JFrame("Gerenciador de tarefas"); 

		telaMainFrame.setSize(300, 120);
		telaMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //encerra o programa
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
				
				new TarefaLista(telaMainFrame);
				
			}
		});
	}


	public static void main(String[] args) {
    	
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FrameInicial();
            }
        });
    }
}