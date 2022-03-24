package model.bo;

import java.util.ArrayList;

import model.dao.ClienteDAO;
import model.vo.Cliente;

public class ClienteBO {

	public ClienteDAO dao = new ClienteDAO();

	public String salvar(Cliente novo) {
		String mensagem = "";

		if (dao.cpfJaCadastrado(novo.getCpf())) {
			mensagem = "CPF informado (" + novo.getCpf() + ") já foi utilizado";
		} else {
			// Salvar no banco
			novo = dao.inserir(novo);

			if (novo.getId() > 0) {
				mensagem = "Cliente cadastrado com sucesso (id: " + novo.getId() + ")";
			} else {
				mensagem = "Erro ao cadastrar cliente";
			}
		}

		return mensagem;
	}

	public ArrayList<Cliente> consultarTodos() {
		return dao.consultarTodos();
	}

	public boolean remover(Cliente removerCliente) {
		boolean excluir;

		if (removerCliente.getLinhas().size() > 0) {
			excluir = false;
		} else {
			excluir = dao.remover(removerCliente.getId());
		}

		return excluir;
	}
}
