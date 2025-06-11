package br.dev.nunes.tarefas.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FrameInicial {

	private JLabel labelTitulo;
	private JButton btnFuncionarios;
	private JButton btnTarefas;
	private JButton btnSair;
	
	public FrameInicial() {
		criarTela();
	}
	
	private void criarTela() {
		
		JFrame tela = new JFrame();
		tela.setTitle("Inicio");
		tela.setSize(430, 250);
		tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tela.setLayout(null);
		tela.setLocationRelativeTo(null);
		
		Container painel = tela.getContentPane();
		
		labelTitulo = new JLabel("CONSULTAR / CADASTRAR NOVO FUNCIONARIO OU TAREFA:");
		labelTitulo.setBounds(20, 20, 370, 30);
		
		btnFuncionarios = new JButton("FUNCIONARIOS");
		btnFuncionarios.setBounds(20, 60, 180, 70);
		
		btnTarefas = new JButton("TAREFAS");
		btnTarefas.setBounds(210, 60, 180, 70);
		
		btnSair = new JButton("SAIR");
		btnSair.setBounds(310, 170, 80, 30);
		
		painel.add(labelTitulo);
		painel.add(btnFuncionarios);
		painel.add(btnTarefas);
		painel.add(btnSair);
		
		btnFuncionarios.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new FuncionarioListaFrame();
			}
		});
		
		btnTarefas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new FuncionarioListaFrame();
				
			}
		});
		
		btnSair.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		
		tela.setVisible(true);
	}
	
}