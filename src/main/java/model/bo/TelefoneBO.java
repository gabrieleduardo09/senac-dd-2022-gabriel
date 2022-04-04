package model.bo;

import java.util.ArrayList;

import model.dao.TelefoneDAO;
import model.vo.Telefone;

public class TelefoneBO {

	public ArrayList<Telefone> consultarTodos() {
		TelefoneDAO telefoneDAO = new TelefoneDAO();
		return telefoneDAO.consultarTodos();
	}

}
