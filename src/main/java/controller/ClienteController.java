package controller;

import java.util.ArrayList;

import model.bo.ClienteBO;
import model.dao.ClienteDAO;
import model.dao.EnderecoDAO;
import model.exception.ClienteComLinhaTelefonicaException;
import model.exception.ErroAoSalvarClienteException;
import model.vo.Cliente;

public class ClienteController {

	public ClienteBO bo = new ClienteBO();
	
	public String salvar(Cliente novo) throws ErroAoSalvarClienteException {
		String mensagem = "";
		
		if(novo == null) {
			mensagem = "Informe todos os dados do novo cliente";
		}else if(novo.getCpf().trim().length() != 11){
			try {
				Long.parseLong(novo.getCpf());
			} catch (NumberFormatException excecao) {
				mensagem = "CPF deve somente números \n";
			}
			
			mensagem += "CPF deve conter 11 dígitos";
			
			throw new ErroAoSalvarClienteException(mensagem);
		}
		
		if(mensagem.isEmpty()) {
			mensagem = bo.salvar(novo);
		}
		
		return mensagem;
	}

	public ArrayList<Cliente> consultarTodos() {
		return bo.consultarTodos();
	}

	public boolean remover(Cliente removerCliente) {
		return bo.remover(removerCliente);
	}
	
	public String excluir(Cliente clienteParaExcluir) throws ClienteComLinhaTelefonicaException {
		String mensagem = "";
		
		if(bo.excluir(clienteParaExcluir)) {
			mensagem = "Cliente " + clienteParaExcluir.getNome() 
				+ " (" + clienteParaExcluir.getCpf() + ") foi excluÃ­do";
		}
		
		return mensagem;
	}
	
}
