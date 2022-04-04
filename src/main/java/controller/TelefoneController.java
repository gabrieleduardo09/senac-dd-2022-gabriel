package controller;

import java.util.ArrayList;

import model.bo.TelefoneBO;
import model.vo.Telefone;

public class TelefoneController {

	public ArrayList<Telefone> consultarTodos() {
		TelefoneBO telefoneBO= new TelefoneBO();
		return telefoneBO.consultarTodos();
	}
}
