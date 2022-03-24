package controller;

import java.util.ArrayList;

import model.bo.EnderecoBO;
import model.dao.EnderecoDAO;
import model.vo.Endereco;

public class EnderecoController {

	public ArrayList<Endereco> pesquisarTodos(){
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		return enderecoDAO.consultarTodos();
	}

	public String salvar(Endereco novoEndereco) {
		// TODO Auto-generated method stub
		EnderecoBO bo = new EnderecoBO();
		String mensagem = "";
		
		if (novoEndereco == null) {
			mensagem = "Informe todos os dados do novo endereço";
		}
		
		if(mensagem.isEmpty()) {
			mensagem = bo.salvar(novoEndereco);
		}
		return mensagem;
	}
}
