package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.EnderecoController;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadastroEndereco extends JFrame {

	private EnderecoController enderecoController = new EnderecoController();
	private JLabel lblRua;
	private JLabel lblNumero;
	private JLabel lblCidade;
	private JLabel lblUf;
	private JLabel lblCep;
	private JTextField txtRua;
	private JTextField txtNumero;
	private JTextField txtCidade;
	private JTextField txtUf;
	private JTextField txtCep;
	private JButton btnLimpar;
	
	public TelaCadastroEndereco() {
		this.setTitle("Cadastro de Endereço");
		this.setBounds(300, 300, 580, 260);

		// Componentes
		// RUA
		lblRua = new JLabel("Rua:");
		lblRua.setBounds(10, 10, 100, 20);

		txtRua = new JTextField();
		txtRua.setBounds(100, 10, 450, 20);

		// NUMERO
		lblNumero = new JLabel("Número:");
		lblNumero.setBounds(10, 40, 100, 20);

		txtNumero = new JTextField();
		txtNumero.setBounds(100, 40, 450, 20);

		// CIDADE
		lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(10, 70, 100, 20);

		txtCidade = new JTextField();
		txtCidade.setBounds(100, 70, 450, 20);

		// UF
		lblUf = new JLabel("UF:");
		lblUf.setBounds(10, 100, 100, 20);

		txtUf = new JTextField();
		txtUf.setBounds(100, 100, 450, 20);

		// CEP
		lblCep = new JLabel("Cep:");
		lblCep.setBounds(10, 130, 100, 20);

		txtCep = new JTextField();
		txtCep.setBounds(100, 130, 450, 20);

		getContentPane().setLayout(null);
		this.getContentPane().add(lblRua);
		this.getContentPane().add(txtRua);
		this.getContentPane().add(lblNumero);
		this.getContentPane().add(txtNumero);
		this.getContentPane().add(lblCidade);
		this.getContentPane().add(txtCidade);
		this.getContentPane().add(lblUf);
		this.getContentPane().add(txtUf);
		this.getContentPane().add(lblCep);
		this.getContentPane().add(txtCep);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnSalvar.setBounds(183, 161, 100, 50);
		getContentPane().add(btnSalvar);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(293, 161, 100, 50);
		getContentPane().add(btnLimpar);
		
	}
}
