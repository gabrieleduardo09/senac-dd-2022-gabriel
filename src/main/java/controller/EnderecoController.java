package controller;

import java.util.ArrayList;

import model.dao.EnderecoDAO;
import model.vo.Endereco;

public class EnderecoController {

	public ArrayList<Endereco> pesquisarTodos(){
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		return enderecoDAO.consultarTodos();
	}

	public String salvar(Endereco novoEndereco) {
		// TODO Auto-generated method stub
		String mensagem = "";
		
		if (novoEndereco == null) {
			mensagem = "Informe todos os dados do novo endereço";
		}
		return mensagem;
	}
}
