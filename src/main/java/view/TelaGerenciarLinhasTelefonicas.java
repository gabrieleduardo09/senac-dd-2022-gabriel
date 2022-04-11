package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Panel;
import java.util.ArrayList;
import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Button;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controller.ClienteController;
import controller.LinhaTelefonicaController;
import controller.TelefoneController;
import model.vo.Endereco;
import model.exception.ErroAoSalvarClienteException;
import model.vo.Cliente;
import model.vo.LinhaTelefonica;
import model.vo.Telefone;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaGerenciarLinhasTelefonicas {

	private JFrame frmGerenciamentoDeLinhas;
	private ClienteController clienteController = new ClienteController();
	private TelefoneController telefoneController = new TelefoneController();
	private LinhaTelefonicaController linhaTelefonicaController = new LinhaTelefonicaController();
	private JButton btnAssociarUsuario;
	private JButton btnLiberarTelefone;
	private JComboBox cbTelefones;
	private JComboBox cbLinhas;
	protected Telefone telefoneSelecionado;
	private ArrayList<Cliente> clientes;
	private ArrayList<Telefone> telefones;

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

		telefones = telefoneController.consultarTodos();

		cbTelefones = new JComboBox(telefones.toArray());
		cbTelefones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telefoneSelecionado = (Telefone) cbTelefones.getSelectedItem();

				if (telefoneSelecionado != null) {
					if (telefoneSelecionado.isAtivo()) {
						Cliente dono = linhaTelefonicaController.obterDonoAtualDoTelefone(telefoneSelecionado.getId());
						cbLinhas.getModel().setSelectedItem(dono);
						cbLinhas.setEnabled(false);
						btnLiberarTelefone.setEnabled(true);
						btnAssociarUsuario.setEnabled(false);
					} else {
						cbLinhas.setEnabled(true);
						btnLiberarTelefone.setEnabled(false);
						btnAssociarUsuario.setEnabled(true);
					}
				}
			}
		});

		cbTelefones.setBounds(80, 10, 400, 20);
		cbTelefones.setSelectedIndex(-1);
		frmGerenciamentoDeLinhas.getContentPane().add(cbTelefones);

		JLabel lblLinhas = new JLabel("Linhas");
		lblLinhas.setBounds(10, 40, 100, 20);
		frmGerenciamentoDeLinhas.getContentPane().add(lblLinhas);

		clientes = clienteController.consultarTodos();

		cbLinhas = new JComboBox(clientes.toArray());
		cbLinhas.setSelectedIndex(-1);
		cbLinhas.setBounds(80, 40, 400, 20);
		frmGerenciamentoDeLinhas.getContentPane().add(cbLinhas);

		btnAssociarUsuario = new JButton("Associar Usu\u00E1rio");
		btnAssociarUsuario.addActionListener(new ActionListener() {
			private Cliente clienteSelecionado;

			public void actionPerformed(ActionEvent arg0) {
				clienteSelecionado = (Cliente) cbLinhas.getSelectedItem();
				boolean criou = linhaTelefonicaController.criarLinha(telefoneSelecionado, clienteSelecionado);

				if (criou) {
					atualizarCombos();
					JOptionPane.showMessageDialog(null, "Linha criada com sucesso", "Sucesso",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao criar linha", "Atenção",
							JOptionPane.WARNING_MESSAGE);
				}

			}

		});
		btnAssociarUsuario.setEnabled(false);
		btnAssociarUsuario.setBounds(115, 80, 140, 50);
		frmGerenciamentoDeLinhas.getContentPane().add(btnAssociarUsuario);

		btnLiberarTelefone = new JButton("Liberar Telef\u00F4ne");
		btnLiberarTelefone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean liberarLinha = linhaTelefonicaController.liberarLinha(telefoneSelecionado.getId());

				if (liberarLinha) {
					JOptionPane.showMessageDialog(null, "Linha liberada com sucesso", "Sucesso",
							JOptionPane.INFORMATION_MESSAGE);
					atualizarCombos();
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao liberar linha", "Atenção",
							JOptionPane.WARNING_MESSAGE);
				}
			}

		});
		btnLiberarTelefone.setEnabled(false);
		btnLiberarTelefone.setBounds(255, 80, 140, 50);
		frmGerenciamentoDeLinhas.getContentPane().add(btnLiberarTelefone);
	}

	protected void atualizarCombos() {
		// https://community.oracle.com/tech/developers/discussion/2130310/jcombobox-not-visually-updating-after-setselecteditem
		telefones = telefoneController.consultarTodos();
		cbTelefones.setModel(new DefaultComboBoxModel(telefones.toArray()));
		cbTelefones.repaint();

		clientes = clienteController.consultarTodos();
		cbLinhas.setModel(new DefaultComboBoxModel(clientes.toArray()));
		cbLinhas.repaint();
	}
}
