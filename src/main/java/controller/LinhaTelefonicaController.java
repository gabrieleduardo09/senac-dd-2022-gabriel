package controller;

import model.bo.LinhaTelefonicaBO;
import model.vo.Cliente;
import model.vo.LinhaTelefonica;

public class LinhaTelefonicaController {

	public LinhaTelefonicaBO linhaTelefonicaBO = new LinhaTelefonicaBO();
	
	public Cliente obterDonoAtualDoTelefone(int id) {
		return linhaTelefonicaBO.obterDonoAtualDoTelefone(id);
	}

}
