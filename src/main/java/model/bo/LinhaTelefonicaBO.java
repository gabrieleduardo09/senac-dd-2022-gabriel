package model.bo;

import model.dao.LinhaTelefonicaDAO;
import model.vo.Cliente;

public class LinhaTelefonicaBO {

	public LinhaTelefonicaDAO linhaTelefonicaDAO = new LinhaTelefonicaDAO();
	
	public Cliente obterDonoAtualDoTelefone(int id) {
		return linhaTelefonicaDAO.obterDonoAtualDoTelefone(id);
	}

}
