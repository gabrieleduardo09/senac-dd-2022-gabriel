package controller;

import model.bo.LinhaTelefonicaBO;
import model.vo.Cliente;
import model.vo.LinhaTelefonica;
import model.vo.Telefone;

public class LinhaTelefonicaController {

	public LinhaTelefonicaBO linhaTelefonicaBO = new LinhaTelefonicaBO();
	
	public Cliente obterDonoAtualDoTelefone(int id) {
		return linhaTelefonicaBO.obterDonoAtualDoTelefone(id);
	}

	public boolean liberarLinha(int id) {
		return linhaTelefonicaBO.liberarLinha(id);
	}

	public boolean criarLinha(Telefone telefoneSelecionado, Cliente clienteSelecionado) {
		return linhaTelefonicaBO.criarLinha(telefoneSelecionado, clienteSelecionado);
	}

}
