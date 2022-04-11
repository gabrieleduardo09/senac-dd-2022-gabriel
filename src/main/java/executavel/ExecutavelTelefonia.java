package executavel;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import controller.ClienteController;
import controller.EnderecoController;
import model.dao.ClienteDAO;
import model.dao.EnderecoDAO;
import model.dao.LinhaTelefonicaDAO;
import model.dao.TelefoneDAO;
import model.exception.ErroAoSalvarClienteException;
import model.vo.Cliente;
import model.vo.Endereco;
import model.vo.LinhaTelefonica;
import model.vo.Telefone;
import view.TelaCadastroCliente;
import view.TelaCadastroEndereco;

public class ExecutavelTelefonia {

	public static void main(String[] argumentos) {
		
//		testarCrudEndereco();
//		testarCrudCliente();
//
//		testarCrudTelefone();
//		testarCrudLinhaTelefonica();
//		
//		testarCadastroClienteComJOptionPane();
//		exclusaoClienteComJOptionPane();
//
//		testarCadastroEnderecoComJOptionPane();
		
		TelaCadastroCliente novaTela = new TelaCadastroCliente();
		novaTela.setVisible(true);

//		TelaCadastroEndereco novaTelaCadastroEndereco = new TelaCadastroEndereco();
//		novaTelaCadastroEndereco.setVisible(true);
		// Terminar...
		
		
	}

	private static void exclusaoClienteComJOptionPane() {
		ClienteController clienteController = new ClienteController();
		ArrayList<Cliente> clientes = clienteController.consultarTodos();

		Cliente removerCliente = (Cliente) JOptionPane.showInputDialog(null, "Selecione o cliente",
				"Exclus�o de cliente", JOptionPane.INFORMATION_MESSAGE, null, clientes.toArray(), null);

		boolean remover = clienteController.remover(removerCliente);

		if (remover) {
			JOptionPane.showMessageDialog(null,
					"Cliente " + removerCliente.getNome() + " (" + removerCliente.getCpf() + ") foi exclu�do");
		} else {
			JOptionPane.showMessageDialog(null, "Cliente n�o foi exclu�do", "Erro", JOptionPane.WARNING_MESSAGE);
		}
	}

