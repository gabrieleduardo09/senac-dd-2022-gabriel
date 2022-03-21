package executavel;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.dao.ClienteDAO;
import model.dao.EnderecoDAO;
import model.dao.LinhaTelefonicaDAO;
import model.dao.TelefoneDAO;
import model.vo.Cliente;
import model.vo.Endereco;
import model.vo.LinhaTelefonica;
import model.vo.Telefone;

public class ExecutavelTelefonia {

	public static void main(String[] argumentos) {
//		testarCrudEndereco();
//		testarCrudCliente();
//		
//		//TODO Exercicio
//		testarCrudTelefone();
		// testarCrudLinhaTelefonica();

		testarCadastroClienteComJOptionPane();
	}

	private static void testarCadastroClienteComJOptionPane() {
		String cpf = JOptionPane.showInputDialog("Informe o CPF (somente n�meros)");
		String nome = JOptionPane.showInputDialog("Informe o nome completo");

		// TODO Violando o MVC...
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		ClienteDAO clienteDAO = new ClienteDAO();

		ArrayList<Endereco> enderecos = enderecoDAO.consultarTodos();

		Endereco enderecoSelecionado = (Endereco) JOptionPane.showInputDialog(null, "Selecione um endere�o",
				"Cadastro de novo cliente", JOptionPane.INFORMATION_MESSAGE, null, enderecos.toArray(), null);

		Cliente novoCliente = new Cliente(nome, cpf, enderecoSelecionado);
		novoCliente = clienteDAO.inserir(novoCliente);

		if (novoCliente.getId() > 0) {
			JOptionPane.showMessageDialog(null, "Novo cliente salvo!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Erro ao salvar cliente", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void testarCrudTelefone() {
		// TODO Auto-generated method stub
		
//		 CREATE TABLE `telefonia`.`telefone` (
//				  `id` INT NOT NULL AUTO_INCREMENT,
//				  `ddd` VARCHAR(2) NOT NULL,
//				  `numero` VARCHAR(10) NOT NULL,
//				  `tipo` INT NOT NULL COMMENT 'Fixo: 1\M�vel: 2',
//				  `ativo` TINYINT NOT NULL,
//				  PRIMARY KEY (`id`));
		String ddd = JOptionPane.showInputDialog("Informe o DDD");
		String numero = JOptionPane.showInputDialog("Informe o n�mero de telefone");
		String tipo = JOptionPane.showInputDialog("Tipo Fixo: 1 | M�vel: 2");

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
