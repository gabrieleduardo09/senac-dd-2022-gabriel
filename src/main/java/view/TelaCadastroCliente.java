package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controller.ClienteController;
import controller.EnderecoController;
import model.vo.Cliente;
import model.vo.Endereco;
import model.exception.ErroAoSalvarClienteException;

public class TelaCadastroCliente extends JFrame {

	private ClienteController clienteController = new ClienteController();
	private EnderecoController enderecoController = new EnderecoController();
	private JLabel lblNome;
	private JLabel lblCpf;
	private JLabel lblEndereco;
	private JTextField txtNome;
	private JTextField txtCpf;
	private JComboBox cbEndereco;
	private JButton btnSalvar;
	private JButton btnLimpar;
	
	public TelaCadastroCliente() {
		this.setTitle("Cadastro de cliente");
		this.setBounds(300, 300, 599, 200);
		
		//Cria os componentes da tela
		lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 10, 100, 20);
		
		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if(event.getKeyCode() == KeyEvent.VK_ENTER) {
					salvar();
				}
			}
		});
		txtNome.setBounds(76, 10, 500, 20);
		
		lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(10, 40, 100, 20);
		
		txtCpf = new JTextField();
		txtCpf.setBounds(76, 40, 500, 20);
		
		lblEndereco = new JLabel("Endere?o:");
		lblEndereco.setBounds(10, 71, 100, 20);

		ArrayList<Endereco> enderecos = enderecoController.pesquisarTodos();
		
		cbEndereco = new JComboBox(enderecos.toArray());
		cbEndereco.setBounds(76, 71, 500, 20);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(201, 102, 100, 48);
		
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});

		getContentPane().setLayout(null);
		this.getContentPane().add(lblNome);
		this.getContentPane().add(txtNome);
		this.getContentPane().add(lblCpf);
		this.getContentPane().add(txtCpf);
		this.getContentPane().add(lblEndereco);
		this.getContentPane().add(cbEndereco);
		this.getContentPane().add(btnSalvar);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limparCampos();
			}
		});
		btnLimpar.setBounds(301, 102, 100, 48);
		getContentPane().add(btnLimpar);
	}

	protected void limparCampos() {
		this.txtNome.setText("");
		this.txtCpf.setText("");
		this.cbEndereco.setSelectedItem(null);
	}
	
	protected void salvar() {
		Cliente novoCliente = new Cliente();
		novoCliente.setNome(txtNome.getText());
		novoCliente.setCpf(txtCpf.getText());
		novoCliente.setEndereco((Endereco)cbEndereco.getSelectedItem());
		
		String mensagem;
		try {
			mensagem = clienteController.salvar(novoCliente);
			JOptionPane.showMessageDialog(null, mensagem,"Mensagem", JOptionPane.INFORMATION_MESSAGE);
			limparCampos();
		} catch (ErroAoSalvarClienteException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Aten??o", JOptionPane.WARNING_MESSAGE);
		}
		
	}
}