	private static void testarCadastroClienteComJOptionPane() {
		String cpf = JOptionPane.showInputDialog("Informe o CPF (somente n�meros)");
		String nome = JOptionPane.showInputDialog("Informe o nome completo");

		EnderecoController enderecoController = new EnderecoController();
		ClienteController clienteController = new ClienteController();

		ArrayList<Endereco> enderecos = enderecoController.pesquisarTodos();

		Endereco enderecoSelecionado = (Endereco) JOptionPane.showInputDialog(null, "Selecione um endere�o",
				"Cadastro de novo cliente", JOptionPane.INFORMATION_MESSAGE, null, enderecos.toArray(), null);

		Cliente novoCliente = new Cliente(nome, cpf, enderecoSelecionado);
		String mensagem;

		try {
			mensagem = clienteController.salvar(novoCliente);
			JOptionPane.showMessageDialog(null, mensagem,"Mensagem", JOptionPane.INFORMATION_MESSAGE);
		} catch (ErroAoSalvarClienteException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),"Alerta", JOptionPane.WARNING_MESSAGE);
		}
	}

	private static void testarCrudTelefone() {
		// TODO Auto-generated method stub
	}

	private static void testarCadastroEnderecoComJOptionPane() {
		// TODO Auto-generated method stub
		String rua = JOptionPane.showInputDialog("Digite a Rua");
		String numero = JOptionPane.showInputDialog("Digite o n�mero");
		String cidade = JOptionPane.showInputDialog("Digite a Cidade");
		String uf = JOptionPane.showInputDialog("Digite o UF (apenas a sigla)");
		String cep = JOptionPane.showInputDialog("Digite o CEP (somente n�meros)");

		EnderecoController enderecoController = new EnderecoController();

		Endereco novoEndereco = new Endereco(rua, numero, cidade, uf, cep);
		String mensagem = enderecoController.salvar(novoEndereco);

		JOptionPane.showMessageDialog(null, mensagem, "Mensagem", JOptionPane.INFORMATION_MESSAGE);
	}

	private static void testarCrudEndereco() {
		// Violando o MVC, pois o main vai chamar a camada de modelo
		EnderecoDAO dao = new EnderecoDAO();
		Endereco novo = new Endereco("Anita Garibaldi", "300", "Florianópolis", "SC", "88320005");

		System.out.println("##Testes de CRUD de Endere�o\n");

		System.out.println("#Teste de insert");
		dao.inserir(novo);

		int idDoNovoEndereco = novo.getId();
		System.out.println(idDoNovoEndereco > 0 ? "Salvou novo endere�o" : "N�o salvou");

		novo.setRua("Rua alterada");

		System.out.println("#Teste de update");
		boolean atualizou = dao.atualizar(novo);

		System.out.println(atualizou ? "Endere�o atualizado" : "Endere�o N�o atualizado");

		System.out.println("#Teste de select");
		Endereco enderecoConsultado = dao.consultar(idDoNovoEndereco);
		System.out.println("Endere�o consultado por id (" + idDoNovoEndereco + "): " + enderecoConsultado.toString());

		System.out.println("#Teste de delete");
		dao.remover(novo.getId());

		System.out.println("#Teste de select");
		Endereco enderecoConsultadoAposRemocao = dao.consultar(idDoNovoEndereco);
		System.out.println(enderecoConsultadoAposRemocao == null ? "Endere�o n�o existe (ok)"
				: "Endere�o n�o foi devidamente exclu��o�do");
	}

	private static void testarCrudCliente() {
		// Violando o MVC, pois o main vai chamar a camada de modelo
		ClienteDAO dao = new ClienteDAO();
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		Cliente novo = new Cliente("Rom�rio Souza", "21200022235", enderecoDAO.consultar(2), null);

		System.out.println("##Testes de CRUD de Cliente\n");
		System.out.println("#Teste de insert");
		dao.inserir(novo);

		int idDoNovoCliente = novo.getId();
		System.out.println(idDoNovoCliente > 0 ? "Salvou novo cliente" : "N�o salvou");

		novo.setNome("Rom�rio Souza 11");

		System.out.println("#Teste de update");
		boolean atualizou = dao.atualizar(novo);

		System.out.println(atualizou ? "Cliente atualizado" : "Cliente N�o atualizado");

		System.out.println("#Teste de select");
		Cliente clienteConsultado = dao.consultar(idDoNovoCliente);
		System.out.println("Cliente consultado por id (" + idDoNovoCliente + "): " + clienteConsultado.toString());

		System.out.println("#Teste de delete");
		dao.remover(novo.getId());

		System.out.println("#Teste de select");
		Cliente clienteConsultadoAposRemocao = dao.consultar(idDoNovoCliente);
		System.out.println(clienteConsultadoAposRemocao == null ? "Cliente n�o existe (ok)"
				: "Cliente n�o foi devidamente exclu��o�do");
	}

	private static void testarCrudLinhaTelefonica() {
		LinhaTelefonicaDAO linhaDAO = new LinhaTelefonicaDAO();
		TelefoneDAO telefoneDAO = new TelefoneDAO();

		ArrayList<Telefone> telefones = telefoneDAO.consultarTodos();

		// Cria simplesmente linhas disponíveis (1 para cada telefone criado)
		for (Telefone t : telefones) {
			LinhaTelefonica novaLinha = new LinhaTelefonica();
			novaLinha.setTelefone(t);
			linhaDAO.inserir(novaLinha);
		}

		System.out.println("#Teste de select");
		LinhaTelefonica linha1 = linhaDAO.consultar(1);
		System.out.println("Linha consultada por id (" + 1 + "): " + linha1.toString());

		System.out.println("#Teste de select (todos)");
		ArrayList<LinhaTelefonica> linhas = linhaDAO.consultarTodos();
		System.out.println("Quantidade de linhas: " + linhas.size());

	}

}
