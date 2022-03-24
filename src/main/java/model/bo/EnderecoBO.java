package model.bo;

import model.dao.EnderecoDAO;
import model.vo.Endereco;

public class EnderecoBO {

	public EnderecoDAO dao = new EnderecoDAO();

	public String salvar(Endereco novoEndereco) {
		String mensagem = "";
		novoEndereco = dao.inserir(novoEndereco);

		if (novoEndereco.getId() > 0) {
			mensagem = "Endere�o cadastrado com sucesso (id: " + novoEndereco.getId() + ")";
		} else {
			mensagem = "Erro ao cadastrar endere�o";
		}

		return mensagem;
	}

}
