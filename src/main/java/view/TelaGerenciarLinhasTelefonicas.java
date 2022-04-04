package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Panel;
import java.util.ArrayList;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Button;
import javax.swing.JLabel;

import controller.ClienteController;
import controller.TelefoneController;
import model.vo.Cliente;
import model.vo.Telefone;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaGerenciarLinhasTelefonicas {

	private JFrame frmGerenciamentoDeLinhas;
	private ClienteController clienteController = new ClienteController();
	private TelefoneController telefoneController = new TelefoneController();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaGerenciarLinhasTelefonicas window = new TelaGerenciarLinhasTelefonicas();
					window.frmGerenciamentoDeLinhas.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaGerenciarLinhasTelefonicas() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGerenciamentoDeLinhas = new JFrame();
		frmGerenciamentoDeLinhas.setTitle("Gerenciamento de Linhas Telef\u00F4nicas");
		frmGerenciamentoDeLinhas.setBounds(100, 100, 520, 180);
		frmGerenciamentoDeLinhas.getContentPane().setLayout(null);
		
		JLabel lblTelefones = new JLabel("Telefones");
		lblTelefones.setBounds(10, 10, 100, 20);
		frmGerenciamentoDeLinhas.getContentPane().add(lblTelefones);
		
		ArrayList<Telefone> telefones = telefoneController.consultarTodos();
		
		JComboBox cbTelefones = new JComboBox(telefones.toArray());
		cbTelefones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		cbTelefones.setSelectedIndex(-1);
		cbTelefones.setBounds(80, 10, 400, 20);
		frmGerenciamentoDeLinhas.getContentPane().add(cbTelefones);
		
		JLabel lblLinhas = new JLabel("Linhas");
		lblLinhas.setBounds(10, 40, 100, 20);
		frmGerenciamentoDeLinhas.getContentPane().add(lblLinhas);
		
		ArrayList<Cliente> clientes = clienteController.consultarTodos();
		
		JComboBox cbLinhas = new JComboBox(clientes.toArray());
		cbLinhas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		cbLinhas.setSelectedIndex(-1);
		cbLinhas.setBounds(80, 40, 400, 20);
		frmGerenciamentoDeLinhas.getContentPane().add(cbLinhas);
		
		JButton btnAssociarUsuario = new JButton("Associar Usu\u00E1rio");
		btnAssociarUsuario.setBounds(115, 80, 140, 50);
		frmGerenciamentoDeLinhas.getContentPane().add(btnAssociarUsuario);
		
		JButton btnLibertarTelefone = new JButton("Liberar Telef\u00F4ne");
		btnLibertarTelefone.setBounds(255, 80, 140, 50);
		frmGerenciamentoDeLinhas.getContentPane().add(btnLibertarTelefone);
	}
}
